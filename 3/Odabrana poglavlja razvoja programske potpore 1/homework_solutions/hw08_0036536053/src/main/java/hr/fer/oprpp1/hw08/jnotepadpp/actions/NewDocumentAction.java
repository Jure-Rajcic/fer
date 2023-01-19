
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public class NewDocumentAction extends LocalizableAction {
    private static String KEYWORD = "new";
    private JNotepadPP notepad;

    public NewDocumentAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		this.putValue(Action.SHORT_DESCRIPTION, "Used to create new document.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        dmdm.createNewDocument();
    }

}
