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
	private static Boolean bstatsMetrics = null;
	@Getter
	private static Boolean autoUpdateConfigs = false;
	@Getter
	private YamlDocument configFile = null;
	private String configFilePath;
	private InputStream inputStream;
	private String configVersionKeyPath;

	private StellarConfig() {

		this.configFilePath = "config.yml";
		this.inputStream = StellarPlugin.getPluginInstance().getResource("config.yml");
		this.configVersionKeyPath = "Config_Version";

	}

	private StellarConfig(String configFilePath, String inputStream, String configVersionKeyPath) {

		this.configFilePath = configFilePath;
		this.inputStream = StellarPlugin.getPluginInstance().getResource(inputStream);
		this.configVersionKeyPath = configVersionKeyPath;

	}

	public void loadConfigFile() {

		try {

			configFile = YamlDocument.create(new File(StellarPlugin.getPluginInstance().getPluginFolder(), configFilePath), inputStream,
					GeneralSettings.DEFAULT,
					LoaderSettings.builder().setAutoUpdate(autoUpdateConfigs).setAllowDuplicateKeys(false).build(),
					DumperSettings.DEFAULT,
					UpdaterSettings.builder().setVersioning(new BasicVersioning(configVersionKeyPath)).setEnableDowngrading(false)
					.setMergeRule(MergeRule.MAPPINGS,true).setMergeRule(MergeRule.MAPPING_AT_SECTION,true).setMergeRule(MergeRule.SECTION_AT_MAPPING,true)
					.setKeepAll(true).build());


			callLoadConfigVariables();

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	private void callLoadConfigVariables() {

		try {

			loadConfigVariables();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	abstract void loadConfigVariables();

}
