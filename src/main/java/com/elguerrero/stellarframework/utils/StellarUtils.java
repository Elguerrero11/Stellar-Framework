package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StellarUtils {

	public static String colorize(String message) {
		return message.replaceAll("%plugin_prefix%", StellarMessages.getPLUGIN_PREFIX())
				.replaceAll("%plugin_prefix_debug%", StellarMessages.getPLUGIN_PREFIX_DEBUG())
				.replaceAll("&", "§");
	}

	public static void tellNoPrefix(Player player, String message) {
		player.sendMessage(colorize(message));
	}

	public static void sendDebugMessage(String message) {
		if (StellarConfig.getDEBUG()) {
			StellarPlugin.getPLUGIN_LOGGER().info(colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&7[&eDEBUG&7] " + message));
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".debug")) {
				player.sendMessage(colorize(StellarMessages.getDEBUG_MESSAGE_FORMAT() + message));
			}
		}
	}

	public static void sendMessageDebugStatus(){

		if (StellarConfig.getDEBUG()) {
			StellarPlugin.getPLUGIN_LOGGER().info(colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode is enabled √"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".debug")) {
					player.sendMessage(colorize(StellarMessages.getDEBUG_STATUS_ENABLED()));
				}
			}
		} else {
			StellarPlugin.getPLUGIN_LOGGER().info(colorize(StellarPlugin.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode is disabled x"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission(StellarPlugin.getPLUGIN_NAME() + ".debug")) {
					player.sendMessage(colorize(StellarMessages.getDEBUG_STATUS_DISABLED()));
				}
			}
		}

	}

}
