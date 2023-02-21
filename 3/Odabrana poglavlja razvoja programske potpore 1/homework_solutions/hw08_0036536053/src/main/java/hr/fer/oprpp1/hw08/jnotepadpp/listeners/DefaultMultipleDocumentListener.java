package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public class DefaultMultipleDocumentListener implements MultipleDocumentListener {

    private JNotepadPP notepad;

    public DefaultMultipleDocumentListener(){}

    public DefaultMultipleDocumentListener(JNotepadPP notepad) {
        this.notepad = notepad;
    }

    @Override
    public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        if (previousModel == null && currentModel == null) {
            JOptionPane.showMessageDialog(notepad,
                    "Error while changing the current document",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (previousModel != null) {
            previousModel.removeSingleDocumentListener(notepad.getSingleDocumentListener());
            previousModel.getTextComponent().removeCaretListener(notepad.getCaretListener());
        }
        if (currentModel != null) {
            currentModel.addSingleDocumentListener(notepad.getSingleDocumentListener());
            currentModel.getTextComponent().addCaretListener(notepad.getCaretListener());
        }
        String text = currentModel.getFilePath() == null ? "(unnnamed) - JNotepad++"
                : (currentModel.getFilePath().toString() + " - JNotepad++");
        notepad.setTitle(text);

    }

    @Override
    public void documentAdded(SingleDocumentModel sdm) {
        boolean isNull = sdm.getFilePath() == null;
        String text = isNull ? "(unnnamed) - JNotepad++" : (sdm.getFilePath().toString() + " - JNotepad++");
        notepad.setTitle(text);
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        dmdm.addTab(!isNull ? sdm.getFilePath().getFileName().toString() : "(unnamed)", sdm.getTextComponent());
        dmdm.setSelectedIndex(dmdm.getNumberOfDocuments() - 1);
        dmdm.setToolTipTextAt(dmdm.getSelectedIndex(), !isNull ? sdm.getFilePath().toString() : "(unnamed)");
        sdm.addSingleDocumentListener(notepad.getSingleDocumentListener());
        sdm.getTextComponent().addCaretListener(notepad.getCaretListener());

    }

    @Override
    public void documentRemoved(SingleDocumentModel sdm) {
        sdm.removeSingleDocumentListener(notepad.getSingleDocumentListener());
        sdm.getTextComponent().removeCaretListener(notepad.getCaretListener());
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        int index = dmdm.getSelectedIndex();
        if (index == -1) {
            notepad.setTitle("JNotepad++");
            return;
        }
        String text = dmdm.getDocument(index).getFilePath() == null
                ? "(unnnamed) - JNotepad++"
                : (dmdm.getDocument(index).getFilePath().toString() + " - JNotepad++");
        notepad.setTitle(text);

    }

}
