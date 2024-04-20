package com.example.dz02;

import java.util.Iterator;

import javax.swing.JOptionPane;

import com.example.dz02.interfaces.Plugin;

public class StatisticsPlugin implements Plugin {

    @Override
    public String getName() {
        return "Statistics";
    }

    @Override
    public String getDescription() {
        return "plugin koji broji koliko ima redaka, riječi i slova u dokumentu i to prikazuje korisniku u dijalogu";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        int rows, words, letters;
        rows = words = letters = 0;
        Iterator<String> it = model.allLines();
        while(it.hasNext()) {
            String line = it.next();
            rows++;
            String[] lineWords = line.split("\\s+");
            words += lineWords.length;
            for(String word : lineWords) {
                letters += word.length();
            }
        }
        String message = "Broj redaka: " + rows + "\nBroj riječi: " + words + "\nBroj slova: " + letters;
        JOptionPane.showMessageDialog(null, message);
    }


    // public static void main(String[] args) {
    //     String text = "Ovo je neki tekst\nkoji ima više redaka\ni više riječi\ni više slova";
    //     TextEditorModel model = new TextEditorModel(text);
    //     StatisticsPlugin plugin = new StatisticsPlugin();
    //     plugin.execute(model, null, null);
    // }
    
}
