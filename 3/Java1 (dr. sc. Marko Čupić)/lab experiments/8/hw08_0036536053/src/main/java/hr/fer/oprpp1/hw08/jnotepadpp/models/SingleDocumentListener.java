package hr.fer.oprpp1.hw08.jnotepadpp.models;

/**
 * interface SingleDocumentListener represents abstract listener functionality for single document
 * 
 * @author Jure Rajcic
 *
 */
public interface SingleDocumentListener {

	/**
	 * @updates all needed elements on @param sdm when changed on document observed
	 */
	void documentModifyStatusUpdated(SingleDocumentModel sdm);
	
	/**
	 * @updates all needed elements on @param sdm when changed in file sysytem observed
	 */
	void documentFilePathUpdated(SingleDocumentModel sdm);
}
