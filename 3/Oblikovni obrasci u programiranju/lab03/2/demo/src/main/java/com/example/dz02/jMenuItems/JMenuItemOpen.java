package com.example.dz02.jMenuItems;

import javax.swing.JMenuItem;

import com.example.dz02.TextEditor;
import com.example.dz02.TextEditorModel;

import javax.swing.*;
import java.io.*;
public class JMenuItemOpen extends JMenuItem {

    JFrame frame;
    public JMenuItemOpen(JFrame frame) {
        super("Open");
        this.frame = frame;
        addListener();
    }

    public void addListener() {
        addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            JFileChooser fileChooser = new JFileChooser("/Users/jrajcic/Desktop/obrazci/lab03/2/demo/example");
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            TextEditorModel model = new TextEditorModel(sb.toString());
            TextEditor editor = new TextEditor(model);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(editor);
            frame.revalidate();
        });
    }

}
