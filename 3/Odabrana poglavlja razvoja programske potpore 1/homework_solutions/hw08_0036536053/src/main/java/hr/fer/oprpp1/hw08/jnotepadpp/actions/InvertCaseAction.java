package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import javax.swing.Action;

public class InvertCaseAction extends CaseAction {
    private static String KEYWORD = "invertCase";

    public InvertCaseAction(JNotepadPP notepad) {
        super(notepad, KEYWORD, CaseActionType.INVERT_CASE);

        this.putValue(Action.SHORT_DESCRIPTION, "Inverts upercase & lowercase for selected text.");
        this.setEnabled(false);
    }

}
