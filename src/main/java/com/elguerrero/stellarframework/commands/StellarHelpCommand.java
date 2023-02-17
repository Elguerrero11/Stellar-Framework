package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class StellarHelpCommand {

	@Getter(AccessLevel.PROTECTED)
	private static List<String> descriptionExtraListMessagesPage1 = new ArrayList<>();

	// Never must hexed more than 5 lines but I need to check exactly how much lines
	@Getter(AccessLevel.PROTECTED)
	private static List<String> pluginHelpListMessagesPage2 = new ArrayList<>();

	public static void registerPluginInfoCommand() {

		new CommandAPICommand(StellarPluginFramework.getPLUGIN_NAME() + "-help")
				.withRequirement((sender) -> {
					if (!StellarUtils.senderIsConsole(sender) && (StellarUtils.checkPlayerPermission((Player) sender, "help", true))) {
						return true;
					} else if (StellarUtils.senderIsConsole(sender)) {
						StellarUtils.sendConsoleInfoMessage("&cOnly the players can use this command.");
						return false;
					} else {
						return false;
					}
				})
				.withHelp("Show the plugin info", "Show the plugin info")
				.executesPlayer((player, args) -> {
					StellarUtils.sendMessagePlayer(player, "&b&m------------------------------------");
					StellarUtils.sendMessagePlayer(player, "");
					// Add the clickeable message above for go to spigot
					StellarUtils.sendMessagePlayer(player, "&7[" + StellarPluginFramework.getPLUGIN_NAME() + "&7] -" + StellarPluginFramework.getPLUGIN_VERSION() + "&bby &3" + StellarPluginFramework.getPLUGIN_AUTHOR());
					StellarUtils.sendMessagePlayer(player, " ");
					StellarUtils.sendMessagePlayer(player, "&6<> &7Optional arguments &6[] &7Required arguments");
					StellarUtils.sendMessagePlayer(player, " ");
					//sendListMessages(player);
					StellarUtils.sendMessagePlayer(player, " ");
					// In the method of above return a clickeable message
					//player.sendMessage("a" + Check if plugin is up to date with method pluginIsUpdated());
					StellarUtils.sendMessagePlayer(player, " ");
					//Add the clickeable message above
					StellarUtils.sendMessagePlayer(player, "Page 1 of " + StellarPluginFramework.getHELP_COMMAND_NUMBER_OF_PAGES() + "&6&l>>");
					StellarUtils.sendMessagePlayer(player, "&b&m------------------------------------");
				}).register();

		// Add the page 2 content
		getPluginHelpListMessagesPage2().add("&edebug &7- &fEnable or disable the debug mode of the plugin");
		getPluginHelpListMessagesPage2().add("&ereload &7- &fReload the plugin");


	}

	private void sendListMessages(Player player) {
		for (String message : descriptionExtraListMessagesPage1) {
			StellarUtils.sendMessagePlayer(player, message);
		}
	}

	private void sendPluginInfoPage2(Player player) {

		StellarUtils.sendMessagePlayer(player, "&b&m------------------------------------");
		for (String message : pluginHelpListMessagesPage2) {
			StellarUtils.sendMessagePlayer(player, message);
		}
		sendPlayerPage2ClickeableMessage(player);
		StellarUtils.sendMessagePlayer(player, "&b&m------------------------------------");
	}

	private void sendPlayerPage2ClickeableMessage(Player player) {
		// Add the clickeable message above
		StellarUtils.sendMessagePlayer(player, "Page 2 of " + StellarPluginFramework.getHELP_COMMAND_NUMBER_OF_PAGES());
		// Replace for the clickeable message
		// Too center the message arrows
		StellarUtils.sendMessagePlayer(player, "&6&l <<          &6&l>>");
	}
}
