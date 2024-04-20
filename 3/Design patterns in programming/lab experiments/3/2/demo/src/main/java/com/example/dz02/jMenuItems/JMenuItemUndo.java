package com.example.dz02.jMenuItems;

import javax.swing.JFrame;
import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;

public class JMenuItemUndo extends JMenuItemBase{

    public JMenuItemUndo(JFrame frame, TextEditorModel model) {
        super("Undo", frame, model);
    }

    @Override
    public void action() {
        UndoManager.getInstance().undo();
    }
    
    
}
