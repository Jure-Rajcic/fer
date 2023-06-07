package com.example.dz02.interfaces;

import com.example.dz02.ClipboardStack;
import com.example.dz02.TextEditorModel;
import com.example.dz02.UndoManager;

public interface Plugin {
    String getName(); // ime plugina (za izbornicku stavku)
    String getDescription(); // kratki opis
    void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);
}