package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.addonsystem.StellarAddon;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.entity.Player;

public class StEnableAddonCmd {

	public static void registerEnableAddonCmd() {

		try {
			new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + "-enableaddon")
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

						String addonName = (String) args[0];

						// If the sender is the console
						if (StellarUtils.senderIsConsole(sender)) {

							if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is already enabled.");

								// If the addon is disabled, load it
							} else if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().loadAddon(addonName);
								StellarUtils.sendConsoleInfoMessage("&aThe addon " + addonName + " has been enabled.");

							} else if (AddonsManager.getInstance().addonJarExists(addonName)) {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is in the addons folder but it is not registered, restart the server to register it.");

							} else {

								StellarUtils.sendConsoleInfoMessage("&cThe addon " + addonName + " is not in the addons folder.");

							}

						// If the sender is a player
						} else {

							Player player = (Player) sender;

							if (AddonsManager.getInstance().getEnabledAddons().containsKey(addonName)) {

								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Already_Enabled().replace("%addon%", addonName));

								// If the addon is disabled, load it
							} else if (AddonsManager.getInstance().getDisabledAddons().containsKey(addonName)) {

								AddonsManager.getInstance().loadAddon(addonName);
								StellarUtils.sendMessagePlayer(player, StellarMessages.getAddon_Enabled().replace("%addon%", addonName));

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
