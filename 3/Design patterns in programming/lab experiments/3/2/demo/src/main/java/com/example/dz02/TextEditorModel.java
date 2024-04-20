package com.example.dz02;

import java.util.*;

import com.example.dz02.interfaces.CursorObserver;
import com.example.dz02.interfaces.TextObserver;

public class TextEditorModel {

    private List<String> lines;
    private LocationRange selectionRange;
    private Location cursorLocation;

    private List<CursorObserver> cursorObservers;
    private List<TextObserver> textObservers;

    public TextEditorModel(String text) {
        lines = new ArrayList<>(Arrays.asList(text.split("\n")));
        cursorLocation = new Location(0, 0);
        this.cursorObservers = new LinkedList<>();
        this.textObservers = new LinkedList<>();
        this.selectionRange = new LocationRange(cursorLocation, cursorLocation);
    }

    // 2.3

    public Iterator<String> allLines() {
        return new IteratorImpl(0, lines.size());
    }

    public Iterator<String> linesRange(int start, int end) {
        if (start < 0 || end < 0 || start > end || start > lines.size() || end > lines.size())
            throw new IllegalArgumentException("Invalid range");
        return new IteratorImpl(start, end);
    }

    private class IteratorImpl implements Iterator<String> {

        private int current, end;

        public IteratorImpl(int start, int end) {
            this.current = start;
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return current < end;
        }

