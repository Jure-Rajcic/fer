package com.example.dz02.commands;

import com.example.dz02.TextEditorModel;

import com.example.dz02.Location;
import com.example.dz02.LocationRange;

public class InsertAction extends AbstractAction {

    public InsertAction(TextEditorModel model, String text) {
        super(model);
        this.text = text;
    }

    public InsertAction(TextEditorModel model, char c) {
        this(model, String.valueOf(c));
    }

    @Override
    public void execute_do() {
        model.insert(text);
    }

    @Override
    public void execute_undo() {
        Location start = new Location(cursorLocation.getRow(), cursorLocation.getColumn());
        Location end;
        if (text.equals("\n")) {
            end = new Location(start.getRow() + 1, 0);
        } else {
            end = new Location(cursorLocation.getRow(), cursorLocation.getColumn() + text.length());
        }
        LocationRange range = new LocationRange(start, end);
        System.out.println("delete range " + range);
        model.deleteRange(range);
    }
    
}
