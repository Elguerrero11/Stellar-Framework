package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarPluginConfig;
import com.elguerrero.stellarframework.config.StellarPluginMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GeneralUtils {

	public static String colorize(String message) {
		return message.replaceAll("&", "§")
				.replaceAll("%plugin_prefix%", StellarPluginMessages.getPLUGIN_PREFIX())
				.replaceAll("%plugin_prefix_debug%", StellarPluginMessages.getPLUGIN_PREFIX_DEBUG());
	}

	public static void tellNoPrefix(Player player, String message) {
		player.sendMessage(colorize(message));
	}

	public static void sendDebugMessage(String message) {
		if (StellarPluginConfig.getDEBUG()) {
			StellarPlugin.getPLUGIN_LOGGER().info(colorize(StellarPlugin.getLOG_PREFIX() + "&7[&eDEBUG&7] " + message));
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("stellarframework.debug")) {
				player.sendMessage(colorize(StellarPluginMessages.getDEBUG_MESSAGE_FORMAT() + message));
			}
		}
	}

	public static void sendMessageDebugStatus(){

		if (StellarPluginConfig.getDEBUG()) {
			StellarPlugin.getPLUGIN_LOGGER().info(colorize(StellarPlugin.getLOG_PREFIX() + "&ei &7Debug mode is enabled √"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("stellarframework.debug")) {
					player.sendMessage(colorize(StellarPluginMessages.getDEBUG_STATUS_ENABLED()));
				}
			}
		} else {
			StellarPlugin.getPLUGIN_LOGGER().info(colorize(StellarPlugin.getLOG_PREFIX() + "&ei &7Debug mode is disabled x"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("stellarframework.debug")) {
					player.sendMessage(colorize(StellarPluginMessages.getDEBUG_STATUS_DISABLED()));
				}
			}
		}

	}

}
