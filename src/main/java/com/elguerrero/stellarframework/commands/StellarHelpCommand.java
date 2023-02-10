package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import lombok.Getter;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class StellarHelpCommand {

	@Getter
	private static List<String> descriptionExtraListMessagesPage1 = null;

	// Never must hexed more than 5 lines but I need to check exactly how much lines
	@Getter
	private static List<String> pluginHelpListMessagesPage2 = null;

	public static void registerPluginInfoCommand() {

		new CommandAPICommand(StellarPluginFramework.getInstance().getPluginName() + " help")
				.withRequirement((sender) -> {
					if (sender instanceof Player && (StellarUtils.checkPlayerPermission((Player) sender, "help", true))) {
						return true;
					} else {
						sender.sendMessage(StellarUtils.colorize(StellarMessages.getNO_PERMISSION()));
						return false;
					}
				})
				.withHelp("Show the plugin info", "Show the plugin info")
				.executesPlayer((player, args) -> {
					StellarUtils.tellNoPrefix(player, "&b&m------------------------------------");
					StellarUtils.tellNoPrefix(player, "");
					// Add the clickeable message above for go to spigot
					StellarUtils.tellNoPrefix(player, "&7[" + StellarPluginFramework.getPLUGIN_NAME() + "&7] -" + StellarPluginFramework.getPLUGIN_VERSION() + "&bby &3" + StellarPluginFramework.getPLUGIN_AUTOR());
					StellarUtils.tellNoPrefix(player, " ");
					StellarUtils.tellNoPrefix(player, "&6<> &7Optional arguments &6[] &7Required arguments");
					StellarUtils.tellNoPrefix(player, " ");
					//sendListMessages(player);
					StellarUtils.tellNoPrefix(player, " ");
					// In the method of above return a clickeable message
					//player.sendMessage("a" + Check if plugin is up to date with method pluginIsUpdated());
					StellarUtils.tellNoPrefix(player, " ");
					//Add the clickeable message above
					StellarUtils.tellNoPrefix(player, "Page 1 of " + StellarPluginFramework.getNUMBER_OF_PAGES() + "&6&l>>");
					StellarUtils.tellNoPrefix(player, "&b&m------------------------------------");
				}).register();

	}

	private void sendListMessages(Player player) {
		for (String message : descriptionExtraListMessagesPage1) {
			StellarUtils.tellNoPrefix(player, message);
		}
	}

	private void sendPluginInfoPage2(Player player) {

		StellarUtils.tellNoPrefix(player, "&b&m------------------------------------");
		for (String message : pluginHelpListMessagesPage2) {
			StellarUtils.tellNoPrefix(player, message);
		}
		sendPlayerPage2ClickeableMessage(player);
		StellarUtils.tellNoPrefix(player, "&b&m------------------------------------");
	}

	private void sendPlayerPage2ClickeableMessage(Player player) {
		// Add the clickeable message above
		StellarUtils.tellNoPrefix(player, "Page 2 of " + StellarPluginFramework.getNUMBER_OF_PAGES());
		// Replace for the clickeable message
		// Too center the message arrows
		StellarUtils.tellNoPrefix(player, "&6&l <<          &6&l>>");
	}
}
