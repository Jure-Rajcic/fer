package hr.fer.ooup.interfaces;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Tool {

    void stateActivated();
    void stateDeactivated();

    void draw(Graphics2D g);

    void keyPressed(KeyEvent e);
    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseDragged(MouseEvent e);
    
}
