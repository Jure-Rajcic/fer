package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class PasteAction extends LocalizableAction {
    private static String KEYWORD = "paste";
    private JNotepadPP notepad;

    public PasteAction(JNotepadPP notepad) {
        super(KEYWORD,notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        this.putValue(Action.SHORT_DESCRIPTION, "Pastes the copied text into selected position.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        if (dmdm.getCurrentDocument() == null)
            return;
        final JTextArea jTextArea = dmdm.getCurrentDocument().getTextComponent();
        int len = Math.abs(jTextArea.getCaret().getDot() - jTextArea.getCaret().getMark());
        int offset = Math.min(jTextArea.getCaret().getDot(), jTextArea.getCaret().getMark());
        try {
            jTextArea.getDocument().remove(offset, len);
            jTextArea.getDocument().insertString(offset, notepad.getCopyPaste(), null);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

}
