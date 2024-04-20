package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class CloseDocumentAction extends LocalizableAction {
    private static String KEYWORD = "close";
    private JNotepadPP notepad;

    public CloseDocumentAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		this.putValue(Action.SHORT_DESCRIPTION, "Closes the current document.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        final FormLocalizationProvider flp = (FormLocalizationProvider) super.getILocalizationProvider();

        if (dmdm.getCurrentDocument() == null)
            return;
        if (dmdm.getCurrentDocument().isModified()) {
            boolean isNull = dmdm.getCurrentDocument().getFilePath() == null;
            int result = JOptionPane.showOptionDialog(notepad,
                    flp.getString("document") + " "
                            + (isNull ? "(unnamed) " : dmdm.getCurrentDocument().getFilePath().getFileName() + " ")
                            + flp.getString("wantToSave"),
                    flp.getString("warning") + "!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE, null,
                    new String[] { flp.getString("yes"), flp.getString("no"), flp.getString("cancel") }, 
                    null);
            switch (result) {
                case JOptionPane.YES_OPTION -> {
                    Path filePath = dmdm.getCurrentDocument().getFilePath();
                    if (filePath == null) {
                        JFileChooser fc = new JFileChooser();
                        fc.setDialogTitle(flp.getString("saveAs"));
                        if (fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
                            JOptionPane.showMessageDialog(
                                    notepad,
                                    flp.getString("document") + " "
                                            + (isNull ? "(unnamed) "
                                                    : dmdm.getCurrentDocument().getFilePath().getFileName() + " ")
                                            + flp.getString("notSaved"),
                                    flp.getString("warning") + "!",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        filePath = fc.getSelectedFile().toPath();
                    }
                    dmdm.saveDocument(dmdm.getCurrentDocument(), filePath);
                    dmdm.closeDocument(dmdm.getCurrentDocument());
                    return;
                }
                case JOptionPane.NO_OPTION -> {
                    dmdm.closeDocument(dmdm.getCurrentDocument());
                    return;
                    // System.out.println("no optioned");
                }
                case JOptionPane.CANCEL_OPTION -> {
                    return;
                    // System.out.println("cancle optioned");
                }
            }
        } else {
            dmdm.closeDocument(dmdm.getCurrentDocument());
        }
    }
}
