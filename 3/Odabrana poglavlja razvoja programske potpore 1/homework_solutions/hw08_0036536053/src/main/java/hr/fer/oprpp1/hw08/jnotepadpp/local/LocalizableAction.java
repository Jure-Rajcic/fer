package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {


	private static final long serialVersionUID = 1L;
	
	private String key;
	private ILocalizationProvider prov;
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		ILocalizationListener listener = () -> {
			String text = LocalizableAction.this.prov.getString(LocalizableAction.this.key);
			LocalizableAction.this.putValue(Action.NAME, text);
		};
		this.prov.addLocalizationListener(listener);
		String text = prov.getString(this.key);
		this.putValue(Action.NAME, text);
	}

	public ILocalizationProvider getILocalizationProvider() {
		return this.prov;
	}

}
