package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	private List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		this.listeners.remove(l);
	}
	
	public void fire() {
		for(ILocalizationListener l : listeners)
			l.localizationChanged();
	}
}
