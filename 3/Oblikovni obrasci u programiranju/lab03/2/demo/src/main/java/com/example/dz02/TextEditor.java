package com.example.dz02;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;

import com.example.dz02.commands.*;
import com.example.dz02.interfaces.*;
import com.example.dz02.jButton.JButtonAction;
import com.example.dz02.jMenuItems.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;

public class TextEditor extends JComponent implements KeyListener, CursorObserver, TextObserver, ClipboardObserver {
    private TextEditorModel model;
    private int cursorX;
    private int cursorY;
    private ClipboardStack clipboardStack;

    public TextEditor(TextEditorModel model) {
        this.model = model;
        setFocusable(true);
        addKeyListener(this);
        cursorX = 0;
        cursorY = 0;
        model.addCursorObserver(this);
        model.addTextObserver(this);

        clipboardStack = new ClipboardStack();
        clipboardStack.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        int x = 0, y = g.getFontMetrics().getHeight();
        g.setColor(Color.BLACK);
        Iterator<String> it = model.allLines();
        LocationRange selectionRange = model.getSelectionRange();
        int lineIndex = 0;

        while (it.hasNext()) {
            String row = it.next();
            int rowWidth = g.getFontMetrics().stringWidth(row);

            // Check if the current line is within the selection range
            if (selectionRange.getStart().getRow() <= lineIndex && selectionRange.getEnd().getRow() >= lineIndex) {
                int startX, endX;
                if (selectionRange.getStart().getRow() == lineIndex) {
                    startX = g.getFontMetrics().stringWidth(row.substring(0, selectionRange.getStart().getColumn()));
                } else {
                    startX = 0;
                }
                if (selectionRange.getEnd().getRow() == lineIndex) {
                    endX = g.getFontMetrics().stringWidth(row.substring(0, selectionRange.getEnd().getColumn()));
                } else {
                    endX = rowWidth;
                }

                // Draw the blue background for the selected text
                g.setColor(Color.BLUE);
                g.fillRect(startX, y - g.getFontMetrics().getAscent(), endX - startX, g.getFontMetrics().getHeight());
                g.setColor(Color.BLACK);
            }

            // Draw the text on top of the blue rectangle
            g.drawString(row, x, y);
            y += g.getFontMetrics().getHeight();
            lineIndex++;
        }
        // Draw the cursor
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(2));
        g.drawLine(cursorX, cursorY, cursorX, cursorY + g.getFontMetrics().getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        final UndoManager undoManager = UndoManager.getInstance();

        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
            return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> model.moveCursorLeft();
            case KeyEvent.VK_RIGHT -> model.moveCursorRight();
            case KeyEvent.VK_UP -> model.moveCursorUp();
            case KeyEvent.VK_DOWN -> model.moveCursorDown();
            case KeyEvent.VK_BACK_SPACE -> undoManager.performEditAction(new DeleteBeforeAction(model));
            case KeyEvent.VK_DELETE -> undoManager.performEditAction(new DeleteAfterAction(model));
            default -> {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    this.clipboardStack.push(model.getSelectionText());
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
                    this.clipboardStack.push(model.getSelectionText());
                    undoManager.performEditAction(new DeleteBeforeAction(model));
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    if (clipboardStack.isEmpty())
                        return;
                    if (e.isShiftDown()) {
                        String text = clipboardStack.pop();
                        undoManager.performEditAction(new InsertAction(model, text));
                    } else {
                        String text = clipboardStack.peek();
                        undoManager.performEditAction(new InsertAction(model, text));
                    }
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
                    undoManager.undo();
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
                    undoManager.redo();
                } else {
                    if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                        undoManager.performEditAction(new InsertAction(model, e.getKeyChar()));
                    }
                }
                // System.out.println(clipboardStack.toString());
            }
        }
        Location newCursorLocation = model.getCursorLocation();
        boolean shiftPressed = e.isShiftDown();
        updateSelection(shiftPressed, newCursorLocation);

    }

    public void updateSelection(boolean shiftPressed, Location newCursorLocation) {
        if (shiftPressed) {
            Location start = model.getSelectionRange().getStart();
            model.setSelectionRange(new LocationRange(start, newCursorLocation));
        } else {
            model.setSelectionRange(new LocationRange(newCursorLocation, newCursorLocation));
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void updateCursorLocation(Location loc) {
        Graphics2D g = (Graphics2D) getGraphics();
        String row = model.linesRange(loc.getRow(), loc.getRow() + 1).next();
        cursorX = 0;
        for (int i = 0; i < loc.getColumn(); i++) {
            cursorX += g.getFontMetrics().charWidth(row.charAt(i));
        }
        cursorY = g.getFontMetrics().getHeight() * loc.getRow();
        repaint();
    }

    @Override
    public void updateText() {
        // System.out.println("selection changed");
        // repaint();
    }

    @Override
    public void updateClipboard() {
        // System.out.println("clipboard changed");
        // check clipboardStack.isEmpty(); and disable/enable paste ... idk anymore to much work for 5 points
        // repaint();
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Test");
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        final TextEditorModel model = new TextEditorModel("jure je kralj\nabraka dabra\n\n\n123 lp.");
        final TextEditor editor = new TextEditor(model);
        container.add(editor, BorderLayout.CENTER);
        
        // Stvaranje izbornika
        JMenuBar menuBar = new JMenuBar();

        // Izbornik "File"
        JMenu fileMenu = new JMenu("File");

        fileMenu.add(new JMenuItemOpen(frame));
        fileMenu.add(new JMenuItemSave(frame, model));
        fileMenu.add(new JMenuItemExit(frame));

        // Izbornik "Edit"
        JMenu editMenu = new JMenu("Edit");

        editMenu.add(new JMenuItemUndo(frame, model));
        editMenu.add(new JMenuItemRedo(frame, model));
        editMenu.add(new JMenuItemCut(frame, model, editor.getClipboardStack()));
        editMenu.add(new JMenuItemCopy(frame, model, editor.getClipboardStack()));
        editMenu.add(new JMenuItemPaste(frame, model, editor.getClipboardStack()));
        editMenu.add(new JMenuItemPasteAndTake(frame, model, editor.getClipboardStack()));
        editMenu.add(new JMenuItemDeleteSection(frame, model));
        // editMenu.add(new JMenuItemClear(frame, model));

        JMenu moveMenu = new JMenu("Move");
        moveMenu.add( new JMenuItemCursorStart(frame, model));
        moveMenu.add( new JMenuItemCursorEnd(frame, model));

        JMenu pluginsMenu = new JMenu("Plugins");

        File pluginsDir = new File("src/main/java/com/example/dz02/plugins");
        // jar cf StatisticsPlugin.jar StatisticsPlugin.class 
        for (File jar : pluginsDir.listFiles()) {
            System.out.println(jar.getName());
            try {
                // this is the package name for the plugin "com.example.dz02.xxx"
                String jarName = "com.example.dz02." + jar.getName().split(".jar")[0];
                Plugin plugin = (Plugin) Class.forName(jarName).getDeclaredConstructor().newInstance();
                JMenuItem pluginItem = new JMenuItem(plugin.getName());
                pluginItem.addActionListener(e ->  {
                        plugin.execute(model, null, null);
                });
                pluginsMenu.add(pluginItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

       
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(moveMenu);
        menuBar.add(pluginsMenu);

        frame.setJMenuBar(menuBar);

        JToolBar toolbar = new JToolBar();

        toolbar.add(new JButtonAction("Undo", editor, new JMenuItemUndo(frame, model)));
        toolbar.add(new JButtonAction("Redo",editor, new JMenuItemRedo(frame, model)));
        toolbar.add(new JButtonAction("Cut", editor,new JMenuItemCut(frame, model, editor.getClipboardStack())));
        toolbar.add(new JButtonAction("Copy", editor,new JMenuItemCopy(frame, model, editor.getClipboardStack())));
        toolbar.add(new JButtonAction("Paste", editor,new JMenuItemPaste(frame, model, editor.getClipboardStack())));

        frame.add(toolbar, BorderLayout.NORTH);

        MyJLabel statusBar = new MyJLabel();
        model.addCursorObserver(statusBar);
        container.add(statusBar, BorderLayout.SOUTH);


        SwingUtilities.invokeLater(() -> {
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            editor.requestFocusInWindow();
        });
    }

    

    public TextEditorModel getModel() {
        return model;
    }

    public ClipboardStack getClipboardStack() {
        return clipboardStack;
    }

}
