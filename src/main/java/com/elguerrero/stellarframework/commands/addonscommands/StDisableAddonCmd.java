package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;

public class StDisableAddonCmd {

	public static void registerDisableAddonCmd() {

		try {
			new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + "-disableaddon")
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

						String addonName = (String) args[0];

						// If the sender is the console
						if (StellarUtils.senderIsConsole(sender)) {

							if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is already disabled.");

								// If the addon is enabled, unload it
							} else if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().unregisterAddon(AddonsManager.getInstance().getDisabledAddons().get(addonName));
								StellarUtils.sendConsoleInfoMessage("&aThe addon " + addonName + " has been disabled.");

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is in the addons folder but it is not registered, restart the server to register it.");

							} else {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is not in the addons folder.");

							}

							// If the sender is a player
						} else {

							Player player = (Player) sender;

							if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Already_Disabled().replace("%addon%", addonName));

							// If the addon is enabled, unload it
							} else if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().unregisterAddon(AddonsManager.getInstance().getEnabledAddons().get(addonName));
								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Disabled().replace("%addon%", addonName));

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Not_Registered().replace("%addon%", addonName));

							} else {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Not_Found().replace("%addon%", addonName));

							}

						}
					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

}
