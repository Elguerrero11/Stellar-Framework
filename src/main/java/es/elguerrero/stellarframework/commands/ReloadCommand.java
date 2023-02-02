package es.elguerrero.stellarframework.commands;

import dev.jorel.commandapi.CommandAPICommand;
import es.elguerrero.stellarframework.StellarPlugin;
import es.elguerrero.stellarframework.config.StellarPluginConfig;
import es.elguerrero.stellarframework.config.StellarPluginMessages;

import java.io.IOException;

public abstract class ReloadCommand {


	public static void registerPluginReloadCommand() {
		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " reload")
				.withRequirement(sender -> sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".reload") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*"))
				.withHelp("Reload the plugin or specified things of that", "You can reload all the plugin with 'all', only the config with 'config' or the messages with 'messages'")
				.executes((sender, args) -> {

					try {
						StellarPluginConfig.getCONFIG_FILE().reload();
						StellarPluginMessages.getMESSAGES_FILE().reload();
					} catch (IOException ex) {

						// TODO: Handle this exception with debug mode method
						ex.printStackTrace();
						sender.sendMessage("&cThere was an error reloading the plugin.");
						sender.sendMessage("&cCheck the console for more information");
					}

					sender.sendMessage("&aThe plugin has be reloaded √");
					
				})
				.register();

		InfoCommand.getPluginInfoListMessagesPage2().add("&ereload &7- &fReload the plugin");
	}

}
