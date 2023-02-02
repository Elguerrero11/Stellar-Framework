package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.commands.InfoCommand;
import com.elguerrero.stellarframework.commands.ReloadCommand;
import com.elguerrero.stellarframework.config.StellarPluginConfig;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public abstract class StellarPlugin extends JavaPlugin {

	private static volatile StellarPlugin INSTANCE;
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
	private static String tellPrefix = "";
	@Getter
	@Setter
	private static String logPrefix = "";

	@Getter
	private static Integer numberOfPages = 1;


	@Override
	public void onEnable() {

		CommandAPI.onEnable(getInstance());

	}

	@Override
	public void onLoad() {

		CommandAPI.onLoad(new CommandAPIConfig().silentLogs(StellarPluginConfig.getDEBUG()).verboseOutput(StellarPluginConfig.getDEBUG()));
		InfoCommand.registerPluginInfoCommand();
		ReloadCommand.registerPluginReloadCommand();


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
