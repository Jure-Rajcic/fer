package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * class DefaultMultipleDocumentModel represents implementation of
 * MultipleDocumentModel
 * 
 * @author Jure Rajcic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	private List<MultipleDocumentListener> listeners;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;

	public DefaultMultipleDocumentModel() {
		this.listeners = new ArrayList<>();
		this.documents = new ArrayList<>();
		this.current = null;
		this.addChangeListener((ChangeEvent e) -> {
			System.out.println("Tab: " + this.getSelectedIndex());
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int index = pane.getSelectedIndex();
			if (index == -1)
				return;
			this.listeners.stream()
					.forEach(l -> l.currentDocumentChanged(this.getCurrentDocument(), this.getDocument(index)));
			this.current = this.getDocument(index);
		});
	}

	

	@Override
	public SingleDocumentModel createNewDocument() {
		System.out.println("creating new empty document");
		final SingleDocumentModel model = new DefaultSingleDocumentModel("", null);
		this.documents.add(model);
		listeners.stream().forEach(l -> l.documentAdded(model));
		model.setModified(false);
		this.current = model;
		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.current;
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		if(path == null)
			throw new IllegalArgumentException("path argument given to findForPath function can't be null");
		System.out.format("finding document for path: %s\n", path);
		for (SingleDocumentModel m : this.documents) {
			if (m.getFilePath() != null && m.getFilePath().equals(path)) {
				return m;
			}
		}
		return null;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		System.out.format("loading document from path: %s\n", path);
		if (path == null)
			throw new IllegalArgumentException("path argument given to loadDocument function can't be null");
		// checking if model for that path is already opend
		SingleDocumentModel model = this.findForPath(path);
		if (model != null) {
			for (var l : this.listeners)
				l.currentDocumentChanged(getCurrentDocument(), model);
		} else {
			try {
				String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
				model = new DefaultSingleDocumentModel(text, path);
			} catch (IOException ex) {
				System.out.format("Exception happends while reading bytes from path: %s", path);
				return null;
			}
			this.documents.add(model);
			for (var l : this.listeners)
				l.documentAdded(model);
		}
		this.setSelectedIndex(this.getIndexOfDocument(model));
		model.setModified(false);
		this.current = model;
		return model;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null)
			newPath = model.getFilePath();
		try {
			Files.write(newPath, model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.format("Exception happends while writing bytes from path: %s", newPath);
			return;
		}
		model.setFilePath(newPath);
		this.listeners.stream().forEach(l -> l.currentDocumentChanged(getCurrentDocument(), model));
		this.current = model;
		this.current.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (!this.documents.remove(model)) return;
		this.removeTabAt(super.getSelectedIndex());
		this.current = this.documents.isEmpty() ? null : this.getDocument(this.getNumberOfDocuments() - 1);
		this.listeners.stream().forEach(l -> l.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < DefaultMultipleDocumentModel.this.getNumberOfDocuments();
			}

			@Override
			public SingleDocumentModel next() {
				return DefaultMultipleDocumentModel.this.getDocument(index++);
			}

			@Override
			public void remove() {
				DefaultMultipleDocumentModel.this.closeDocument(DefaultMultipleDocumentModel.this.getDocument(--index));
			}
		};
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return documents.indexOf(doc);
	}


	@Override
	public JComponent getVisualComponent() {
		return this;
	}

}
