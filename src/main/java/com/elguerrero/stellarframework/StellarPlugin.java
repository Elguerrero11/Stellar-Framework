package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.commands.DebugCommand;
import com.elguerrero.stellarframework.commands.HelpCommand;
import com.elguerrero.stellarframework.commands.ReloadCommand;
import com.elguerrero.stellarframework.config.StellarPluginConfig;
import com.elguerrero.stellarframework.utils.GeneralUtils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public abstract class StellarPlugin extends JavaPlugin {

	private static volatile StellarPlugin INSTANCE;
	@Getter
	private static Logger PLUGIN_LOGGER;
	@Getter
	@Setter
	private static String PLUGIN_NAME = getInstance().getPluginName();

	@Getter
	// I have to specify the setter method for check null in minor plugin version
	@Setter
	private static Double PLUGIN_VERSION = null;
	@Getter
	@Setter
	private static Double MAJOR_PLUGIN_VERSION = null;
	@Getter
	@Setter
	private static Integer MINOR_PLUGIN_VERSION = null;
	
	@Getter
	@Setter
	private static String PLUGIN_AUTOR = null;

	@Getter
	@Setter
	private static String PLUGIN_PREFIX = "";
	@Getter
	@Setter
	private static String LOG_PREFIX = "";

	@Getter
	private static Integer NUMBER_OF_PAGES = 1;


	@Override
	public void onEnable() {

		CommandAPI.onEnable(getInstance());
		GeneralUtils.sendMessageDebugStatus();

	}

	@Override
	public void onLoad() {

		CommandAPI.onLoad(new CommandAPIConfig().silentLogs(StellarPluginConfig.getDEBUG()).verboseOutput(StellarPluginConfig.getDEBUG()));
		HelpCommand.registerPluginInfoCommand();
		ReloadCommand.registerPluginReloadCommand();
		DebugCommand.registerPluginDebugCommand();
		PLUGIN_LOGGER = getInstance().getLogger();


	}

	@Override
	public void onDisable() {

		CommandAPI.onDisable();

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

		if (PLUGIN_AUTOR == null) {
			throw new NullPointerException();
		}
		return PLUGIN_AUTOR;
	}

}
