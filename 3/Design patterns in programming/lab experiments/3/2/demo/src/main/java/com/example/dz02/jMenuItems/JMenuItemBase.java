package com.example.dz02.jMenuItems;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import com.example.dz02.Location;
import com.example.dz02.TextEditorModel;

public abstract class JMenuItemBase extends JMenuItem {

    protected TextEditorModel model;
    private JFrame frame;

    public JMenuItemBase(String text, JFrame frame, TextEditorModel model) {
        super(text);
        this.model = model;
        this.frame = frame;
        addActionListener(e -> templateMethod());
    }

    public void templateMethod() {
        action();
        Location newCursorLocation = model.getCursorLocation();
        model.getSelectionRange().setStart(newCursorLocation);
        model.getSelectionRange().setEnd(newCursorLocation);
        frame.repaint();
    }

    public abstract void action();

}
