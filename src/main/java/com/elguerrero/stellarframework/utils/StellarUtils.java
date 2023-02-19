package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.commands.StellarDebugCommand;
import com.elguerrero.stellarframework.commands.StellarDebugReportCommand;
import com.elguerrero.stellarframework.commands.StellarHelpCommand;
import com.elguerrero.stellarframework.commands.StellarReloadCommand;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarLangManager;
import com.elguerrero.stellarframework.config.StellarMessages;
import org.bukkit.entity.Player;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public abstract class StellarUtils {

	// METHODS WITH MIXED UTILITIES

	/**
	 * Colorize the message with the clasic color codes replacing the & with § Also replace the plugin prefix
	 * placeholder with the plugin prefix in the messages file used Also replace the plugin prefix debug placeholder
	 * with the plugin prefix debug in the messages file used
	 * <p>
	 * TODO: Add support to the hex color codes
	 * TODO: Add support to minimessages library
	 *
	 * @param message - The message to colorize
	 * @return String - The message colorized
	 */
	public static String colorize(String message) {
		return message.replaceAll("%plugin_prefix%", StellarMessages.getPLUGIN_PREFIX())
				.replaceAll("%plugin_prefix_debug%", StellarMessages.getPLUGIN_PREFIX_DEBUG())
				.replaceAll("&", "§");
	}

	/**
	 * Check if the sender is the console or not
	 *
	 * @param sender - The sender to check, can be console or a player
	 * @return boolean - If the sender is the console
	 */
	public static boolean senderIsConsole(Object sender) {
		return sender instanceof Console;
	}

	/**
	 * Check if the player has the permission to execute the command If the player has the permission, return true If
	 * the player has not the permission, return false and send the no permission message to the player
	 *
	 * @param player                  - The player to check the permission
	 * @param permission              - The permission to check
	 * @param sendNoPermissionMessage - If send the no permission message to the player in case it dont have permission
	 * @return boolean - If the player has the permission
	 */
	public static boolean checkPlayerPermission(Player player, String permission, boolean sendNoPermissionMessage) {

		if (player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + ".*") || player.hasPermission(StellarPluginFramework.getPLUGIN_NAME() + "." + permission)) {
			return true;
		} else if (sendNoPermissionMessage) {
			player.sendMessage(StellarUtils.colorize(StellarMessages.getNO_PERMISSION()));
		}
		return false;
	}

	// METHODS FOR SEND MESSAGES TO THE CONSOLE AND PLAYERS

	/**
	 * Send an info message to the console with gray color Use colorize() method to colorize the message
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendConsoleInfoMessage(String message) {
		StellarPluginFramework.getPLUGIN_LOGGER().info(colorize(message));
	}

	/**
	 * Send a warning message to the console with yellow color Use colorize() method to colorize the message
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendConsoleWarnMessage(String message) {
		StellarPluginFramework.getPLUGIN_LOGGER().warning(colorize(message));
	}

	/**
	 * Send a severe message to the console with red color Use colorize() method to colorize the message
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendConsoleSevereMessage(String message) {
		StellarPluginFramework.getPLUGIN_LOGGER().severe(colorize(message));
	}

	/**
	 * Send a message to the player Use colorize() method to colorize the message
	 *
	 * @param player  - The player to send the message
	 * @param message - The message to send to the player
	 */
	public static void sendMessagePlayer(Player player, String message) {
		player.sendMessage(colorize(message));
	}

	/**
	 * Send a DEBUG message to the console with the DEBUG prefix
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendDebugMessage(String message) {
		if (StellarConfig.getDEBUG()) {
			sendConsoleInfoMessage("&7[&eDEBUG&7] &ei&r" + message);
		}
	}

	/**
	 * Send the status of the debug mode to the console
	 */

	public static void sendMessageDebugStatus() {

		if (StellarConfig.getDEBUG()) {
			StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode is enabled V");
		} else {
			StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode is disabled X");
		}

	}


	// METHODS RELATED TO LOAD PLUGIN CONFIG, MESSAGES AND TO REGISTER THINGS LIKE COMMANDS

	/**
	 * Check if the plugin folder exists, if not, create it
	 *
	 * Exception - If the plugin folder cant be created it can throw a SecurityException or a IOException
	 * (The exception is launched inside the method with a try and catch)
	 */
	public static void checkPluginFolder() {

		try {
			if (!StellarPluginFramework.getINSTANCE().getDataFolder().exists()) {
				StellarPluginFramework.getINSTANCE().getDataFolder().mkdirs();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Load the plugin configs and messages with the lang system
	 */
	public static void loadPluginConfigs() {

		StellarConfig.loadConfigFile();
		StellarConfig.loadConfigVariables();
		StellarLangManager.loadSelectedLangMessages();

	}

	/**
	 * Register the plugin commands calling the methods in their classes for register the commands with CommandAPI
	 */
	public static void registerCommands() {

		StellarHelpCommand.registerInfoCommand();
		StellarDebugCommand.registerDebugCommand();
		StellarReloadCommand.registerReloadCommand();
		StellarDebugReportCommand.registerDebugReportCommand();

	}

	// METHODS RELATED TO THE ERRORS_LOGS

	private static void logException(Exception ex) {

		Date date = new Date();
		String formattedDate = new SimpleDateFormat(StellarConfig.getDATE_FORMAT()).format(date);
		String exceptionStack = Arrays.stream(ex.getStackTrace())
				.map(StackTraceElement::toString)
				.collect(Collectors.joining("\n"));


		try (FileWriter writer = new FileWriter(StellarPluginFramework.getERRORS_LOG(), true);
			 PrintWriter printWriter = new PrintWriter(writer)) {

			printWriter.println("[Error date] " + formattedDate);
			printWriter.println("[Exception type] " + ex);
			printWriter.println("[Exception StackTrace] " + exceptionStack);
			printWriter.println("");
		} catch (IOException exx) {
			exx.printStackTrace();
		}
	}

	/**
	 * Check if the errors.log file exists, if not, create it
	 */
	private static void ErrorsFileExist(){

		final File errorsLogFile = new File(StellarPluginFramework.getPLUGIN_FOLDER(), "errors.log");
		if (!errorsLogFile.exists()) {
			try {
				errorsLogFile.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 1 - Send the general error message to the console when a BIG error ocurred with the plugin
	 * 2 - Check if the errors.log file exists, if not, create it
	 * 3 - Log the exception in the errors.log file
	 * @param ex - The exception to log
	 */
	public static void sendErrorMessageConsole(Exception ex) {

		ErrorsFileExist();
		logException(ex);
		sendConsoleSevereMessage("An error ocurred with the plugin, please check the errors.log file in the plugin folder.");
	}

	// METHODS RELATED TO THE OTHER ERRORS

	/**
	 * Send the error message to the console when a SMALL error ocurred with the plugin
	 * @param message - The message to send to the console
	 */
	public static void sendErrorMessageConsole(String message) {
		sendConsoleSevereMessage(message);
	}

	/**
	 * Method used for manage some errors in the plugin that I cant always manage with errors.log
	 * @param ex
	 */
	public static void sendPluginErrorConsole(Exception ex){
		// Poner mensaje de Plugin error: x como al generar x carpeta
		sendConsoleWarnMessage("---------------------------------------");
        // Poner mensaje con el error del exception con el strack, con severe de color rojo
		sendConsoleWarnMessage("---------------------------------------");
	}

}
