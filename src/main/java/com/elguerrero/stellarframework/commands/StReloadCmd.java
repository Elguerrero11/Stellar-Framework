package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class StReloadCmd {

	private StReloadCmd() {
	}

	public static void registerReloadCommand() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-reload")
					.withRequirement((sender) -> {
						if (!StellarUtils.senderIsConsole(sender) && StellarUtils.checkPlayerPermission((Player) sender, "reload", true)) {
							return true;
						} else if (!(StellarUtils.senderIsConsole(sender) && StellarUtils.checkPlayerPermission((Player) sender, "reload", false))) {
							return false;
						} else {
							return true;
						}
					})
					.withHelp("Reload the plugin", "Reload the plugin config.yml and en_US.yml")
					.executes((sender, args) -> {

						try {
							StellarUtils.loadPluginConfigs();

							if (StellarUtils.senderIsConsole(sender)) {
								StellarUtils.sendConsoleInfoMessage("&ei &aThe plugin has been reloaded. V");
							} else {
								StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarPlugin.getBasicMessagesInstance().getPluginReloaded()));
								StellarUtils.sendConsoleInfoMessage("&ei &aThe plugin has been reloaded by " + sender.getName() + ". V");
							}
						} catch (Exception ex) {

							if (!StellarUtils.senderIsConsole(sender)) {
								StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarPlugin.getBasicMessagesInstance().getPluginError()));
							}
							StellarUtils.logErrorException(ex, sender.getName() + "has tried to reload the plugin but an error ocurred, please check your errors.log file in the plugin folder.");
						}

					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

}
