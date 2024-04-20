package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationListener listener;
	private ILocalizationProvider parent;
	
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.connected = false;
		this.parent = parent;
	}

	public void disconnect() {
		this.connected = false;
	}
	
	public void connect() {
		if(this.connected)
			System.out.println("Already connected.");
		else {
			this.connected = true;
			this.listener = new ILocalizationListener() {
				@Override
				public void localizationChanged() {
					LocalizationProviderBridge.this.parent.addLocalizationListener(listener);
				}
			};
		}
	}
	@Override
	public String getString(String key) {
		return this.parent.getString(key);
	}
}
