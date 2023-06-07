package com.example.dz02.commands;

import com.example.dz02.Location;
import com.example.dz02.LocationRange;
import com.example.dz02.TextEditorModel;

import java.util.Iterator;


public class DeleteBeforeAction extends AbstractAction {

    private final Location start;
    private final Location end;


    public DeleteBeforeAction(TextEditorModel model) {
        super(model);
        if (model.getSelectionText().isEmpty()) {
            int row = cursorLocation.getRow();
            int column = cursorLocation.getColumn();
            Iterator<String> it = model.linesRange(row, row + 1);
            String line = it.next();
            System.out.println("DeleteBeforeAction: " + line + " " + column);
            if (column == 0) {
                if (row == 0) {
                    this.text = "";
                } else {
                    this.text = "\n";
                }
            } else {
                this.text = line.substring(column - 1, column);
            }
            model.setSelectionRange(new LocationRange(new Location(row, column - 1), cursorLocation));
        } 
        // this.text =  model.getSelectionText();
        this.start = model.getSelectionRange().getStart();
        this.end = model.getSelectionRange().getEnd();
        this.cursorLocation = start;
    }

    @Override
    public void execute_do() {
        model.setSelectionRange(new LocationRange(start, end));
        model.deleteBefore();
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursorLocation);
        model.insert(text);
    }
    
}
