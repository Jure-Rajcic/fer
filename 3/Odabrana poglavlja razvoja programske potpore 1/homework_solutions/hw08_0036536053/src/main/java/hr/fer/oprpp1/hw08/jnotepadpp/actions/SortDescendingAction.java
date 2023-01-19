
package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;

public class SortDescendingAction extends SortingAction {
    private static String KEYWORD = "descending";

    public SortDescendingAction(JNotepadPP notepad) {
        super(notepad, KEYWORD, SortingActionType.DESCENDING);
    }
}

