package com.elguerrero.stellarframework.config;

import com.elguerrero.stellarframework.StellarPlugin;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

// Abstract removed
public class StellarConfig {

	@Getter
	private static YamlDocument CONFIG_FILE;
	@Getter
	private final File PLUGIN_DATA_FOLDER = StellarPlugin.getInstance().getDataFolder();

	// The config options
	@Getter
	private static Integer CONFIG_VERSION;
	@Getter
	private static Boolean DEBUG;
	@Getter
	private static Boolean BSTATS_METRICS;

	/**
	 * Happen when the plugin load and reload
	 * <p>
	 * Create the file if dont exist and update it if is not updated
	 */
	public static void loadConfigFile() {

		try {
			CONFIG_FILE = YamlDocument.create(new File(StellarPlugin.getInstance().getDataFolder(), "config.yml"), Objects.requireNonNull(StellarPlugin.getInstance().getResource("config.yml")),
					GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("Config_version")).build());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Happen when the plugin load and reload
	 * Set the variables to the config values
	 */

	public static void loadConfigVariables() {

		CONFIG_VERSION = CONFIG_FILE.getInt("General_Options.Config_version");
		DEBUG = CONFIG_FILE.getBoolean("General_Options.Debug");

		BSTATS_METRICS = CONFIG_FILE.getBoolean("General_Options.BStats_Metrics");

	}

}
