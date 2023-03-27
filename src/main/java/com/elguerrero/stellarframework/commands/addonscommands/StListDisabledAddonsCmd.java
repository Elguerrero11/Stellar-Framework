package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class StListDisabledAddonsCmd {

	public static void registerListDisabledAddonsCmd() {

		try {
			new CommandAPICommand(StellarPlugin.getPLUGIN_NAME() + "-disabledaddons")
					.withRequirement((sender) -> {

						if (StellarUtils.senderIsConsole(sender)) {
							return true;
						} else {
							return StellarUtils.checkPlayerPermission((Player) sender, "disabledaddons", true);
						}

					})
					.withHelp("Show the list of the disabled addons of the plugin", "")
					.executes((sender, args) -> {
						if (StellarUtils.senderIsConsole(sender)) {
							StellarUtils.sendConsoleInfoMessage("The disabled addons for this plugin are:");
							StellarUtils.sendConsoleInfoMessage(String.join(", ", AddonsManager.getInstance().getDisabledAddons().keySet()));
						} else {
							StellarUtils.sendMessagePlayer((Player) sender, "The disabled addons for this plugin are:");
							StellarUtils.sendMessagePlayer((Player) sender, String.join(", ", AddonsManager.getInstance().getDisabledAddons().keySet()));
						}
					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

}
