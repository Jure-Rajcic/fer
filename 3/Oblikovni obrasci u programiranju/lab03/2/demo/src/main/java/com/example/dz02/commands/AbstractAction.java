package com.example.dz02.commands;

import com.example.dz02.Location;
import com.example.dz02.TextEditorModel;
import com.example.dz02.interfaces.EditAction;

public abstract class AbstractAction implements EditAction{

    protected TextEditorModel model;
    protected Location cursorLocation;
    protected String text;

    public AbstractAction(TextEditorModel model) {
        this.model = model;
        this.cursorLocation = new Location(model.getCursorLocation().getRow(), model.getCursorLocation().getColumn());
    }

}

