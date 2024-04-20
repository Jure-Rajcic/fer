package com.example.dz02.jMenuItems;

import java.util.Iterator;

import javax.swing.JFrame;

import com.example.dz02.Location;
import com.example.dz02.TextEditorModel;

public class JMenuItemCursorEnd extends JMenuItemBase{

    public JMenuItemCursorEnd( JFrame frame, TextEditorModel model) {
        super("Cursor to document end", frame, model);
    }

    @Override
    public void action() {
        Iterator<String> lines = model.allLines();
        String lastLine = new String();
        int lastLineIndex = 0;
        while (lines.hasNext()) {
            lastLine = lines.next();
            lastLineIndex++;
        }
        model.setCursorLocation(new Location(lastLineIndex - 1, lastLine.length()));
    }
}
