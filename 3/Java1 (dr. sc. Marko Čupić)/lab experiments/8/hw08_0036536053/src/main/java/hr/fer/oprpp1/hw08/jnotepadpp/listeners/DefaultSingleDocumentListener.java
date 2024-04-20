package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import java.awt.Image;

public class DefaultSingleDocumentListener implements SingleDocumentListener {

    private JNotepadPP notepad;

    public DefaultSingleDocumentListener(JNotepadPP notepad) {
        this.notepad = notepad;
    }

    @Override
    public void documentModifyStatusUpdated(SingleDocumentModel sdm) {
        InputStream is;
        if (sdm.isModified()) is = notepad.getClass().getResourceAsStream("icons/red_diskette.png");
        else is = notepad.getClass().getResourceAsStream("icons/green_diskette.png");
        if (is == null) System.out.println("Image not loaded.");

        try {
            byte[] bytes = is.readAllBytes();
            is.close();

            ImageIcon image = new ImageIcon(bytes);
            Image img = image.getImage(); // transform it
            Image newimg = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH); // scale it the smooth way
            final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
            dmdm.setIconAt(dmdm.getSelectedIndex(), new ImageIcon(newimg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void documentFilePathUpdated(SingleDocumentModel sdm) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        dmdm.setTitleAt(dmdm.getSelectedIndex(), sdm.getFilePath().getFileName().toString());
        dmdm.setToolTipTextAt(dmdm.getSelectedIndex(), sdm.getFilePath().toString());
    }

}
