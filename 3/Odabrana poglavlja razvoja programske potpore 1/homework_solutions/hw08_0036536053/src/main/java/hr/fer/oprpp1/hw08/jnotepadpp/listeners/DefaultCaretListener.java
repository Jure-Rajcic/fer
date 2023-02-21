package hr.fer.oprpp1.hw08.jnotepadpp.listeners;

import javax.swing.JLabel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;

public class DefaultCaretListener implements CaretListener{

    private JNotepadPP notepad;
    public DefaultCaretListener(JNotepadPP notepad) {
        this.notepad = notepad;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        JTextComponent tc = (JTextComponent) e.getSource();
			int pos = tc.getCaretPosition();
			Document doc = tc.getDocument();
			Element root = doc.getDefaultRootElement();
			int row = root.getElementIndex(pos);
			int col = pos - root.getElement(row).getStartOffset();
			int selection = Math.abs(e.getDot() - e.getMark());

            boolean selected = selection != 0;
            notepad.getInvertCaseAction().setEnabled(selected);
            notepad.getToUpperCaseAction().setEnabled(selected);
            notepad.getToLowerCaseAction().setEnabled(selected);
			
			
			JLabel len = (JLabel) notepad.getStatusBar().getComponent(0);
			len.setText("Length:" + doc.getLength());
			
			JLabel lcs = (JLabel) notepad.getStatusBar().getComponent(1);
			lcs.setText("Ln:" + (row+1) + " Col:" + (col+1) + " Sel:" + selection);
		
        
    }
    
}
