package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;

public class StEnableAddonCmd {

	private StEnableAddonCmd() {
	}

	public static void registerEnableAddonCmd() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-enableaddon")
					.withRequirement((sender) -> {

						if (StellarUtils.senderIsConsole(sender)) {
							return true;
						} else {
							return StellarUtils.checkPlayerPermission((Player) sender, "enableaddon", true);
						}

					})
					.withArguments(new StringArgument("addon"))
					.withHelp("Enable a addon for the plugin", "")
					.executes((sender, args) -> {

						final String addonName = (String) args[0];
						final String addonString = "&cThe addon";
						final String addonString2 = "&aThe addon";
						final String addonStringPlaceholder = "%addon%";

						// If the sender is the console
						if (StellarUtils.senderIsConsole(sender)) {

							if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " is already enabled.");

								// If the addon is disabled, load it
							} else if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().loadAddon(addonName);
								StellarUtils.sendConsoleInfoMessage(addonString2 + addonName + " has been enabled.");

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " is in the addons folder but it is not registered, restart the server to register it.");

							} else {

								StellarUtils.sendConsoleInfoMessage(addonString + addonName + " is not in the addons folder.");

							}

						// If the sender is a player
						} else {

							Player player = (Player) sender;

							if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarPlugin.getMessagesInstance().getAddonAlreadyEnabled().replace(addonStringPlaceholder, addonName));

								// If the addon is disabled, load it
							} else if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().loadAddon(addonName);
								StellarUtils.sendMessagePlayer(player, StellarPlugin.getMessagesInstance().getAddonEnabled().replace(addonStringPlaceholder, addonName));

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
