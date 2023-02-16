package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class StellarPluginFramework extends JavaPlugin {

	@Getter
	private static volatile StellarPluginFramework INSTANCE;
	@Getter
	private static Logger PLUGIN_LOGGER = null;
	@Getter
	private static final File PLUGIN_FOLDER = StellarPluginFramework.getINSTANCE().getDataFolder();
	@Getter
	private static File LANG_FOLDER = null;
	@Getter
	private static String PLUGIN_NAME = null;
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private static String PLUGIN_FORMAT = null;
	@Getter
	private static String PLUGIN_DESCRIPTION = null;
	@Getter
	private static String PLUGIN_VERSION = null;
	@Getter
	private static String PLUGIN_AUTHOR = null;

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private static Integer HELP_COMMAND_NUMBER_OF_PAGES = 2;


	@Override
	public void onLoad() {
		try {
			// Declare the plugin main class as the instance of the plugin
			// When this is called in the son class of the plugin of this class using super.onLoad()
			INSTANCE = this;
			setVariablesValues();
			StellarUtils.checkPluginFolder();
			StellarUtils.loadPluginConfigs();
			CommandAPI.onLoad(new CommandAPIConfig().silentLogs(StellarConfig.getDEBUG()).verboseOutput(StellarConfig.getDEBUG()));
			StellarUtils.registerCommands();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public void onEnable() {

		try {
			CommandAPI.onEnable(INSTANCE);
			StellarUtils.sendMessageDebugStatus();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onDisable() {

		try {
			CommandAPI.onDisable();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private static void setVariablesValues() {

		StellarPluginFramework.PLUGIN_LOGGER = INSTANCE.getLogger();
		StellarPluginFramework.PLUGIN_NAME = Objects.requireNonNull(INSTANCE).getName();
		StellarPluginFramework.PLUGIN_DESCRIPTION = INSTANCE.getDescription().getDescription();
		StellarPluginFramework.PLUGIN_VERSION = INSTANCE.getDescription().getVersion();
		StellarPluginFramework.PLUGIN_AUTHOR = INSTANCE.getDescription().getAuthors().toString();
		LANG_FOLDER = new File(PLUGIN_FOLDER, "lang");

	}

}
