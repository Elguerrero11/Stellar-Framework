package es.elguerrero.stellarframework.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import es.elguerrero.stellarframework.StellarPlugin;
import es.elguerrero.stellarframework.config.StellarPluginConfig;
import es.elguerrero.stellarframework.config.StellarPluginMessages;

import java.io.IOException;

public abstract class ReloadCommand {


	public static void registerPluginReloadCommand() {
		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " reload")
				.withRequirement(sender -> sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".reload") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*"))
				.withHelp("Reload the plugin or specified things of that", "You can reload all the plugin with 'all', only the config with 'config' or the messages with 'messages'")
				.withArguments(new StringArgument("arg0").includeSuggestions(ArgumentSuggestions.strings("config", "messages")))
				.executes((sender, args) -> {

					/*if (args[0].equals("all")) {

						try {
							StellarPluginConfig.getCONFIG_FILE().reload();
							StellarPluginMessages.getMESSAGES_FILE().reload();
						} catch (IOException ex) {

							// TODO: Handle this exception with debug mode method
							ex.printStackTrace();
							sender.sendMessage("&cThere was an error reloading the plugin config or messages.");
							sender.sendMessage("&cCheck the console for more information");
						}

						sender.sendMessage("&aThe plugin config and messages has be reloaded √");

			} else*/
					if (args[0].equals("config")) {

						try {
							StellarPluginConfig.getCONFIG_FILE().reload();
						} catch (IOException ex) {

							// TODO: Handle this exception with debug mode method
							ex.printStackTrace();
							sender.sendMessage("&cThere was an error reloading the plugin config.");
							sender.sendMessage("&cCheck the console for more information");
						}

						sender.sendMessage("&aThe plugin config has be reloaded √");

					} else if (args[0].equals("messages")) {

						try {
							StellarPluginMessages.getMESSAGES_FILE().reload();
						} catch (IOException ex) {

							// TODO: Handle this exception with debug mode method
							ex.printStackTrace();
							sender.sendMessage("&cThere was an error reloading the plugin messages.");
							sender.sendMessage("&cCheck the console for more information");
						}

						sender.sendMessage("&aThe plugin messages has be reloaded √");

					} else {

						try {
							StellarPluginConfig.getCONFIG_FILE().reload();
							StellarPluginMessages.getMESSAGES_FILE().reload();
						} catch (IOException ex) {

							// TODO: Handle this exception with debug mode method
							ex.printStackTrace();
							sender.sendMessage("&cThere was an error reloading the plugin config or messages.");
							sender.sendMessage("&cCheck the console for more information");
						}

						sender.sendMessage("&aThe plugin config and messages has be reloaded √");
						//sender.sendMessage("You need to specify what you want to reload");
					}

				})
				.register();

		InfoCommand.getPluginInfoListMessagesPage1().add("&ereload &7- &fReload the plugin or specified things of that");
	}

}
