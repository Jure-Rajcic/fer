package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.ClipboardStack;
import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;
import com.example.dz02.commands.InsertAction;

public class JMenuItemPasteAndTake extends JMenuItemBase {

    private ClipboardStack clipboardStack;

    public JMenuItemPasteAndTake(JFrame frame, TextEditorModel model, ClipboardStack clipboardStack) {
        super("Paste and Take", frame, model);
        this.clipboardStack = clipboardStack;
    }

    @Override
    public void action() {
        UndoManager um = UndoManager.getInstance();
        if (clipboardStack.isEmpty())
            return;
        String text = clipboardStack.pop();
        um.performEditAction(new InsertAction(model, text));
    }

}
