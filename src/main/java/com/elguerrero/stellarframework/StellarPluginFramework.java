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
	private static volatile StellarPluginFramework INSTANCE = null;
	@Getter
	private static Logger PLUGIN_LOGGER = null;
	@Getter
	private static File PLUGIN_FOLDER = null;
	@Getter
	private static File LANG_FOLDER = null;

	@Getter
	private static File ERRORS_LOG = null;
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
	private static int HELP_COMMAND_NUMBER_OF_PAGES = 2;


	@Override
	public void onLoad() {
		try {
			// Declare the plugin main class as the instance of the plugin
			// When this is called in the son class of the plugin of this class using super.onLoad()
			INSTANCE = this;
			checkIfInstanceIsNull();
			setVariablesValues();
			StellarUtils.checkPluginFolder();
			StellarUtils.loadPluginConfigs();
			StellarUtils.sendDebugMessage("The instance of the framework is the plugin:" + INSTANCE.getName() + " , who have the main class:" + INSTANCE.getClass().getName());

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

		PLUGIN_LOGGER = INSTANCE.getLogger();
		PLUGIN_FOLDER = INSTANCE.getDataFolder();
		ERRORS_LOG = new File(PLUGIN_FOLDER, "errors.log");
		PLUGIN_NAME = Objects.requireNonNull(INSTANCE).getName();
		PLUGIN_DESCRIPTION = INSTANCE.getDescription().getDescription();
		PLUGIN_VERSION = INSTANCE.getDescription().getVersion();
		PLUGIN_AUTHOR = INSTANCE.getDescription().getAuthors().toString();
		LANG_FOLDER = new File(PLUGIN_FOLDER, "lang");

	}

	private static void checkIfInstanceIsNull() {
		if (INSTANCE == null) {
			StellarUtils.sendConsoleSevereMessage("&cThe plugin need a restart of the server for work properly.");
			StellarUtils.sendConsoleSevereMessage("&cDisabling the plugin for avoid errors.... Please restart the server.");
			INSTANCE.onDisable();
		}
	}

}
