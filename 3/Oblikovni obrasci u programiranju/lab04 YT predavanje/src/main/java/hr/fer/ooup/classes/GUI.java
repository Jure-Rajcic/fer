package hr.fer.ooup.classes;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hr.fer.ooup.classes.renderers.G2DRenderer;
import hr.fer.ooup.classes.renderers.Renderers;
import hr.fer.ooup.classes.renderers.SVGRenderer;
import hr.fer.ooup.classes.shapes.Circle;
import hr.fer.ooup.classes.shapes.Square;
import hr.fer.ooup.classes.tools.AddCircleTool;
import hr.fer.ooup.classes.tools.AddSquareTool;
import hr.fer.ooup.classes.tools.EditingTool;

import java.awt.Point;
import hr.fer.ooup.interfaces.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;


    private List<GeometricShape> document = new ArrayList<>();
    private Canvas canvas;
    private Tool selectedTool;
    private List<ToolButton> toolButtons = new ArrayList<>();

    public GUI () {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);

        initGUI();
        initDocument();
    }

    

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        this.canvas = new Canvas();
        cp.add(canvas, BorderLayout.CENTER);

        toolButtons.add(new ToolButton(new AddCircleTool(document, canvas), "Circle"));
        toolButtons.add(new ToolButton(new AddSquareTool(document, canvas), "Square"));
        toolButtons.add(new ToolButton(new EditingTool(document, canvas), "Edit"));

        JPanel toolsPanel = new JPanel();
        for(ToolButton button : toolButtons) {
            toolsPanel.add(button);
        }
        cp.add(toolsPanel, BorderLayout.PAGE_START);
        toolButtons.get(0).activateTool();
    }

    public class Canvas extends JComponent {
        private static final long serialVersionUID = 1L;

        public Canvas() {
            setFocusable(true);
            registerKeyListeners();
            registerMouseListeners();
        }

        private void registerKeyListeners() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (selectedTool != null) selectedTool.keyPressed(e);
                }
            });
        }
       

        private void registerMouseListeners() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedTool != null) selectedTool.mouseClicked(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedTool != null) selectedTool.mousePressed(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                   if (selectedTool != null) selectedTool.mouseReleased(e);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (selectedTool != null) selectedTool.mouseDragged(e);
                };
            });

        }
        
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            ((G2DRenderer)Renderers.getDefaultRenderer()).setG2d(g2d);

            for (GeometricShape shape : document) {
                shape.draw();
            }

            if (selectedTool != null) selectedTool.draw(g2d);

        }
    }

    private class ToolButton extends JLabel {
        private static final long serialVersionUID = 1L;
        private Tool tool;

        public ToolButton(Tool tool, String title) {
            super(title);
            this.tool = tool;
            setBorder(BorderFactory.createEtchedBorder());
            setBackground(Color.YELLOW);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    activateTool();
                }
            });

        }

        public void activateTool() {
            for (ToolButton button : toolButtons) {
                if (button.tool == selectedTool) {
                    button.setOpaque(false);
                    button.repaint();
                }
            }
            if (selectedTool != null) {
                selectedTool.stateDeactivated();
            }
            selectedTool = tool;
            tool.stateActivated();

            setOpaque(true);
            repaint();
        }
    }

    private void initDocument() {
        document.clear();
        document.add(new Circle(new Point(100, 100), 20));
        document.add(new Circle(new Point(100, 150), 20));
        document.add(new Circle(new Point(150, 100), 20));
        document.add(new Circle(new Point(150, 150), 20));
        document.add(new Square(new Point(200, 150), 50));
    }


    // public static void main(String[] args) {
    //     Renderers.setRenderer(new G2DRenderer());

    //     SwingUtilities.invokeLater(() -> {
    //         new GUI().setVisible(true);
    //     });
    // }

    public static void main(String[] args) throws IOException {
        SVGRenderer svgRenderer = new SVGRenderer();
        Renderers.setRenderer(svgRenderer);

        List<GeometricShape> document = new ArrayList<>();
        document.add(new Circle(new Point(100, 100), 20));
        document.add(new Circle(new Point(100, 150), 20));
        document.add(new Circle(new Point(150, 100), 20));
        document.add(new Circle(new Point(150, 150), 20));
        document.add(new Square(new Point(200, 150), 50));
        document.forEach(shape -> shape.draw());

        Files.writeString(Path.of("pic.svg"), svgRenderer.getSVG());



    }

    
}
