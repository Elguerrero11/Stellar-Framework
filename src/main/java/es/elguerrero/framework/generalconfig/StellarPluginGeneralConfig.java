package es.elguerrero.framework.generalconfig;

import es.elguerrero.framework.StellarPlugin;
import net.xconfig.bukkit.XConfigBukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class StellarPluginGeneralConfig implements XConfigBukkit {

	public int version;
	public boolean debug;

	public StellarPluginGeneralConfig(JavaPlugin plugin) {
		super(StellarPlugin.getInstance, "config.yml", false);
	}

	@Override
	public void onLoad() {
		version = getConfig().getInt("version");
		debug = getConfig().getBoolean("debug");
	}

	public void setDebug(boolean value) {
		debug = value;
		getConfig().set("debug", value);
		save();
	}

}
