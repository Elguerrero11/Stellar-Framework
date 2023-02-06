package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarPluginConfig;
import com.elguerrero.stellarframework.config.StellarPluginMessages;
import com.elguerrero.stellarframework.utils.GeneralUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class DebugCommand {

	public static void registerPluginDebugCommand() {
		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " debug")
				.withRequirement((sender) -> {
					if (sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".debug") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*")) {
						return true;
					} else {
						sender.sendMessage(GeneralUtils.colorize(StellarPluginMessages.getNO_PERMISSION()));
						return false;
					}
				})
				.withHelp("Enable and disable the debug mode of the plugin", "Send the plugin debug messages to the console and the players")
				.executes((sender, args) -> {

					if (StellarPluginConfig.getCONFIG_FILE().getBoolean("General_Options.Debug")) {
						StellarPluginConfig.getCONFIG_FILE().set("General_Options.Debug", false);
						sender.sendMessage(GeneralUtils.colorize(StellarPluginMessages.getDEBUG_DISABLED()));
						StellarPlugin.getPLUGIN_LOGGER().info(GeneralUtils.colorize(StellarPlugin.getLOG_PREFIX() + "&ei &7Debug mode has been disabled x"));
					} else {
						StellarPluginConfig.getCONFIG_FILE().set("General_Options.Debug", true);
						sender.sendMessage(GeneralUtils.colorize(StellarPluginMessages.getDEBUG_ENABLED()));
						StellarPlugin.getPLUGIN_LOGGER().info(GeneralUtils.colorize(StellarPlugin.getLOG_PREFIX() + "&ei &7Debug mode has been enabled √"));
					}

				})
				.register();

		InfoCommand.getPluginInfoListMessagesPage2().add("&edebug &7- &fEnable or disable the debug mode of the plugin");
	}

}
