package es.elguerrero.framework;

import es.elguerrero.framework.generalconfig.StellarPluginGeneralConfig;
import lombok.Getter;
import lombok.Setter;
import net.xconfig.bukkit.XConfigBukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class StellarPlugin extends JavaPlugin {

	@Getter
	@Setter
	private String pluginName = null;
	@Getter
	@Setter
	private Double majorPluginVersion = null;
	@Getter
	@Setter
	private Integer minorPluginVersion = null;
	@Getter
	@Setter
	private String autor = null;
	@Getter
	@Setter
	private XConfigBukkit generalConfig;


	@Override
	public void onEnable() {
		

		getGeneralConfig() = new StellarPluginGeneralConfig() {
		};
		config.load();

	}

	@Override
	public void onLoad() {

	}

	@Override
	public void onDisable() {

	}

	/*public Instance pluginGetInstance(){

	}*/


	// Getters for the variables
	// With null check

	public String getPluginName() {

		if (pluginName == null) {
			throw new NullPointerException();
		}
		return pluginName;
	}

	public Double getMajorPluginVersion() {

		if (majorPluginVersion == null) {
			throw new NullPointerException();
		}
		return majorPluginVersion;
	}

	public Integer getMinorPluginVersion() {

		if (minorPluginVersion == null) {
			throw new NullPointerException();
		}
		return minorPluginVersion;
	}

	public String getAutor() {

		if (autor == null) {
			throw new NullPointerException();
		}
		return autor;
	}


}
