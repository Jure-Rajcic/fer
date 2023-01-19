package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import javax.swing.Action;

public class ToUpperCaseAction extends CaseAction {
    private static String KEYWORD = "toUpperCase";

    public ToUpperCaseAction(JNotepadPP notepad) {
        super(notepad, KEYWORD, CaseActionType.TO_UPPER_CASE);

        this.putValue(Action.SHORT_DESCRIPTION, "Switches the selected text to uppercase.");
        this.setEnabled(false);
    }

}
