package com.elguerrero.stellarframework.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class StellarMessages implements StellarMessagesManager{

	// All the messages
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static Integer MESSAGES_VERSION = null;
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String PLUGIN_PREFIX = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String PLUGIN_PREFIX_DEBUG = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_ENABLED = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_DISABLED = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_STATUS_ENABLED = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_STATUS_DISABLED = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String DEBUG_MESSAGE_FORMAT = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String RELOAD = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String PLUGIN_ERROR = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String NO_PERMISSION = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Already_Enabled = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Already_Disabled = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Disabled = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Enabled = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Not_Found = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Not_Registered = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Cant_Reload = "";
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private static String Addon_Reloaded = "";


}
