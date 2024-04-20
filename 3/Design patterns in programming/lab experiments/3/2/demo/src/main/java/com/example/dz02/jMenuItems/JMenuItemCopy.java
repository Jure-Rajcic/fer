package com.example.dz02.jMenuItems;

import javax.swing.JFrame;

import com.example.dz02.ClipboardStack;
import com.example.dz02.TextEditorModel;

public class JMenuItemCopy extends JMenuItemBase {

    private ClipboardStack clipboardStack;

    public JMenuItemCopy(JFrame frame, TextEditorModel model, ClipboardStack clipboardStack) {
        super("Copy", frame, model);
        this.clipboardStack = clipboardStack;
    }

    @Override
    public void action() {
        clipboardStack.push(model.getSelectionText());
    }

}
