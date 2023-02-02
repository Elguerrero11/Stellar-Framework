package es.elguerrero.stellarframework.utils;

import org.bukkit.entity.Player;

public class GeneralUtils {

	// Here will be a checker for see if the version of the plugin is the latest

	/*public boolean thisPluginIsUpdated(Double mayorVersion, Integer minorVersion){

		if (!(minorVersion == null)){


		} else if (){

		}
	}*/

	public static String colorize(String message) {
		return message.replace("&", "§");
	}

	public static void tellNoPrefix(Player player, String message) {
		player.sendMessage(colorize(message));
	}

}
