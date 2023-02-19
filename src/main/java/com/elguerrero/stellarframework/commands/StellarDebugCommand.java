package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class StellarDebugCommand {

	public static void registerDebugCommand() {
		new CommandAPICommand(StellarPluginFramework.getPLUGIN_NAME() + "-debug")
				.withRequirement((sender) -> {
					if (!StellarUtils.senderIsConsole(sender) && StellarUtils.checkPlayerPermission((Player) sender, "debug", true)){
						return true;
						} else if (!(StellarUtils.senderIsConsole(sender) && StellarUtils.checkPlayerPermission((Player) sender, "debug", false))) {
						return false;
					    } else {
						return true;
						}
				})
				.withHelp("Enable and disable the debug mode of the plugin", "Send the plugin debug messages to the console and the players")
				.executes((sender, args) -> {

					if (StellarConfig.getCONFIG_FILE().getBoolean("General_Options.Debug")) {
						StellarConfig.getCONFIG_FILE().set("General_Options.Debug", false);
						if (StellarUtils.senderIsConsole(sender)){
							StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been disabled by the console. X");
						} else {
							StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarMessages.getDEBUG_DISABLED()));
							StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been disabled by " + sender.getName() + ". X");
						}
					} else {
						StellarConfig.getCONFIG_FILE().set("General_Options.Debug", true);
						if (StellarUtils.senderIsConsole(sender)){
							StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been enabled by the console");
						} else {
							StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarMessages.getDEBUG_ENABLED()));
							StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been enabled by " + sender.getName() + ". V");
						}
					}

				})
				.register();
	}

}
