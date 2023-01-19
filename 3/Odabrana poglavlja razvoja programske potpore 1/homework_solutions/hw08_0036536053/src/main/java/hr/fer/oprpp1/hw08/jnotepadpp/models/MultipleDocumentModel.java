package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JComponent;

/**
 * Interface MultipleDocumentModel represents abstract functionalyties when
 * working with multiple document
 * 
 * @author Jure Rajcic
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * @creates new document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * @returns currentDocument
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * @creates new document (loads) from @param path
	 * @returns loaded document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * @saves document @param sdm on @param path
	 */
	void saveDocument(SingleDocumentModel sdm, Path newPath);

	/**
	 * @closes document @param sdm
	 */
	void closeDocument(SingleDocumentModel sdm);

	/**
	 * @adds (MultipleDocumentListener) @param l to documents
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * @removes (MultipleDocumentListener) @param l from documents
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * @returns number of documents thaht are currently opend
	 */
	int getNumberOfDocuments();

	/**
	 * @returns document on given @param index
	 */
	SingleDocumentModel getDocument(int index);

	/**
	 * @returns JComponent which ...
	 */
	JComponent getVisualComponent();

	/**
	 * @returns SingleDocumentModel from @param path from documents
	 * @return null if model doesnt exist on thaht path
	 */
	SingleDocumentModel findForPath(Path path);

	/**
	 * @returns index of @param doc in opend documents
	 * @return -1 if @param doc is not present
	 */
	int getIndexOfDocument(SingleDocumentModel doc);
}
