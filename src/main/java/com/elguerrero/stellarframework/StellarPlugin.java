package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.utils.StellarPluginUtils;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class StellarPlugin extends JavaPlugin implements StellarPluginUtils {

	@Getter
	private static volatile StellarPlugin PLUGIN_INSTANCE = null;
	@Getter
	private static Logger PLUGIN_LOGGER = null;
	@Getter
	private static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
	@Getter
	private static File PLUGIN_FOLDER = null;

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
	@Setter(AccessLevel.PROTECTED)
	private static String PLUGIN_AUTHOR = "&3Elguerrero|MoonWalker";

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private static int HELP_COMMAND_NUMBER_OF_PAGES = 2;


	@Override
	public void onLoad() {
		try {
			// Declare the plugin main class as the instance of the plugin
			// When this is called in the son class of the plugin of this class using super.onLoad()
			PLUGIN_INSTANCE = this;
			checkIfInstanceIsNull();
			setVariablesValues();
			if (!StellarUtils.pluginFileExist(PLUGIN_FOLDER, true)){
				return;
			}
			StellarUtils.loadPluginConfigs();
			StellarUtils.sendDebugMessage("The instance of the framework is the plugin:" + PLUGIN_INSTANCE.getName() + " , who have the main class:" + PLUGIN_INSTANCE.getClass().getName());

			CommandAPI.onLoad(new CommandAPIConfig().silentLogs(StellarConfig.getDEBUG()).verboseOutput(StellarConfig.getDEBUG()));
			StellarUtils.registerCommands();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}


	@Override
	public void onEnable() {

		try {
			CommandAPI.onEnable(PLUGIN_INSTANCE);
			StellarUtils.sendMessageDebugStatus();
			this.consoleSendPluginStartMessage();
		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

	@Override
	public void onDisable() {

		try {
			CommandAPI.onDisable();
		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	private static void setVariablesValues() {

		PLUGIN_LOGGER = PLUGIN_INSTANCE.getLogger();
		PLUGIN_FOLDER = PLUGIN_INSTANCE.getDataFolder();
		ERRORS_LOG = new File(PLUGIN_FOLDER, "errors.log");
		PLUGIN_NAME = Objects.requireNonNull(PLUGIN_INSTANCE).getName();
		PLUGIN_DESCRIPTION = PLUGIN_INSTANCE.getDescription().getDescription();
		PLUGIN_VERSION = PLUGIN_INSTANCE.getDescription().getVersion();
		PLUGIN_AUTHOR = PLUGIN_INSTANCE.getDescription().getAuthors().toString();

	}

	private static void checkIfInstanceIsNull() {
		if (PLUGIN_INSTANCE == null) {
			StellarUtils.sendConsoleSevereMessage("&cThe plugin need a restart of the server for work properly.");
			StellarUtils.sendConsoleSevereMessage("&cDisabling the plugin for avoid errors.... Please restart the server.");
			PLUGIN_INSTANCE.onDisable();
		}
	}

}
