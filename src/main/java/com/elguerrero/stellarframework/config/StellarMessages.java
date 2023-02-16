package com.elguerrero.stellarframework.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
