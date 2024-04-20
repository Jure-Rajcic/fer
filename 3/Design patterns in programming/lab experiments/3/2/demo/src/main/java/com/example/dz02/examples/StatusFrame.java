package com.example.dz02.examples;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

public class StatusFrame extends JFrame {
    private JTextArea textArea;
    private JLabel statusBar;

    public StatusFrame() {
        super("Status Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int position = e.getDot();
                int line = 1;
                try {
                    line = textArea.getLineOfOffset(position) + 1;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                statusBar.setText("Cursor Position: " + position + ", Line: " + line);
            }
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        statusBar = new JLabel("Cursor Position: 0, Line: 1");
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatusFrame());
    }
}
