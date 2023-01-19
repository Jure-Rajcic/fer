
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public abstract class CaseAction extends LocalizableAction {
    private JNotepadPP notepad;
    private CaseActionType type;

    public CaseAction(JNotepadPP notepad, String keyword, CaseActionType type) {
        super(keyword, notepad.getFormLocalizationProvider());
        this.notepad = notepad;
        this.type = type;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        final JTextArea jTextArea = dmdm.getCurrentDocument().getTextComponent();
        int len = Math.abs(jTextArea.getCaret().getDot() - jTextArea.getCaret().getMark());
        int offset = 0;
        offset = Math.min(jTextArea.getCaret().getDot(), jTextArea.getCaret().getMark());
        try {
            char[] chars = jTextArea.getDocument().getText(offset, len).toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                switch (type) {
                    case INVERT_CASE -> {
                        if (Character.isLowerCase(c)) {
                            chars[i] = Character.toUpperCase(c);
                        } else if (Character.isUpperCase(c)) {
                            chars[i] = Character.toLowerCase(c);
                        }
                    }
                    case TO_UPPER_CASE ->
                        chars[i] = Character.toUpperCase(c);

                    case TO_LOWER_CASE ->
                        chars[i] = Character.toLowerCase(c);

                }
            }
            jTextArea.getDocument().remove(offset, len);
            jTextArea.getDocument().insertString(offset, new String(chars), null);
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
    }

}
