package com.elguerrero.stellarframework.config;

import com.elguerrero.stellarframework.StellarPluginFramework;
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

public abstract class StellarMessages {

	// All the messages
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static Integer MESSAGES_VERSION = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String PLUGIN_PREFIX = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String PLUGIN_PREFIX_DEBUG = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_ENABLED = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_DISABLED = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_STATUS_ENABLED = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_STATUS_DISABLED = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_MESSAGE_FORMAT = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String RELOAD = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String PLUGIN_ERROR = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String NO_PERMISSION = null;


}
