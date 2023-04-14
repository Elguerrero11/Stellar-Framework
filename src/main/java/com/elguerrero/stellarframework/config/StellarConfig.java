package com.elguerrero.stellarframework.config;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public abstract class StellarConfig implements StellarConfigManager {

	@Setter(AccessLevel.PROTECTED)
	private static StellarConfig CHILD_INSTANCE = null;
	@Getter(AccessLevel.PUBLIC)
	private static YamlDocument CONFIG_FILE;
	private static final InputStream resourceStream = StellarPlugin.getPluginInstance().getResource("StellarPlugin/config.yml");

	@Getter
	private static final String CONFIG_PATH = "StellarPlugin/config.yml";
	private static final String CONFIG_VERSION_PATH = "Config_Version";

	// The config options
	@Getter
	private static String LANG;
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
			CONFIG_FILE = YamlDocument.create(new File(StellarPlugin.getPluginFolder(), CONFIG_PATH), Objects.requireNonNull(resourceStream),
					GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning(CONFIG_VERSION_PATH)).build());

			loadConfigVariables();

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex, "default");
		}


	}

	/**
	 * Happen when the plugin load and reload Set the variables to the config values
	 */

	public static void loadConfigVariables() {

		LANG = CONFIG_FILE.getString("Lang");
		CONFIG_VERSION = CONFIG_FILE.getInt(CONFIG_VERSION_PATH);
		DEBUG = CONFIG_FILE.getBoolean("Debug_Mode");

		BSTATS_METRICS = CONFIG_FILE.getBoolean("BStats_Metrics");

		CHILD_INSTANCE.loadStellarPluginConfigVariables();

	}


}
