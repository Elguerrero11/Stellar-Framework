package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class StDebugCmd {

	private StDebugCmd() {
	}

	public static void registerDebugCommand() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-debug")
					.withRequirement((sender) -> {
						if (!StellarUtils.senderIsConsole(sender) && StellarUtils.checkPlayerPermission((Player) sender, "debug", true)) {
							return true;
						} else if (!(StellarUtils.senderIsConsole(sender) && StellarUtils.checkPlayerPermission((Player) sender, "debug", false))) {
							return false;
						} else {
							return true;
						}
					})
					.withHelp("Enable and disable the debug mode of the plugin", "Send the plugin debug messages to the console and the players")
					.executes((sender, args) -> {

						final String configDebugPath = "General_Options.Debug";

						if (StellarPlugin.getConfigInstance().getConfigFile().getBoolean(configDebugPath)) {
							StellarPlugin.getConfigInstance().getConfigFile().set(configDebugPath, false);
							if (StellarUtils.senderIsConsole(sender)) {
								StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been disabled by the console. X");
							} else {
								StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarPlugin.getMessagesInstance().getDebugDisabled()));
								StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been disabled by " + sender.getName() + ". X");
							}
						} else {
							StellarPlugin.getConfigInstance().getConfigFile().set(configDebugPath, true);
							if (StellarUtils.senderIsConsole(sender)) {
								StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been enabled by the console");
							} else {
								StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarPlugin.getMessagesInstance().getDebugEnabled()));
								StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode has been enabled by " + sender.getName() + ". V");
							}
						}

					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

}
