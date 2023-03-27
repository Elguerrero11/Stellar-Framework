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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class StellarLangManager implements StellarConfigManager, StellarMessagesManager{

	@Setter(AccessLevel.PROTECTED)
	private static StellarMessages CHILD_INSTANCE = null;
	@Getter(AccessLevel.PROTECTED)
	private static final List<String> LANGUAGES_LIST = new ArrayList<>(Arrays.asList("es_ES", "en_US"));
	private static String SELECTED_LANGUAGE = "en_US";
	@Getter
	private static YamlDocument SELECTED_LANGUAGE_FILE = null;

	@Getter
	private static final File LANG_FOLDER = new File(StellarPlugin.getPLUGIN_FOLDER(), "StellarPlugin/Lang");;

	@Getter
	private static final String LANG_PATH = "StellarPlugin/Lang/";
	private static final String LANG_VERSION_PATH = "Messages_Version";


	/**
	 * Happen when the plugin load and reload
	 * <p>
	 * The plugin folder was already checked
	 * <p>
	 * 1 - Check the lang folder and create it if dont exist
	 * 2 - If the config lang is not the same as the selected
	 * lang, update the selected lang Too check if the config lang is valid for avoid that be empty or not in the list
	 * If is send a warn message and use the default lang
	 * 3 - Generate and save as selected language file object the
	 * selected language file
	 * 4 - Generate all the others messages files
	 * 5 - Set the messages of StellarMessages class to the messages of the selected language file
	 */
	public static void loadSelectedLangMessages() {

		try {

			if (!StellarConfig.getLANG().equalsIgnoreCase(SELECTED_LANGUAGE)) {
				SELECTED_LANGUAGE = StellarConfig.getLANG();
			} else if (StellarConfig.getLANG().isEmpty() || !LANGUAGES_LIST.contains(StellarConfig.getLANG())) {
				StellarUtils.sendConsoleWarnMessage("&cThe language selected in the config is not valid, the default language will be used.");
			}

			SELECTED_LANGUAGE_FILE = YamlDocument.create(new File(StellarPlugin.getPLUGIN_FOLDER(), LANG_PATH + SELECTED_LANGUAGE + ".yml"), Objects.requireNonNull(StellarPlugin.getPLUGIN_INSTANCE().getResource(LANG_PATH + SELECTED_LANGUAGE + ".yml")),
					GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning(LANG_VERSION_PATH)).build());

			for (String lang : LANGUAGES_LIST) {

				if (!SELECTED_LANGUAGE.equalsIgnoreCase(lang)) {

					YamlDocument.create(new File(StellarPlugin.getPLUGIN_FOLDER(), LANG_PATH + lang + ".yml"), Objects.requireNonNull(StellarPlugin.getPLUGIN_INSTANCE().getResource(LANG_PATH + lang + ".yml")),
							GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning(LANG_VERSION_PATH)).build());

				}
			}

			StellarLangManager.setStellarMessages();

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex,"default");
		}
	}

	private static void setStellarMessages() {

		// All the messages

		StellarMessages.setPLUGIN_PREFIX(SELECTED_LANGUAGE_FILE.getString("Plugin_Prefix"));
		StellarMessages.setPLUGIN_PREFIX_DEBUG(SELECTED_LANGUAGE_FILE.getString("Plugin_Prefix_Debug"));
		StellarMessages.setDEBUG_ENABLED(SELECTED_LANGUAGE_FILE.getString("Debug_Enabled"));
		StellarMessages.setDEBUG_DISABLED(SELECTED_LANGUAGE_FILE.getString("Debug_Disabled"));
		StellarMessages.setDEBUG_STATUS_ENABLED(SELECTED_LANGUAGE_FILE.getString("Debug_Status_Enabled"));
		StellarMessages.setDEBUG_STATUS_DISABLED(SELECTED_LANGUAGE_FILE.getString("Debug_Status_Disabled"));
		StellarMessages.setDEBUG_MESSAGE_FORMAT(SELECTED_LANGUAGE_FILE.getString("Debug_Message_Format"));
		StellarMessages.setRELOAD(SELECTED_LANGUAGE_FILE.getString("Reload"));
		StellarMessages.setPLUGIN_ERROR(SELECTED_LANGUAGE_FILE.getString("Plugin_Error"));
		StellarMessages.setNO_PERMISSION(SELECTED_LANGUAGE_FILE.getString("No_Permission"));
		StellarMessages.setAddon_Already_Enabled(SELECTED_LANGUAGE_FILE.getString("Addon_Already_Enabled"));
		StellarMessages.setAddon_Already_Disabled(SELECTED_LANGUAGE_FILE.getString("Addon_Already_Disabled"));
		StellarMessages.setAddon_Disabled(SELECTED_LANGUAGE_FILE.getString("Addon_Disabled"));
		StellarMessages.setAddon_Enabled(SELECTED_LANGUAGE_FILE.getString("Addon_Enabled"));
		StellarMessages.setAddon_Not_Found(SELECTED_LANGUAGE_FILE.getString("Addon_Not_Found"));
		StellarMessages.setAddon_Not_Registered(SELECTED_LANGUAGE_FILE.getString("Addon_Not_Registered"));
		StellarMessages.setAddon_Cant_Reload(SELECTED_LANGUAGE_FILE.getString("Addon_Cant_Reload"));
		StellarMessages.setAddon_Reloaded(SELECTED_LANGUAGE_FILE.getString("Addon_ReLoaded"));


		StellarMessages.setMESSAGES_VERSION(SELECTED_LANGUAGE_FILE.getInt(LANG_VERSION_PATH));

		CHILD_INSTANCE.loadStellarPluginMessagesVariables();

	}


}
