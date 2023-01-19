package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class ExitAction extends LocalizableAction {
    private static String KEYWORD = "exit";
    private JNotepadPP notepad;

    public ExitAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        this.putValue(Action.SHORT_DESCRIPTION, "Closes the JNotepad++");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        FormLocalizationProvider flp = notepad.getFormLocalizationProvider();
        Iterator<SingleDocumentModel> iterator = dmdm.iterator();
        while (iterator.hasNext()) {
            SingleDocumentModel m = iterator.next();
            if (m.isModified()) {
                boolean isNull = m.getFilePath() == null;
                int result = JOptionPane.showOptionDialog(notepad,
                        flp.getString("document") + " " + (isNull ? "(unnamed) " : m.getFilePath().getFileName() + " ")
                                + flp.getString("wantToSave"),
                        flp.getString("warning") + "!",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null,
                        new String[] { flp.getString("yes"), flp.getString("no"), flp.getString("cancel") },
                        null);
                switch (result) {
                    case JOptionPane.YES_OPTION -> {
                        Path filePath = m.getFilePath();
                        if (filePath == null) {
                            JFileChooser fc = new JFileChooser();
                            fc.setDialogTitle(flp.getString("saveAs"));
                            if (fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
                                JOptionPane.showMessageDialog(
                                        notepad,
                                        flp.getString("docNotSaved"),
                                        flp.getString("warning") + "!",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            filePath = fc.getSelectedFile().toPath();
                        }
                        dmdm.saveDocument(m, filePath);
                        iterator.remove();
                    }
                    case JOptionPane.NO_OPTION -> {
                        iterator.remove();
                        continue;
                    }
                    case JOptionPane.CANCEL_OPTION -> {
                    }
                }
            }
        }
        notepad.getClock().stop();
        notepad.dispose();

    }
}
