package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.Config;
import com.elguerrero.stellarframework.config.Messages;
import com.elguerrero.stellarframework.utils.GeneralUtils;
import dev.jorel.commandapi.CommandAPICommand;

public class DebugCommand {

	public static void registerPluginDebugCommand() {
		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " debug")
				.withRequirement((sender) -> {
					if (sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".debug") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*")) {
						return true;
					} else {
						sender.sendMessage(GeneralUtils.colorize(Messages.getNO_PERMISSION()));
						return false;
					}
				})
				.withHelp("Enable and disable the debug mode of the plugin", "Send the plugin debug messages to the console and the players")
				.executes((sender, args) -> {

					if (Config.getCONFIG_FILE().getBoolean("General_Options.Debug")) {
						Config.getCONFIG_FILE().set("General_Options.Debug", false);
						sender.sendMessage(GeneralUtils.colorize(Messages.getDEBUG_DISABLED()));
						StellarPlugin.getPLUGIN_LOGGER().info(GeneralUtils.colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode has been disabled x"));
					} else {
						Config.getCONFIG_FILE().set("General_Options.Debug", true);
						sender.sendMessage(GeneralUtils.colorize(Messages.getDEBUG_ENABLED()));
						StellarPlugin.getPLUGIN_LOGGER().info(GeneralUtils.colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode has been enabled √"));
					}

				})
				.register();

		HelpCommand.getPluginHelpListMessagesPage2().add("&edebug &7- &fEnable or disable the debug mode of the plugin");
	}

}
