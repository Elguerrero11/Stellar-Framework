package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPluginFramework;
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
			StellarPluginFramework.getPLUGIN_LOGGER().info(colorize(StellarPluginFramework.getPLUGIN_LOG_PREFIX() + "&7[&eDEBUG&7] " + message));
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".debug")) {
				player.sendMessage(colorize(StellarMessages.getDEBUG_MESSAGE_FORMAT() + message));
			}
		}
	}

	public static void sendDebugErrorMessage(){
		StellarPluginFramework.getPLUGIN_LOGGER().severe(colorize(StellarPluginFramework.getPLUGIN_LOG_PREFIX() + "&cAn error ocurred with the plugin, please check the errors.log file in the plugin folder"));
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".debug")) {
				player.sendMessage(colorize(StellarMessages.getPLUGIN_ERROR()));
			}
		}
	}

	public static void sendMessageDebugStatus(){

		if (StellarConfig.getDEBUG()) {
			StellarPluginFramework.getPLUGIN_LOGGER().info(colorize(StellarPluginFramework.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode is enabled √"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".debug")) {
					player.sendMessage(colorize(StellarMessages.getDEBUG_STATUS_ENABLED()));
				}
			}
		} else {
			StellarPluginFramework.getPLUGIN_LOGGER().info(colorize(StellarPluginFramework.getPLUGIN_LOG_PREFIX() + "&ei &7Debug mode is disabled x"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".debug")) {
					player.sendMessage(colorize(StellarMessages.getDEBUG_STATUS_DISABLED()));
				}
			}
		}

	}

	public static void checkPluginFolder(){

		try {

		if (!StellarPluginFramework.getInstance().getDataFolder().exists()) {

			if (StellarPluginFramework.getInstance().getDataFolder().mkdirs()){
				sendDebugMessage("&ei &7Plugin folder created √");
			} else {
				sendDebugMessage("&ei &cWas a problem creating the plugin folder so this has not be created x");
			}


		} else {
			sendDebugMessage("&ei &7Plugin folder already exists so it not need to be created √");
		}

		} catch (Exception ex){
			ex.printStackTrace();
			sendDebugMessage("&ei &cA problem ocurred when check that the plugin folder exist and create if not x");
		}

	}

}
