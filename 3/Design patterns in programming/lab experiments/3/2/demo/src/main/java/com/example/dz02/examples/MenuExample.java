package com.example.dz02.examples;

import javax.swing.*;

public class MenuExample {

    public static void main(String[] args) {
        // Stvaranje glavnog prozora
        JFrame frame = new JFrame("Primjer izbornika");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Stvaranje izbornika
        JMenuBar menuBar = new JMenuBar();

        // Izbornik "File"
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // Izbornik "Edit"
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem pasteAndTakeItem = new JMenuItem("Paste and Take");
        JMenuItem deleteSelectionItem = new JMenuItem("Delete selection");
        JMenuItem clearDocumentItem = new JMenuItem("Clear document");
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(pasteAndTakeItem);
        editMenu.add(deleteSelectionItem);
        editMenu.add(clearDocumentItem);

        // Izbornik "Move"
        JMenu moveMenu = new JMenu("Move");
        JMenuItem cursorToStartItem = new JMenuItem("Cursor to document start");
        JMenuItem cursorToEndItem = new JMenuItem("Cursor to document end");
        moveMenu.add(cursorToStartItem);
        moveMenu.add(cursorToEndItem);

        // Dodavanje izbornika glavnom izborniku
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(moveMenu);

        // Postavljanje izbornika na glavni prozor
        frame.setJMenuBar(menuBar);

        

        // Prikazivanje prozora
        frame.setVisible(true);
    }
}
