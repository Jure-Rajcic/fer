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
		// System.out.println("dodao : " + listeners.size());

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		this.listeners.remove(l);
		// System.out.println("maknuo : " + listeners.size());

	}

	
	public void fire() {
		// System.out.println(listeners.size());
		for(ILocalizationListener l : listeners)
			l.localizationChanged();
	}
}
