package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;

public class StReloadAddonCmd {

	private StReloadAddonCmd() {
	}

	public static void registerReloadAddonCmd() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-reloadaddon")
					.withRequirement((sender) -> {

						if (StellarUtils.senderIsConsole(sender)) {
							return true;
						} else {
							return StellarUtils.checkPlayerPermission((Player) sender, "reloadaddon", true);
						}

					})
					.withArguments(new StringArgument("addon"))
					.withHelp("Reload a addon", "Will reload the addon config and messages")
					.executes((sender, args) -> {

						final String addonName = (String) args[0];
						final String addonString = "&aThe addon";
						final String addonString2 = "&cThe addon";
						final String addonStringPlaceholder = "%addon%";

						// If the sender is the console
						if (StellarUtils.senderIsConsole(sender)) {

							if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().reloadAddonConfig(AddonsManager.getInstance().getEnabledAddons().get(addonName));
								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " has be reloaded.");

							} else if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " is not enabled so can't be reloaded.");

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendConsoleInfoMessage(addonString2 + addonName + " is in the addons folder but it is not registered, restart the server to register it.");

							} else {

								StellarUtils.sendConsoleInfoMessage(addonString2 + addonName + " is not in the addons folder.");

							}

							// If the sender is a player
						} else {

							Player player = (Player) sender;

							if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().reloadAddonConfig(AddonsManager.getInstance().getEnabledAddons().get(addonName));
								StellarUtils.sendMessagePlayer(player,StellarMessages.getAddon_Reloaded().replace(addonStringPlaceholder, addonName));

							} else if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Cant_Reload().replace(addonStringPlaceholder, addonName));

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Not_Registered().replace(addonStringPlaceholder, addonName));

							} else {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Not_Found().replace(addonStringPlaceholder, addonName));

							}

						}
					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

}