        @Override
        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return TextEditorModel.this.lines.get(current++);
        }

    }

    // 2.4

    public void addCursorObserver(CursorObserver observer) {
        this.cursorObservers.add(observer);
    }

    public void removeCursorObserver(CursorObserver observer) {
        this.cursorObservers.remove(observer);
    }

    public void notifyCursorObservers() {
        for (CursorObserver observer : this.cursorObservers) {
            observer.updateCursorLocation(cursorLocation);
        }
    }

    public void moveCursorLeft() {
        if (cursorLocation.getColumn() == 0 && cursorLocation.getRow() == 0)
            return;
        if (cursorLocation.getColumn() == 0) {
            cursorLocation = new Location(cursorLocation.getRow() - 1, lines.get(cursorLocation.getRow() - 1).length());
        } else {
            cursorLocation = new Location(cursorLocation.getRow(), cursorLocation.getColumn() - 1);
        }
        notifyCursorObservers();
    }

    public void moveCursorRight() {
        if (cursorLocation.getColumn() == lines.get(cursorLocation.getRow()).length()
                && cursorLocation.getRow() == lines.size() - 1)
            return;
        if (cursorLocation.getColumn() == lines.get(cursorLocation.getRow()).length()) {
            cursorLocation = new Location(cursorLocation.getRow() + 1, 0);
        } else {
            cursorLocation = new Location(cursorLocation.getRow(), cursorLocation.getColumn() + 1);
        }
        notifyCursorObservers();
    }

    public void moveCursorUp() {
        if (cursorLocation.getRow() == 0)
            return;
        int row = cursorLocation.getRow() - 1;
        int column = Math.min(cursorLocation.getColumn(), lines.get(row).length());
        cursorLocation = new Location(row, column);
        notifyCursorObservers();
    }

    public void moveCursorDown() {
        if (cursorLocation.getRow() == lines.size() - 1)
            return;
        int row = cursorLocation.getRow() + 1;
        int column = Math.min(cursorLocation.getColumn(), lines.get(row).length());
        cursorLocation = new Location(row, column);
        notifyCursorObservers();
    }

    // 2.5
    public void addTextObserver(TextObserver observer) {
        this.textObservers.add(observer);
    }

    public void removeTextObserver(TextObserver observer) {
        this.textObservers.remove(observer);
    }

    public void notifyTextObservers() {
        for (TextObserver observer : this.textObservers) {
            observer.updateText();
        }
        notifyCursorObservers();
    }

    public void deleteBefore() {
        if (cursorLocation.getColumn() == 0 && cursorLocation.getRow() == 0)
            return;
        if (!selectionRange.getStart().equals(selectionRange.getEnd())) {
            deleteRange(selectionRange);
            notifyTextObservers();
            return;
        }
        String row = lines.get(cursorLocation.getRow());
        if (cursorLocation.getColumn() == 0) {
            String firstPart = lines.get(cursorLocation.getRow() - 1);
            String newLine = firstPart + row;
            lines.set(cursorLocation.getRow() - 1, newLine);
            lines.remove(cursorLocation.getRow());
            cursorLocation = new Location(cursorLocation.getRow() - 1, firstPart.length());
        } else {
            String newLine = row.substring(0, cursorLocation.getColumn() - 1)
                    + row.substring(cursorLocation.getColumn());
            lines.set(cursorLocation.getRow(), newLine);
            cursorLocation = new Location(cursorLocation.getRow(), cursorLocation.getColumn() - 1);
        }
        notifyTextObservers();
    }

    public void deleteAfter() {
        if (cursorLocation.getColumn() == lines.get(cursorLocation.getRow()).length()
                && cursorLocation.getRow() == lines.size() - 1)
            return;
        if (!selectionRange.getStart().equals(selectionRange.getEnd())) {
            deleteRange(selectionRange);
            notifyTextObservers();
            return;
        }
        String row = lines.get(cursorLocation.getRow());
        if (cursorLocation.getColumn() == row.length()) {
            String newLine = row + lines.get(cursorLocation.getRow() + 1);
            lines.set(cursorLocation.getRow(), newLine);
            lines.remove(cursorLocation.getRow() + 1);
        } else {
            String newLine = row.substring(0, cursorLocation.getColumn())
                    + row.substring(cursorLocation.getColumn() + 1);
            lines.set(cursorLocation.getRow(), newLine);
        }
        notifyTextObservers();
    }

    public void deleteRange(LocationRange r) {
        System.out.println("Deleting range");
        if (r.getStart().getRow() == r.getEnd().getRow()) {
            String row = lines.get(r.getStart().getRow());
            String newLine = row.substring(0, r.getStart().getColumn()) + row.substring(r.getEnd().getColumn());
            lines.set(r.getStart().getRow(), newLine);
        } else {
            String row = lines.get(r.getStart().getRow());
            String newLine = row.substring(0, r.getStart().getColumn())
                    + lines.get(r.getEnd().getRow()).substring(r.getEnd().getColumn());
            lines.set(r.getStart().getRow(), newLine);
            for (int i = r.getStart().getRow() + 1; i <= r.getEnd().getRow(); i++) {
                lines.remove(r.getStart().getRow() + 1);
            }
        }
        cursorLocation = r.getStart();
        notifyTextObservers();
    }
    


    public LocationRange getSelectionRange() {
        return selectionRange;
    }

    

    public void setSelectionRange(LocationRange range) {
        this.selectionRange = range;
        notifyTextObservers();
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    // 2.6
    public void insert(char keyChar) {
        if (!selectionRange.getStart().equals(selectionRange.getEnd())) {
            deleteRange(selectionRange);
        }
        String row = lines.get(cursorLocation.getRow());
        String firstPart = row.substring(0, cursorLocation.getColumn());
        String secondPart = row.substring(cursorLocation.getColumn());
        if (keyChar == '\n') {
            lines.set(cursorLocation.getRow(), firstPart);
            lines.add(cursorLocation.getRow() + 1, secondPart);
            cursorLocation = new Location(cursorLocation.getRow() + 1, 0);
        } else {
            lines.set(cursorLocation.getRow(), firstPart + keyChar + secondPart);
            cursorLocation = new Location(cursorLocation.getRow(), cursorLocation.getColumn() + 1);
        }
        notifyTextObservers();
    }

    public void insert(String text) {
        for (int i = 0; i < text.length(); i++) {
            insert(text.charAt(i));
        }
    }

    public String getSelectionText() {
        LocationRange range = getSelectionRange();
        if (range.getStart().equals(range.getEnd())) {
            return "";
        }
        if (range.getStart().getRow() == range.getEnd().getRow()) {
            return lines.get(range.getStart().getRow()).substring(range.getStart().getColumn(),
                    range.getEnd().getColumn());
        }
        String text = lines.get(range.getStart().getRow()).substring(range.getStart().getColumn()) + "\n";
        for (int i = range.getStart().getRow() + 1; i < range.getEnd().getRow(); i++) {
            text += lines.get(i) + "\n";
        }
        text += lines.get(range.getEnd().getRow()).substring(0, range.getEnd().getColumn());
        return text;
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
        notifyCursorObservers();
    }

   

}
