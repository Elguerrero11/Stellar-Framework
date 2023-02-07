package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;

public class StellarDebugCommand {

	public static void registerPluginDebugCommand() {
		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " debug")
				.withRequirement((sender) -> {
					if (sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".debug") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*")) {
						return true;
					} else {
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getNO_PERMISSION()));
						return false;
					}
				})
				.withHelp("Enable and disable the debug mode of the plugin", "Send the plugin debug messages to the console and the players")
				.executes((sender, args) -> {

					if (StellarConfig.getCONFIG_FILE().getBoolean("General_Options.Debug")) {
						StellarConfig.getCONFIG_FILE().set("General_Options.Debug", false);
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getDEBUG_DISABLED()));
						StellarPlugin.getPLUGIN_LOGGER().info(StellarUtils.colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode has been disabled x"));
					} else {
						StellarConfig.getCONFIG_FILE().set("General_Options.Debug", true);
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getDEBUG_ENABLED()));
						StellarPlugin.getPLUGIN_LOGGER().info(StellarUtils.colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode has been enabled √"));
					}

				})
				.register();

		StellarHelpCommand.getPluginHelpListMessagesPage2().add("&edebug &7- &fEnable or disable the debug mode of the plugin");
	}

}
