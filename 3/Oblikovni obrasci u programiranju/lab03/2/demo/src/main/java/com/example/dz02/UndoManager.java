package com.example.dz02;

import java.util.Stack;

import com.example.dz02.interfaces.EditAction;

public class UndoManager {

    private Stack<EditAction> undoStack;
    private Stack<EditAction> redoStack;

    private static UndoManager instance = new UndoManager();

    public static UndoManager getInstance() {
        return instance;
    }

    private UndoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void performEditAction(EditAction action) {
        redoStack.clear();
        undoStack.push(action);
        action.execute_do();
    }

    public void undo() {
        if (undoStack.isEmpty()) return;
        EditAction action = undoStack.pop();
        redoStack.push(action);
        action.execute_undo();
        return;
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        EditAction action = redoStack.pop();
        undoStack.push(action);
        action.execute_do();
        return;
    }

    boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    boolean isRedoStackEmpty() {
        return redoStack.isEmpty();
    }

    
}
