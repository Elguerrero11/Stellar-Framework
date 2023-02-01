package es.elguerrero.stellarframework.config;

public class ConfigImplement extends StellarPluginConfig {

	private static final ConfigImplement INSTANCE = new ConfigImplement();

	@Override
	public ConfigImplement getInstance() {
		return INSTANCE;
	}

	public void reload() {
		onConfigLoad();
	}
}
