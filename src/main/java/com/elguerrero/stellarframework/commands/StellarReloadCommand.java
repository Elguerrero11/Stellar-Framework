package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;

import java.io.IOException;

public abstract class StellarReloadCommand {

	public static void registerPluginReloadCommand() {
		new CommandAPICommand(StellarPluginFramework.getPLUGIN_NAME() + " reload")
				.withRequirement((sender) -> {
					if (sender.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".reload") || sender.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".*")) {
						return true;
					} else {
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getNO_PERMISSION()));
						return false;
					}
				})
				.withHelp("Reload the plugin", "Reload the plugin config.yml and messages.yml")
				.executes((sender, args) -> {

					try {
						StellarConfig.getCONFIG_FILE().reload();
						StellarMessages.getMESSAGES_FILE().reload();
					} catch (IOException ex) {

						// TODO: Handle this exception with debug mode method
						// TODO: Handle this too with the error.log file
						ex.printStackTrace();
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getPLUGIN_ERROR()));
					}

					sender.sendMessage(StellarMessages.getRELOAD());

				})
				.register();

		StellarHelpCommand.getPluginHelpListMessagesPage2().add("&ereload &7- &fReload the plugin");
	}

}
