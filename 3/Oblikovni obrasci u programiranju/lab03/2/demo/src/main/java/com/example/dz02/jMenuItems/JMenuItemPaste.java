package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.ClipboardStack;
import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;
import com.example.dz02.commands.InsertAction;

public class JMenuItemPaste extends JMenuItemBase {

    private ClipboardStack clipboardStack;

    public JMenuItemPaste(JFrame frame, TextEditorModel model, ClipboardStack clipboardStack) {
        super("Paste", frame, model);
        this.clipboardStack = clipboardStack;
    }

    @Override
    public void action() {
        UndoManager um = UndoManager.getInstance();
        if (clipboardStack.isEmpty())
            return;
        String text = clipboardStack.peek();
        um.performEditAction(new InsertAction(model, text));
    }

}
