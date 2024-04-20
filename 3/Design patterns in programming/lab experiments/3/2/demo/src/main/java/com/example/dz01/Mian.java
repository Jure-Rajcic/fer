package com.example.dz01;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Mian 
{
    public static void main( String[] args ){
        final JFrame frame = new JFrame("Test");
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new Component(), BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
