
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

public abstract class SortingAction extends LocalizableAction {
    private JNotepadPP notepad;
    private SortingActionType type;

    public SortingAction(JNotepadPP notepad, String keyword, SortingActionType type) {
        super(keyword, notepad.getFormLocalizationProvider());
        this.notepad = notepad;
        this.type = type;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();
        final FormLocalizationProvider flp = notepad.getFormLocalizationProvider();
        final JTextArea jTextArea = dmdm.getCurrentDocument().getTextComponent();

        int len = Math.abs(jTextArea.getCaret().getDot() - jTextArea.getCaret().getMark());
        System.out.println(len);
        int offset = Math.min(jTextArea.getCaret().getDot(), jTextArea.getCaret().getMark());
        System.out.println(offset);
        try {
            String text = jTextArea.getDocument().getText(offset, len);
            List<String> lines = Arrays.asList(text.split("\n"));
            switch(this.type) {
                case ASCENDING -> Collections.sort(lines, (s1,s2) -> Collator.getInstance(new Locale(flp.getString("language"))).compare(s1, s2));
                case DESCENDING -> Collections.sort(lines, (s1,s2) -> Collator.getInstance(new Locale(flp.getString("language"))).compare(s2, s1));
            }
            jTextArea.getDocument().remove(offset, len);
            text = String.join("\n", lines);
			jTextArea.getDocument().insertString(offset, text, null);
        } catch (BadLocationException exception) {
			exception.printStackTrace();
		}
    }

}
