package com.example.dz02.jMenuItems;

import javax.swing.JMenuItem;

import com.example.dz02.TextEditorModel;

import javax.swing.*;
import java.io.*;
import java.util.Iterator;
public class JMenuItemSave extends JMenuItem {

    private JFrame frame;
    private TextEditorModel model;
    
    public JMenuItemSave(JFrame frame, TextEditorModel model) {
        super("Save");
        this.frame = frame;
        this.model = model;
        addListener();
    }

    public void addListener() {
        addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("/Users/jrajcic/Desktop/obrazci/lab03/2/demo/example");
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                // Now use fileToSave to write your data, for example:
                Iterator<String> it = model.allLines();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                    while(it.hasNext()) {
                        String line = it.next() + "\n";
                        writer.write(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            frame.revalidate();
        });
    }

}
