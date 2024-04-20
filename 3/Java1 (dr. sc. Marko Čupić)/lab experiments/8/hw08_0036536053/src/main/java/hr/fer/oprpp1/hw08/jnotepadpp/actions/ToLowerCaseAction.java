
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import javax.swing.Action;

public class ToLowerCaseAction extends CaseAction {
    private static String KEYWORD = "toLowerCase";

    public ToLowerCaseAction(JNotepadPP notepad) {
        super(notepad, KEYWORD, CaseActionType.TO_LOWER_CASE);

        this.putValue(Action.SHORT_DESCRIPTION, "Switches the selected text to lowercase.");
        this.setEnabled(false);

    }

}
