package hr.fer.oprpp1.hw08.jnotepadpp.models;

/**
 * interface MultipleDocumentListener represents abstract listener functionality for multiple document
 * 
 * @author Jure Rajcic
 *
 */
public interface MultipleDocumentListener {

	/**
	 * @updates all needed elements while changing from @param previousModel to @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * @updates all needed elements while adding @param sdm document
	 */
	void documentAdded(SingleDocumentModel sdm);
	
	/**
	 * @updates all needed elements while removing @param sdm document
	 */
	void documentRemoved(SingleDocumentModel sdm);
}
