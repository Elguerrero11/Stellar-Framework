package com.elguerrero.stellarframework.commands.addonscommands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class StListEnabledAddonsCmd {

	private StListEnabledAddonsCmd() {
	}

	public static void registerListEnabledAddonsCommand() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-enabledaddons")
					.withRequirement((sender) -> {

						if (StellarUtils.senderIsConsole(sender)) {
							return true;
						} else {
							return StellarUtils.checkPlayerPermission((Player) sender, "enabledaddons", true);
						}

					})
					.withHelp("Show the list of the enabled addons of the plugin", "")
					.executes((sender, args) -> {
						if (StellarUtils.senderIsConsole(sender)) {
							StellarUtils.sendConsoleInfoMessage("The enabled addons for this plugin are:");
							StellarUtils.sendConsoleInfoMessage(String.join(", ", AddonsManager.getInstance().getEnabledAddons().keySet()));
						} else {
							StellarUtils.sendMessagePlayer((Player) sender, "The enabled addons for this plugin are:");
							StellarUtils.sendMessagePlayer((Player) sender, String.join(", ", AddonsManager.getInstance().getEnabledAddons().keySet()));
						}
					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

}
