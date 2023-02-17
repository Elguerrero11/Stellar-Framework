package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.config.StellarLangManager;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

import java.io.IOException;

public abstract class StellarReloadCommand {

	public static void registerPluginReloadCommand() {
		new CommandAPICommand(StellarPluginFramework.getPLUGIN_NAME() + "-reload")
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
						StellarConfig.getCONFIG_FILE().reload();
						StellarLangManager.getSELECTED_LANGUAGE_FILE().reload();
					} catch (IOException ex) {

						// TODO: Handle this exception with debug mode method
						StellarUtils.sendErrorMessageConsole(ex);
					}

					if (StellarUtils.senderIsConsole(sender)) {
						StellarUtils.sendConsoleInfoMessage("&ei &aThe plugin has been reloaded. V");
					} else {
						StellarUtils.sendMessagePlayer((Player) sender, StellarUtils.colorize(StellarMessages.getRELOAD()));
						StellarUtils.sendConsoleInfoMessage("&ei &aThe plugin has been reloaded by " + sender.getName() + ". V");
					}

				})
				.register();

	}

}
