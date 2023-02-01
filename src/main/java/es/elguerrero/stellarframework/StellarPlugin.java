package es.elguerrero.stellarframework;

import dev.jorel.commandapi.CommandAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public abstract class StellarPlugin extends JavaPlugin {

	private static volatile StellarPlugin INSTANCE;
	@Getter
	@Setter
	private static String PLUGIN_NAME = null;
	@Getter
	@Setter
	private static Double MAJOR_PLUGIN_VERSION = null;
	@Getter
	@Setter
	private static Integer MINOR_PLUGIN_VERSION = null;
	@Getter
	@Setter
	private static String AUTOR = null;


	@Override
	public void onEnable() {


		/*getGeneralConfig() = new StellarPluginConfig() {
		};
		config.load();*/

	}

	@Override
	public void onLoad() {

		CommandAPI.onLoad(CommandAPIConfig config);

	}

	@Override
	public void onDisable() {

	}

	public static StellarPlugin getInstance() {
		if (INSTANCE == null) {
			INSTANCE = JavaPlugin.getPlugin(StellarPlugin.class);

			Objects.requireNonNull(INSTANCE, "The plugin need a full server restart for work fine, not just a reload.");
		}

		return INSTANCE;
	}


	// Getters for the variables
	// With null check

	public String getPluginName() {

		if (PLUGIN_NAME == null) {
			throw new NullPointerException();
		}
		return PLUGIN_NAME;
	}

	public Double getMajorPluginVersion() {

		if (MAJOR_PLUGIN_VERSION == null) {
			throw new NullPointerException();
		}
		return MAJOR_PLUGIN_VERSION;
	}

	public Integer getMinorPluginVersion() {

		if (MINOR_PLUGIN_VERSION == null) {
			throw new NullPointerException();
		}
		return MINOR_PLUGIN_VERSION;
	}

	public String getAutor() {

		if (AUTOR == null) {
			throw new NullPointerException();
		}
		return AUTOR;
	}


}
