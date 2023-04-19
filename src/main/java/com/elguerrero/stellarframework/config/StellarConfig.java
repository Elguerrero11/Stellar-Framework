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


public abstract class StellarConfig {

	@Getter
	private static String lang = "";
	@Getter
	private static Boolean debug = false;
	@Getter
	private static Boolean bStats = null;
	@Getter
	private static Boolean autoUpdateConfigs = false;
	@Getter
	protected YamlDocument configFile = null;
	protected String configFilePath;
	protected InputStream inputStream;
	protected String configVersionKeyPath;

	protected StellarConfig() {

		configFilePath = "config.yml";
		inputStream = StellarPlugin.getPluginInstance().getResource("config.yml");
		configVersionKeyPath = "Config_Version";

	}

	protected StellarConfig(String configFilePath, String inputStream, String configVersionKeyPath) {

		this.configFilePath = configFilePath;
		this.inputStream = StellarPlugin.getPluginInstance().getResource(inputStream);
		this.configVersionKeyPath = configVersionKeyPath;

	}

	public void loadConfigFile() {

		try {

			createConfigFile();
			setGeneralVariables();
			createConfigFile();
			callLoadConfigVariables();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	protected void createConfigFile() {

		try {

			configFile = YamlDocument.create(new File(StellarPlugin.getPluginInstance().getPluginFolder(), configFilePath), inputStream,
					GeneralSettings.DEFAULT,
					LoaderSettings.builder().setAutoUpdate(autoUpdateConfigs).setAllowDuplicateKeys(false).build(),
					DumperSettings.DEFAULT,
					UpdaterSettings.builder().setVersioning(new BasicVersioning(configVersionKeyPath)).setEnableDowngrading(false)
					.setMergeRule(MergeRule.MAPPINGS,true).setMergeRule(MergeRule.MAPPING_AT_SECTION,true).setMergeRule(MergeRule.SECTION_AT_MAPPING,true)
					.setKeepAll(true).build());

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	protected static void setGeneralVariables(){

		try {

			lang = StellarPlugin.getConfigInstance().getConfigFile().getString("Lang");
			debug = StellarPlugin.getConfigInstance().getConfigFile().getBoolean("Debug_Mode");
			bStats = StellarPlugin.getConfigInstance().getConfigFile().getBoolean("BStats");
			autoUpdateConfigs = StellarPlugin.getConfigInstance().getConfigFile().getBoolean("Auto_Update_Configs");

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	protected void callLoadConfigVariables() {

		try {

			loadConfigVariables();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	abstract void loadConfigVariables();

}
