
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class SaveAction extends LocalizableAction {
    private static String KEYWORD = "save";
    private JNotepadPP notepad;

    public SaveAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(Action.SHORT_DESCRIPTION, "Used to save the document.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        if (dmdm.getNumberOfDocuments() == 0)
            return;
        SingleDocumentModel m = dmdm.getCurrentDocument();
        if (m.getFilePath() == null)
            notepad.getSaveAsAction().actionPerformed(e);
        else
            dmdm.saveDocument(m, m.getFilePath());

    }

}
