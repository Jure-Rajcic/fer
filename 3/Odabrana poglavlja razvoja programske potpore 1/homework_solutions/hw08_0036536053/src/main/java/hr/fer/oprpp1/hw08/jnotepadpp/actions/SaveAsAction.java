
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import java.awt.event.InputEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class SaveAsAction extends LocalizableAction {
    private static String KEYWORD = "saveAs";
    private JNotepadPP notepad;

    public SaveAsAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
            KeyEvent.VK_S,
            InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK,
            false));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        this.putValue(Action.SHORT_DESCRIPTION, "Used to save the document to any path.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();

        if (dmdm.getNumberOfDocuments() == 0)
            return;
        JFileChooser fc = new JFileChooser();
        FormLocalizationProvider flp = notepad.getFormLocalizationProvider();
        fc.setDialogTitle(flp.getString(KEYWORD));
        if (fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(
                    notepad,
                    flp.getString("document") + " " +
                            (dmdm.getCurrentDocument().getFilePath() == null ? "(unnamed)"
                                    : dmdm.getCurrentDocument().getFilePath().getFileName())
                            + " " + flp.getString("notSaved"),
                    flp.getString("warning") + "!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Path filePath = fc.getSelectedFile().toPath();
        if (Files.exists(filePath) && !filePath.equals(dmdm.getCurrentDocument().getFilePath())) {
            JOptionPane.showMessageDialog(
                    notepad,
                    flp.getString("document") + " " + filePath.getFileName().toString() + " "
                            + flp.getString("alreadyExists"),
                    flp.getString("error") + "!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dmdm.saveDocument(dmdm.getCurrentDocument(), filePath);
    }

}
