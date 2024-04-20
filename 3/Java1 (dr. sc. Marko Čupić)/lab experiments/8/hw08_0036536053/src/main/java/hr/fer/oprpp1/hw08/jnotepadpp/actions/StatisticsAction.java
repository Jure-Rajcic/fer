package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Document;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class StatisticsAction extends LocalizableAction {
	private static String KEYWORD = "statistics";
	private JNotepadPP notepad;

	private int noOfChars;
	private int noOfNonBlank = 0;
	private int noOfLines = 1;
	private String text;
	private Element element;

	public StatisticsAction(JNotepadPP notepad) {
        super(KEYWORD, notepad.getFormLocalizationProvider());
		this.notepad = notepad;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		this.putValue(Action.SHORT_DESCRIPTION, "Gives the statistical information for the document.");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		final DefaultMultipleDocumentModel dmdm = notepad.getDefaultMultipleDocumentModel();

		if (dmdm.getCurrentDocument() == null)
			return;
		Document doc = dmdm.getCurrentDocument().getTextComponent().getDocument();
		element = doc.getDefaultRootElement();
		noOfChars = doc.getLength();
		noOfLines = element.getElementCount();
		try {
			text = doc.getText(0, noOfChars);
			for (int i = 0; i < noOfChars; i++) {
				if (Character.isLetterOrDigit(text.charAt(i)))
					noOfNonBlank++;
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		boolean isNull = dmdm.getCurrentDocument().getFilePath() == null;
		FormLocalizationProvider flp = (FormLocalizationProvider) super.getILocalizationProvider();

		JOptionPane.showMessageDialog(notepad, flp.getString("document") + " "
				+ (isNull ? "(unnamed)" : dmdm.getCurrentDocument().getFilePath().getFileName().toString())
				+ " " + flp.getString("has") + " " + noOfChars + " " + flp.getString("chars") + ", "
				+ noOfNonBlank + " " + flp.getString("nonBlank") + " "
				+ noOfLines + " " + flp.getString("lines") + ".",
				flp.getString("statistics"), 1);
		noOfNonBlank = 0;

	}

}
