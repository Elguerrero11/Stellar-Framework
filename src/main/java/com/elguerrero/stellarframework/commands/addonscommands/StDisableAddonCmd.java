package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;

public abstract class StDisableAddonCmd {

	private StDisableAddonCmd() {
	}

	public static void registerDisableAddonCmd() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-disableaddon")
					.withRequirement((sender) -> {

						if (StellarUtils.senderIsConsole(sender)) {
							return true;
						} else {
							return StellarUtils.checkPlayerPermission((Player) sender, "disableaddon", true);
						}

					})
					.withArguments(new StringArgument("addon"))
					.withHelp("Disable a addon for the plugin", "")
					.executes((sender, args) -> {

						final String addonName = (String) args[0];
						final String addonString = "&cThe addon";
						final String addonStringPlaceholder = "%addon%";

						// If the sender is the console
						if (StellarUtils.senderIsConsole(sender)) {

							if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " is already disabled.");

								// If the addon is enabled, unload it
							} else if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().unregisterAddon(AddonsManager.getInstance().getDisabledAddons().get(addonName));
								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " has been disabled.");

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " is in the addons folder but it is not registered, restart the server to register it.");

							} else {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is not in the addons folder.");

							}

							// If the sender is a player
						} else {

							Player player = (Player) sender;

							if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarPlugin.getMessagesInstance().getAddonAlreadyDisabled().replace(addonStringPlaceholder, addonName));

							// If the addon is enabled, unload it
							} else if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().unregisterAddon(AddonsManager.getInstance().getEnabledAddons().get(addonName));
								StellarUtils.sendMessagePlayer(player, StellarPlugin.getMessagesInstance().getAddonDisabled().replace(addonStringPlaceholder, addonName));

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarPlugin.getMessagesInstance().getAddonNotRegistered().replace(addonStringPlaceholder, addonName));

							} else {

								StellarUtils.sendMessagePlayer(player, StellarPlugin.getMessagesInstance().getAddonNotFound().replace(addonStringPlaceholder, addonName));

							}

						}
					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

}
