package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.Location;
import com.example.dz02.TextEditorModel;

public class JMenuItemCursorStart extends JMenuItemBase{

    public JMenuItemCursorStart( JFrame frame, TextEditorModel model) {
        super("Cursor to document start", frame, model);
    }

    @Override
    public void action() {
        model.setCursorLocation(new Location(0, 0));
    }
    
}
