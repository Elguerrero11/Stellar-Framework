package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.commands.StellarDebugCommand;
import com.elguerrero.stellarframework.commands.StellarHelpCommand;
import com.elguerrero.stellarframework.commands.StellarReloadCommand;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
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
	private static String PLUGIN_NAME = null;

	@Getter
	private static String PLUGIN_VERSION;

	@Getter
	private static String PLUGIN_DESCRIPTION = null;
	@Getter
	@Setter
	private static String PLUGIN_AUTOR = null;

	@Getter
	@Setter
	private static String PLUGIN_LOG_PREFIX = "[" + PLUGIN_NAME + "]";

	@Getter
	private static Integer NUMBER_OF_PAGES = 2;


	@Override
	public void onEnable() {

		CommandAPI.onEnable(getInstance());
		StellarUtils.sendMessageDebugStatus();

	}

	@Override
	public void onLoad() {

		CommandAPI.onLoad(new CommandAPIConfig().silentLogs(StellarConfig.getDEBUG()).verboseOutput(StellarConfig.getDEBUG()));
		StellarHelpCommand.registerPluginInfoCommand();
		StellarReloadCommand.registerPluginReloadCommand();
		StellarDebugCommand.registerPluginDebugCommand();
		PLUGIN_LOGGER = getInstance().getLogger();
		StellarConfig.ConfigLoad();
		StellarMessages.MessagesLoad();

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

	/**
	 * Get the plugin name
	 * @return String - Plugin name or null if the plugin name is null
	 */

	public String getPluginName() {

		if (PLUGIN_NAME == null) {
			throw new NullPointerException();
		}
		return PLUGIN_NAME;
	}

	/**
	 * Get the plugin author
	 * @return String - Plugin author or null if the plugin author is null
	 */

	public String getAutor() {

		if (PLUGIN_AUTOR == null) {
			throw new NullPointerException();
		}
		return PLUGIN_AUTOR;
	}

}
