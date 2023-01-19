package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * class DefaultSingleDocumentModel represents implementation of
 * SingleDocumentModel
 * 
 * @author Jure Rajcic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private boolean modified;
	private JTextArea textComponent;
	private Path path;
	private List<SingleDocumentListener> listeners;

	public DefaultSingleDocumentModel(String textContent, Path path) {
		this.textComponent = new JTextArea(textContent);
		this.path = path;
		this.listeners = new ArrayList<>();
		this.modified = false;
		this.textComponent.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						System.out.println("DocumentListener: insertUpdate!");
						DefaultSingleDocumentModel.this.setModified(true);
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						System.out.println("DocumentListener: removeUpdate!");
						DefaultSingleDocumentModel.this.setModified(true);
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						System.out.println("DocumentListener: changedUpdate!");
						DefaultSingleDocumentModel.this.setModified(true);
					}
				});

	}

	@Override
	public JTextArea getTextComponent() {
		return this.textComponent;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
		listeners.stream().forEach(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		listeners.stream().forEach(l -> l.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}

}
