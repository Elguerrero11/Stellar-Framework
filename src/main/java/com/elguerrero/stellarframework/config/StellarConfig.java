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


public abstract class StellarConfig implements StellarConfigManager {

	@Getter
	private YamlDocument configFile = null;
	private String configFilePath;
	private InputStream inputStream;
	private String configVersionPath;

	private StellarConfig(String configFilePath, String inputStream, String configVersionPath) {

		this.configFilePath = configFilePath;
		this.inputStream = StellarPlugin.getPluginInstance().getResource(inputStream);
		this.configVersionPath = configVersionPath;

	}

	public void loadConfigFile() {

		try {

			this.configFile = YamlDocument.create(new File(StellarPlugin.getPluginInstance().getPluginFolder(), this.configFilePath), this.inputStream,
					GeneralSettings.DEFAULT,
					LoaderSettings.builder().setAutoUpdate(StellarPlugin.getPluginInstance().getAutoUpdateConfigs()).setAllowDuplicateKeys(false).build(),
					DumperSettings.DEFAULT,
					UpdaterSettings.builder().setVersioning(new BasicVersioning(configVersionPath)).setEnableDowngrading(false)
					.setMergeRule(MergeRule.MAPPINGS,true).setMergeRule(MergeRule.MAPPING_AT_SECTION,true).setMergeRule(MergeRule.SECTION_AT_MAPPING,true)
					.setKeepAll(true).build());


			loadConfigVariables();

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex, "default");
		}


	}

}
