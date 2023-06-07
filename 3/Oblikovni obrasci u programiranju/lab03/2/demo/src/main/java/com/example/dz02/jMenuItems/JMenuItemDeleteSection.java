package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;
import com.example.dz02.commands.DeleteBeforeAction;

public class JMenuItemDeleteSection extends JMenuItemBase{
    
        public JMenuItemDeleteSection(JFrame frame, TextEditorModel model) {
            super("Delete section", frame, model);
        }
    
        @Override
        public void action() {
            UndoManager um = UndoManager.getInstance();
            um.performEditAction(new DeleteBeforeAction(model));
        }
    
}
