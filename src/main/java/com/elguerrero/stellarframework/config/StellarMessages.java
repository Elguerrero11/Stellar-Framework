package com.elguerrero.stellarframework.config;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.MergeRule;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public abstract class StellarMessages {

	@Getter
	protected YamlDocument messagesFile = null;
	protected String messagesFilePath;
	protected InputStream inputStream;
	protected String messagesVersionKeyPath;

	protected String selectedLang = "";

	protected final File langFolder = new File(StellarPlugin.getPluginInstance().getPluginFolder(), "Lang");

	protected final String langFolderPath = "Lang/";


	@Getter
	protected String pluginPrefix = "";
	@Getter
	protected String pluginPrefixDebug = "";
	@Getter
	protected String pluginReloaded = "";
	@Getter
	protected String pluginError = "";
	@Getter
	protected String noPermission = "";
	@Getter
	protected String debugEnabled = "";
	@Getter
	protected String debugDisabled = "";
	@Getter
	protected String debugStatusEnabled = "";
	@Getter
	protected String debugStatusDisabled = "";
	@Getter
	protected String debugMessageFormat = "";
	@Getter
	protected String addonAlreadyEnabled = "";
	@Getter
	protected String addonAlreadyDisabled = "";
	@Getter
	protected String addonDisabled = "";
	@Getter
	protected String addonEnabled = "";
	@Getter
	protected String addonNotFound = "";
	@Getter
	protected String addonNotRegistered = "";
	@Getter
	protected String addonCantReload = "";
	@Getter
	protected String addonReloaded = "";

	private StellarMessages() {

		this.messagesFilePath = langFolderPath + selectedLang;
		this.inputStream = StellarPlugin.getPluginInstance().getResource(langFolderPath + selectedLang);
		this.messagesVersionKeyPath = "Messages_Version";

	}

	private void loadSelectedLang(){

		final String Lang = StellarConfig.getLang();

		try {

			if (Lang.isBlank()){
				selectedLang = "en_US";
				StellarUtils.sendConsoleWarnMessage("The language selected in the config is not valid, the default language 'en_US' will be used.");
			}

			if (checkLangFileExist(Lang)){
				selectedLang = Lang;
				StellarUtils.sendConsoleInfoMessage("&aLanguage " + Lang + " selected V");
			}

		} catch (Exception ex){
			StellarUtils.logErrorException(ex,"default");
		}

	}

	private boolean checkLangFileExist(String fileName) {

		try {

			final File yamlFile = new File(langFolder, fileName + ".yml");

			if (StellarUtils.pluginFileExist(new File("Lang"),true)) {

				return yamlFile.exists() && yamlFile.isFile();

			}
			return false;

		} catch (Exception ex){
			StellarUtils.logErrorException(ex,"default");
			return false;
		}
	}

	public void loadMessagesFile() {

		try {

			messagesFile = YamlDocument.create(new File(StellarPlugin.getPluginInstance().getPluginFolder(), messagesFilePath), inputStream,
					GeneralSettings.DEFAULT,
					LoaderSettings.builder().setAutoUpdate(StellarConfig.getAutoUpdateConfigs()).setAllowDuplicateKeys(false).build(),
					DumperSettings.DEFAULT,
					UpdaterSettings.builder().setVersioning(new BasicVersioning(messagesVersionKeyPath)).setEnableDowngrading(false)
					.setMergeRule(MergeRule.MAPPINGS,true).setMergeRule(MergeRule.MAPPING_AT_SECTION,true).setMergeRule(MergeRule.SECTION_AT_MAPPING,true)
					.setKeepAll(true).build());


			callLoadConfigVariables();

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	private void callLoadConfigVariables() {

		pluginPrefix = messagesFile.getString("Plugin_Prefix");
		pluginPrefixDebug = messagesFile.getString("Plugin_Prefix_Debug");
		pluginReloaded = messagesFile.getString("Plugin_Reloaded");
		pluginError = messagesFile.getString("Plugin_Error");
		noPermission = messagesFile.getString("No_Permission");
		debugEnabled = messagesFile.getString("Debug_Enabled");
		debugDisabled = messagesFile.getString("Debug_Disabled");
		debugStatusEnabled = messagesFile.getString("Debug_Status_Enabled");
		debugStatusDisabled = messagesFile.getString("Debug_Status_Disabled");
		debugMessageFormat = messagesFile.getString("Debug_Message_Format");
		addonAlreadyEnabled = messagesFile.getString("Addon_Already_Enabled");
		addonAlreadyDisabled = messagesFile.getString("Addon_Already_Disabled");
		addonDisabled = messagesFile.getString("Addon_Disabled");
		addonEnabled = messagesFile.getString("Addon_Enabled");
		addonNotFound = messagesFile.getString("Addon_Not_Found");
		addonNotRegistered = messagesFile.getString("Addon_Not_Registered");
		addonCantReload = messagesFile.getString("Addon_Cant_Reload");
	    addonReloaded = messagesFile.getString("Addon_Reloaded");

		loadMessagesVariables();
	}

	abstract void loadMessagesVariables();

}