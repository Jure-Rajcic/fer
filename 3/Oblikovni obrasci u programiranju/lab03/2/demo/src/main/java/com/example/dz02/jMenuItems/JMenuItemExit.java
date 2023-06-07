package com.example.dz02.jMenuItems;

import javax.swing.JMenuItem;


import javax.swing.*;
public class JMenuItemExit extends JMenuItem {

    private JFrame frame;
    
    public JMenuItemExit(JFrame frame) {
        super("Exit");
        this.frame = frame;
        addListener();
    }

    
    public void addListener() {
        addActionListener(e -> {
            frame.dispose();
        });
    }

}
