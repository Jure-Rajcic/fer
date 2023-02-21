package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public class OpenDocumentAction extends LocalizableAction {
    private static String KEYWORD = "open";
    private JNotepadPP notepad;

    public OpenDocumentAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		this.putValue(Action.SHORT_DESCRIPTION, "Used to open existing document from disk.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        FormLocalizationProvider flp = (FormLocalizationProvider) super.getILocalizationProvider();
        fc.setDialogTitle(flp.getString(KEYWORD));
        if (fc.showOpenDialog(this.notepad) != JFileChooser.APPROVE_OPTION)
            return;
        File fileName = fc.getSelectedFile();
        Path filePath = fileName.toPath();
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        SingleDocumentModel current = dmdm.loadDocument(filePath);
        if (current == null) {
            JOptionPane.showMessageDialog(notepad,
                    flp.getString("errorLoading") + " " + fileName.getAbsolutePath() + ".",
                    flp.getString("error") + "!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
