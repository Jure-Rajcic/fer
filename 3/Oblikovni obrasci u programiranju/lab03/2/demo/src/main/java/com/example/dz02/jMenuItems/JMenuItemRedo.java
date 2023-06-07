package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;

public class JMenuItemRedo extends JMenuItemBase{

    public JMenuItemRedo(JFrame frame, TextEditorModel model) {
        super("Redo", frame, model);
    }

    @Override
    public void action() {
        UndoManager.getInstance().redo();
    }
    
}

