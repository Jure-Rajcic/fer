
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import javax.swing.Action;

public class SortAscendingAction extends SortingAction {
    private static String KEYWORD = "ascending";

    public SortAscendingAction(JNotepadPP notepad) {
        super(notepad, KEYWORD, SortingActionType.ASCENDING);

        this.putValue(Action.SHORT_DESCRIPTION, "Sorts the lines in document in ascending order.");
        this.putValue(Action.SHORT_DESCRIPTION, "Sorts the lines in document in descending order.");
    }
}
