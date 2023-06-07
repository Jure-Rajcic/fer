package hr.fer.ooup.classes.tools;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import hr.fer.ooup.interfaces.Tool;

public abstract class ToolAdapter implements Tool {

    @Override
    public void stateActivated() {
    }

    @Override
    public void stateDeactivated() {
    }

    @Override
    public void draw(Graphics2D g) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
}
