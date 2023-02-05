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

public abstract class StellarPluginMessages {

	@Getter
	private static YamlDocument MESSAGES_FILE;
	@Getter
	private final File PLUGIN_DATA_FOLDER = StellarPlugin.getInstance().getDataFolder();

	// All the messages
	@Getter
	private static Integer MESSAGES_VERSION;
	@Getter
	private static String PLUGIN_PREFIX;
	@Getter
	private static String PLUGIN_PREFIX_DEBUG;
	@Getter
	private static String DEBUG_ENABLED;
	@Getter
	private static String DEBUG_DISABLED;
	@Getter
	private static String DEBUG_STATUS_ENABLED;
	@Getter
	private static String DEBUG_STATUS_DISABLED;
	@Getter
	private static String DEBUG_MESSAGE_FORMAT;
	@Getter
	private static String RELOAD;
	@Getter
	private static String ERROR;

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
		PLUGIN_PREFIX = MESSAGES_FILE.getString("Plugin_prefix:");
		PLUGIN_PREFIX_DEBUG = MESSAGES_FILE.getString("Plugin_Prefix_Debug:");
		DEBUG_ENABLED = MESSAGES_FILE.getString("Debug_Enabled:");
		DEBUG_DISABLED = MESSAGES_FILE.getString("Debug_Disabled:");
		DEBUG_STATUS_ENABLED = MESSAGES_FILE.getString("Debug_Status_Enabled:");
		DEBUG_STATUS_DISABLED = MESSAGES_FILE.getString("Debug_Status_Disabled:");
		DEBUG_MESSAGE_FORMAT = MESSAGES_FILE.getString("Debug_Message_Format:");
		RELOAD = MESSAGES_FILE.getString("Reload:");
		ERROR = MESSAGES_FILE.getString("Error:");

	}


}
