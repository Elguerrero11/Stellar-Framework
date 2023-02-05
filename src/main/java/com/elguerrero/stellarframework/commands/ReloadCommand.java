package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarPluginConfig;
import com.elguerrero.stellarframework.config.StellarPluginMessages;
import com.elguerrero.stellarframework.utils.GeneralUtils;
import dev.jorel.commandapi.CommandAPICommand;

import java.io.IOException;

public abstract class ReloadCommand {


	public static void registerPluginReloadCommand() {
		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " reload")
				.withRequirement(sender -> sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".reload") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*"))
				.withHelp("Reload the plugin", "Reload the plugin config.yml and messages.yml")
				.executes((sender, args) -> {

					try {
						StellarPluginConfig.getCONFIG_FILE().reload();
						StellarPluginMessages.getMESSAGES_FILE().reload();
					} catch (IOException ex) {

						// TODO: Handle this exception with debug mode method
						// TODO: Handle this too with the error.log file
						ex.printStackTrace();
						sender.sendMessage(GeneralUtils.colorize(StellarPluginMessages.getERROR()));
					}

					sender.sendMessage(StellarPluginMessages.getRELOAD());

				})
				.register();

		InfoCommand.getPluginInfoListMessagesPage2().add("&ereload &7- &fReload the plugin");
	}

}
