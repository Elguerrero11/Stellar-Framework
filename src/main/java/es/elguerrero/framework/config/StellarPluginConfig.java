package es.elguerrero.framework.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import es.elguerrero.framework.StellarPlugin;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class StellarPluginConfig {

	private YamlDocument CONFIG_FILE;
	@Getter
	private final File PLUGIN_DATA_FOLDER = StellarPlugin.getInstance().getDataFolder();
	private static volatile StellarPluginConfig INSTANCE;

	private Integer CONFIG_VERSION;
	private Boolean DEBUG;
	private Boolean BSTATS_METRICS;

	/**
	 * Happen when the plugin loads
	 * <p>
	 * Create the file if dont exist and update it if is not updated
	 * <p>
	 * Too, load the variables
	 */
	public void onLoad() {

		try {
			CONFIG_FILE = YamlDocument.create(new File(StellarPlugin.getInstance().getDataFolder(), "config.yml"), Objects.requireNonNull(StellarPlugin.getInstance().getResource("config.yml")),
					GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version")).build());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		INSTANCE.CONFIG_VERSION = CONFIG_FILE.getInt("General_Options.Version");
		INSTANCE.DEBUG = CONFIG_FILE.getBoolean("General_Options.Debug");
		INSTANCE.BSTATS_METRICS = CONFIG_FILE.getBoolean("General_Options.BStats_Metrics");

	}

	public abstract StellarPluginConfig getInstance();


}
