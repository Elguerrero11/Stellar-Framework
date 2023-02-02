package es.elguerrero.stellarframework.commands;

import dev.jorel.commandapi.CommandAPICommand;
import es.elguerrero.stellarframework.StellarPlugin;
import es.elguerrero.stellarframework.utils.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class InfoCommand {

	@Setter
	private static List<String> descriptionExtraListMessagesPage1 = null;

	// Never must hexed more than 5 lines but I need to check exactly how much lines
	@Getter
	private static List<String> pluginInfoListMessagesPage1 = null;

	public static void registerPluginInfoCommand() {

		new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + " help")
				.withRequirement((sender) -> sender instanceof Player && sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".info") || sender.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".*"))
				.withHelp("Show the plugin info", "Show the plugin info")
				.executesPlayer((player, args) -> {
					GeneralUtils.tellNoPrefix(player, StellarPlugin.getTellPrefix() + "&b&m------------------------------------");
					GeneralUtils.tellNoPrefix(player, "");
					// Add the clickeable message above for go to spigot
					GeneralUtils.tellNoPrefix(player, StellarPlugin.getTellPrefix() + "&7-" + StellarPlugin.getPLUGIN_NAME() + "&bby &3" + StellarPlugin.getPLUGIN_AUTOR());
					GeneralUtils.tellNoPrefix(player, " ");
					GeneralUtils.tellNoPrefix(player, "&6<> &7Optional arguments &6[] &7Required arguments");
					GeneralUtils.tellNoPrefix(player, " ");
					//sendListMessages(player);
					GeneralUtils.tellNoPrefix(player, " ");
					// In the method of above return a clickeable message
					//player.sendMessage("a" + Check if plugin is up to date with method pluginIsUpdated());
					GeneralUtils.tellNoPrefix(player, " ");
					//Add the clickeable message above
					GeneralUtils.tellNoPrefix(player, "Page 1 of " + StellarPlugin.getNumberOfPages() + "&6&l>>");
					GeneralUtils.tellNoPrefix(player, "&b&m------------------------------------");
				}).register();

	}

	private void sendListMessages(Player player) {
		for (String message : descriptionExtraListMessagesPage1) {
			GeneralUtils.tellNoPrefix(player, message);
		}
	}

	private void sendPluginInfoPage2(Player player) {

		GeneralUtils.tellNoPrefix(player, "&b&m------------------------------------");
		for (String message : pluginInfoListMessagesPage1) {
			GeneralUtils.tellNoPrefix(player, message);
		}
		sendPlayerPage2ClickeableMessage(player);
		GeneralUtils.tellNoPrefix(player, "&b&m------------------------------------");
	}

	private void sendPlayerPage2ClickeableMessage(Player player) {
		// Add the clickeable message above
		GeneralUtils.tellNoPrefix(player, "Page 2 of " + StellarPlugin.getNumberOfPages());
		// Replace for the clickeable message
		// Too center the message arrows
		GeneralUtils.tellNoPrefix(player, "&6&l <<          &6&l>>");
	}
}
