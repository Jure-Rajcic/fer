package com.example.dz02;

import javax.swing.JLabel;

import com.example.dz02.interfaces.CursorObserver;
import java.awt.Color;

public class MyJLabel extends JLabel implements CursorObserver{
    
    public MyJLabel() {
        super("Row: 0 Column: 0");
        this.setBackground(Color.GRAY);
        this.setOpaque(true);
    }

    @Override
    public void updateCursorLocation(Location loc) {
        int row = loc.getRow();
        int column = loc.getColumn();
        this.setText("Row: " + row + " Column: " + column);
        revalidate();
        repaint();
    }
    
}
