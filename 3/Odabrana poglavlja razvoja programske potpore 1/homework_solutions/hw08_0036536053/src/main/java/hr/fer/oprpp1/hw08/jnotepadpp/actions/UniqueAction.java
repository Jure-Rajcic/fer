
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.Action;

public class UniqueAction extends LocalizableAction {
    private static String KEYWORD = "unique";
    private JNotepadPP notepad;

    public UniqueAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
        this.notepad = notepad;

        this.putValue(Action.SHORT_DESCRIPTION, "Deletes the repeating lines in the document.");
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        final JTextArea jTextArea = dmdm.getCurrentDocument().getTextComponent();

        int len = Math.abs(jTextArea.getCaret().getDot() - jTextArea.getCaret().getMark());
        System.out.println(len);
        int offset = Math.min(jTextArea.getCaret().getDot(), jTextArea.getCaret().getMark());
        System.out.println(offset);
        try {
            String text = jTextArea.getDocument().getText(offset, len);
            List<String> lines = Arrays.asList(text.split("\n"));
            HashMap<String, Integer> frek = new HashMap<>();
            for (String line : lines)
                frek.put(line, frek.getOrDefault(line, 1) + 1);
            jTextArea.getDocument().remove(offset, len);
            text = String.join("\n", frek.keySet());
            jTextArea.getDocument().insertString(offset, text, null);
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
    }
}
