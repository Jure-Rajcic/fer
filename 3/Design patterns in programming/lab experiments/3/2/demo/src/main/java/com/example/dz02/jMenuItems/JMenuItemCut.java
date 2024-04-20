package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.ClipboardStack;
import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;
import com.example.dz02.commands.DeleteBeforeAction;

public class JMenuItemCut extends JMenuItemBase{

    private ClipboardStack clipboardStack;

    
        public JMenuItemCut(JFrame frame, TextEditorModel model, ClipboardStack clipboardStack) {
            super("Cut", frame, model);
            this.clipboardStack = clipboardStack;
        }
    
        @Override
        public void action() {
            UndoManager um = UndoManager.getInstance();
            clipboardStack.push(model.getSelectionText());
            um.performEditAction(new DeleteBeforeAction(model));
        }
    
}
