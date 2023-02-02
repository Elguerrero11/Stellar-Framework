package es.elguerrero.stellarframework.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import es.elguerrero.stellarframework.StellarPlugin;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class StellarPluginMessages {

	@Getter
	private static YamlDocument MESSAGES_FILE;
	@Getter
	private final File PLUGIN_DATA_FOLDER = StellarPlugin.getInstance().getDataFolder();

	// All the messages
	@Getter
	private static Integer MESSAGES_VERSION;

	/**
	 * Happen when the plugin loads
	 * <p>
	 * Create the file if dont exist and update it if is not updated
	 * <p>
	 * Too, load the variables
	 */
	public static void onConfigLoad() {

		try {
			MESSAGES_FILE = YamlDocument.create(new File(StellarPlugin.getInstance().getDataFolder(), "messages.yml"), Objects.requireNonNull(StellarPlugin.getInstance().getResource("messages.yml")),
					GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("Messages_version")).build());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		MESSAGES_VERSION = MESSAGES_FILE.getInt("Messages_version:");

	}


}
