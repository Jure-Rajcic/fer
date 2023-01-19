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

public class CopyAction extends LocalizableAction {
    private static String KEYWORD = "copy";
    private JNotepadPP notepad;

    public CopyAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        this.putValue(Action.SHORT_DESCRIPTION, "Copies the selected portion of text.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        if (dmdm.getCurrentDocument() == null)
            return;
        final JTextArea jTextArea = dmdm.getCurrentDocument().getTextComponent();
        int len = Math.abs(jTextArea.getCaret().getDot() - jTextArea.getCaret().getMark());
        if (len == 0)
            return;
        int offset = Math.min(jTextArea.getCaret().getDot(), jTextArea.getCaret().getMark());
        try {
            notepad.setCopyPaste(jTextArea.getDocument().getText(offset, len));
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
    }

}
