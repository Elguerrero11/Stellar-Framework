package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.config.StellarLangManager;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPI;
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
	private static final File LANG_FOLDER = new File(PLUGIN_FOLDER, "lang");
	@Getter
	private static String PLUGIN_NAME = null;
	@Getter
	private static String PLUGIN_DESCRIPTION = "null";
	@Getter
	private static String PLUGIN_VERSION = null;
	@Getter
	private static String PLUGIN_AUTHOR = null;

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private static Integer HELP_COMMAND_NUMBER_OF_PAGES = 2;


	@Override
	public void onLoad() {

		// Declare the plugin main class as the instance of the plugin
		// When this is called in the son class of the plugin of this class using super.onLoad()
		INSTANCE = this;
	    setVariablesValues();
		StellarUtils.checkPluginFolder();
		StellarLangManager.loadSelectedLangMessages();


	}

	@Override
	public void onEnable() {

		CommandAPI.onEnable(INSTANCE);
		StellarUtils.sendMessageDebugStatus();

	}

	@Override
	public void onDisable() {

		CommandAPI.onDisable();

	}

	private static void setVariablesValues() {

		StellarPluginFramework.PLUGIN_LOGGER = INSTANCE.getLogger();
		StellarPluginFramework.PLUGIN_NAME = Objects.requireNonNull(INSTANCE).getName();
		StellarPluginFramework.PLUGIN_DESCRIPTION = INSTANCE.getDescription().getDescription();
		StellarPluginFramework.PLUGIN_VERSION = INSTANCE.getDescription().getVersion();
		StellarPluginFramework.PLUGIN_AUTHOR = INSTANCE.getDescription().getAuthors().toString();

	}

}
