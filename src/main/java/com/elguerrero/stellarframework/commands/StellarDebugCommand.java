package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

import java.io.Console;

public class StellarDebugCommand {

	public static void registerPluginDebugCommand() {
		new CommandAPICommand(StellarPluginFramework.getPLUGIN_NAME() + " debug")
				.withRequirement((sender) -> {
					if (sender instanceof Player player) {
						if (StellarUtils.checkPlayerPermission(player, "debug", true)){
							return true;
						} else {
							return false;
						}
					} else {
						return true;
					}

				})
				.withHelp("Enable and disable the debug mode of the plugin", "Send the plugin debug messages to the console and the players")
				.executes((sender, args) -> {

					if (StellarConfig.getCONFIG_FILE().getBoolean("General_Options.Debug")) {
						StellarConfig.getCONFIG_FILE().set("General_Options.Debug", false);
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getDEBUG_DISABLED()));
						StellarPluginFramework.getPLUGIN_LOGGER().info(StellarUtils.colorize("&ei &7Debug mode has been disabled by" + StellarUtils.checkSenderInstance(sender) + "x"));
					} else {
						StellarConfig.getCONFIG_FILE().set("General_Options.Debug", true);
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getDEBUG_ENABLED()));
						StellarPluginFramework.getPLUGIN_LOGGER().info(StellarUtils.colorize("&ei &7Debug mode has been enabled" + StellarUtils.checkSenderInstance(sender) + "V"));
					}

				})
				.register();

		StellarHelpCommand.getPluginHelpListMessagesPage2().add("&edebug &7- &fEnable or disable the debug mode of the plugin");
	}

}
