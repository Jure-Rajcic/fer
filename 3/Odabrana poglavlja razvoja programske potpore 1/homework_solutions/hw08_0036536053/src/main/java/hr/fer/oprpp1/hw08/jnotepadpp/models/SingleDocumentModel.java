package hr.fer.oprpp1.hw08.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface SingleDocumentModel represents abstract functionalyties when working with single document
 * 
 * @author Jure Rajcic
 *
 */
public interface SingleDocumentModel {

	/**
	 *@returns JTextArea which holds document text
	 */
	JTextArea getTextComponent();
	
	/**
	 * @returns path to file in user file sysytem
	 */
	Path getFilePath();
	
	/**
	 * @sets document file path to new @param path
	 */
	void setFilePath(Path path);
	
	/**
	 * @returns document status (has it changed or not)
	 */
	boolean isModified();
	
	/**
	 * @sets document status (has it changed or not)
	 */
	void setModified(boolean modified);
	
	/**
	 * @adds document listener ( @param SingleDocumentListener ) to list
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * @removes document listener ( @param SingleDocumentListener ) from list
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
