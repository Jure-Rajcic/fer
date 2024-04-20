package com.example.dz01;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Component extends JComponent  implements KeyListener {

    public Component() {
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(java.awt.Color.RED);
        g.setStroke(new BasicStroke(1));
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        int x = 10, y = g.getFontMetrics().getHeight();
        String[] rows = new String[] {"Hello world 1", "Hello world2"};
        for (String row : rows) {
            g.drawString(row, x, y);
            y += g.getFontMetrics().getHeight();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Enter pressed, disposing JFrame ...");
            SwingUtilities.getWindowAncestor(this).dispose();;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
