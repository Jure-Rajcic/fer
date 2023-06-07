package com.example.dz02.commands;

import java.util.Iterator;

import com.example.dz02.Location;
import com.example.dz02.LocationRange;
import com.example.dz02.TextEditorModel;

/*
 * 
 * MA PREVISE VRIMENA SAM POROSIA NA DELETE BEFORE TAKO DA MI SE OVO UOBCE NEDA RADIT (-_-)
 */
public class DeleteAfterAction extends AbstractAction{

    private final Location start;
    private final Location end;

    public DeleteAfterAction(TextEditorModel model) {
        super(model);
        if (model.getSelectionText().isEmpty()) {
            int row = cursorLocation.getRow();
            int column = cursorLocation.getColumn();
            Iterator<String> it = model.linesRange(row, row + 1);
            String line = it.next();
            if (column == line.length()) {
                if (row == line.length() - 1) {
                    this.text = "";
                } else {
                    this.text = "\n";
                }
            } else {
                this.text = line.substring(column, column + 1);
            }
            model.setSelectionRange(new LocationRange(new Location(row, column - 1), cursorLocation));
        } 
        this.text =  model.getSelectionText();
        this.start = model.getSelectionRange().getStart();
        this.end = model.getSelectionRange().getEnd();
        this.cursorLocation = start;
    }

    @Override
    public void execute_do() {
        model.setSelectionRange(new LocationRange(start, end));
        model.deleteAfter();
    }

    @Override
    public void execute_undo() {
        model.setCursorLocation(cursorLocation);
        model.insert(text);
    }
    
}
