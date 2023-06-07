package hr.fer.ooup.classes;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JComponent;

import hr.fer.ooup.classes.model_loders.FileModelLoaderImpl;
import hr.fer.ooup.classes.model_savers.FileModelSaverImpl;
import hr.fer.ooup.classes.renderers.G2DRendererImpl;
import hr.fer.ooup.classes.renderers.SVGRendererImpl;
import hr.fer.ooup.interfaces.DocumentModelListener;
import hr.fer.ooup.interfaces.Renderer;

import hr.fer.ooup.classes.shape.LineSegment;
import hr.fer.ooup.classes.shape.Oval;
import hr.fer.ooup.classes.states.AddShapeState;
import hr.fer.ooup.classes.states.EraserState;
import hr.fer.ooup.classes.states.IdleState;
import hr.fer.ooup.classes.states.SelectShapeState;
import hr.fer.ooup.interfaces.*;

import java.awt.BorderLayout;
import java.awt.Container;

import java.util.*;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Canvas canvas;
    private State currentState;

    public GUI(List<GraphicalObject> objects) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);

        this.canvas = new Canvas(new DocumentModel());
        initGUI(objects);

        this.currentState = new IdleState();
    }


    private void initGUI(List<GraphicalObject> objects) {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true); 
        List<JButton> toolButtons = getToolButtons(objects);
        toolButtons.forEach(button -> toolBar.add(button));
        add(toolBar, BorderLayout.NORTH);
        
        cp.add(this.canvas, BorderLayout.CENTER);


    }

    private List<JButton> getToolButtons(List<GraphicalObject> objects) {
        List<JButton> buttons = new LinkedList<>();

        objects.forEach(go -> {
            JButton shapeButton = new JButton(go.getShapeName());
            shapeButton.addActionListener(e -> {
                currentState.onLeaving();
                currentState = new AddShapeState(canvas.document, go);
            });
            buttons.add(shapeButton);
        });


        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            currentState.onLeaving();
            currentState = new SelectShapeState(canvas.document);
        });
        buttons.add(selectButton);


        JButton eraseButton = new JButton("Erase");
        eraseButton.addActionListener(e -> {
            currentState.onLeaving();
            currentState = new EraserState(canvas.document);
        });
        buttons.add(eraseButton);


        JButton svgExportButton = new JButton("SVG export");
        svgExportButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "Enter the file name (without extension):");
            if (fileName == null) return;
            SVGRendererImpl r = new SVGRendererImpl(fileName, getWidth(), getHeight());
            canvas.document.list().forEach(go -> go.render(r));
            try {
                r.close();
            } catch (IOException ex) {
                System.out.println("Error while exporting to SVG");
                ex.printStackTrace();
            } 
        });
        buttons.add(svgExportButton);


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "Enter the file name (without extension):");
            if (fileName == null) return;

            FileModelSaverImpl saver = new FileModelSaverImpl(fileName);
            List<String> rows = new LinkedList<>();
            canvas.document.list().forEach(go -> go.save(rows));
            try {
                saver.save(rows);
            } catch (IOException ex) {
                System.out.println("Error while saving model to file");
                ex.printStackTrace();
            }
        });
        buttons.add(saveButton);


        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            currentState.onLeaving();
            currentState = new IdleState();

            FileModelLoaderImpl loader = new FileModelLoaderImpl();
            String fileName = loader.selectFile();
            if (fileName == null) return;
            
            Stack<GraphicalObject> stack = new Stack<>();
            try {
                loader.setFileName(fileName);
                loader.load(stack);
                canvas.document.clear();
                while (!stack.isEmpty()) {
                    canvas.document.addGraphicalObject(stack.pop());
                }
            } catch (IOException ex) {
                System.out.println("Error while loading model from file");
                ex.printStackTrace();
            }
        });
        buttons.add(loadButton);

        return buttons;
    }

    public class Canvas extends JComponent implements DocumentModelListener {
        private static final long serialVersionUID = 1L;
        private final DocumentModel document;

        public Canvas(DocumentModel document) {
            this.document = document;
            document.addDocumentModelListener(this);
            setFocusable(true);
            registerKeyListeners();
            registerMouseListeners();
        }

        private void registerKeyListeners() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ESCAPE: {
                            currentState = new IdleState();
                            break;
                        }
                        default: {
                            currentState.keyPressed(e.getKeyCode());
                            break;
                        }
                    }
                    e.consume();
                    canvas.repaint();
                }
            });
        }

        private void registerMouseListeners() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentState.mouseDown(e.getPoint(), e.isShiftDown(), e.isControlDown());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    currentState.mouseUp(e.getPoint(), e.isShiftDown(), e.isControlDown());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    requestFocusInWindow();
                }

            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    currentState.mouseDragged(e.getPoint());
                }
            });

        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            Graphics2D g2d = (Graphics2D) g;
            Renderer r = new G2DRendererImpl(g2d);
            for (GraphicalObject go : document.list()) {
                go.render(r);
                currentState.afterDraw(r, go);
            }
            currentState.afterDraw(r);
        }

        @Override
        public void documentChange() {
            repaint();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            List<GraphicalObject> objects = new ArrayList<>();
            objects.add(new LineSegment());
            objects.add(new Oval());

            GUI gui = new GUI(objects);
            gui.setVisible(true);
        });
    }

}
