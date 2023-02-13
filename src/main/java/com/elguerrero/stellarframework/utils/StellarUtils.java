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
			StellarPluginFramework.getPLUGIN_LOGGER().info(colorize("&7[&eDEBUG&7] " + message));
		}
	}

	public static void sendPluginErrorMessage(){

		StellarPluginFramework.getPLUGIN_LOGGER().severe(colorize("&cAn error ocurred with the plugin, please check the errors.log file in the plugin folder"));
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (checkPlayerPermission(player, "debug", false)) {
				player.sendMessage(colorize(StellarMessages.getPLUGIN_ERROR()));
			}
		}
	}

	public static void sendMessageDebugStatus(){

		if (StellarConfig.getDEBUG()) {
			StellarPluginFramework.getPLUGIN_LOGGER().info(colorize("&ei &7Debug mode is enabled √"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (checkPlayerPermission(player, "debug", false)) {
					player.sendMessage(colorize(StellarMessages.getDEBUG_STATUS_ENABLED()));
				}
			}
		} else {
			StellarPluginFramework.getPLUGIN_LOGGER().info(colorize("&ei &7Debug mode is disabled x"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (checkPlayerPermission(player, "debug", false)) {
					player.sendMessage(colorize(StellarMessages.getDEBUG_STATUS_DISABLED()));
				}
			}
		}

	}

	public static void checkPluginFolder(){

		try {

		if (!StellarPluginFramework.getINSTANCE().getDataFolder().exists()) {

			StellarPluginFramework.getINSTANCE().getDataFolder().mkdirs();

		}

		} catch (Exception ex){
			ex.printStackTrace();

		}

	}

	public static Boolean checkPlayerPermission(Player player, String permission, Boolean sendNoPermissionMessage){

		if (player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".*") || player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + "." + permission)) {
			return true;
		} else if (sendNoPermissionMessage) {
				player.sendMessage(StellarUtils.colorize(StellarMessages.getNO_PERMISSION()));
			}
			return false;
		}

	public static String checkSenderInstance(Object sender){
		if (sender instanceof Player) {
			return ((Player) sender).getName();
		} else {
			return "console";
		}
	}

	public static void sendConsoleInfoMessage(String message){
		StellarPluginFramework.getPLUGIN_LOGGER().info(colorize(message));
	}

	public static void sendConsoleWarnMessage(String message){
		StellarPluginFramework.getPLUGIN_LOGGER().warning(colorize(message));
	}

	public static void sendConsoleSevereMessage(String message){
		StellarPluginFramework.getPLUGIN_LOGGER().severe(colorize(message));
	}



}
