package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.commands.StDebugCmd;
import com.elguerrero.stellarframework.commands.StDebugReportCmd;
import com.elguerrero.stellarframework.commands.StHelpCmd;
import com.elguerrero.stellarframework.commands.StReloadCmd;
import com.elguerrero.stellarframework.commands.addonscommands.*;
import com.elguerrero.stellarframework.config.StellarConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class StellarUtils {

	private StellarUtils() {
	}

	// METHODS WITH MIXED UTILITIES

	/**
	 * Colorize the message with the clasic color codes replacing the & with § Also replace the plugin prefix
	 * placeholder with the plugin prefix in the messages file used Also replace the plugin prefix debug placeholder
	 * with the plugin prefix debug in the messages file used
	 * <p>
	 * TODO: Add support to the hex(rgb) color codes
	 * TODO: Add support to minimessages library
	 * TODO: Add placeholderapi support
	 * TODO: Maybe add regex patterns support
	 *
	 * @param message - The message to colorize
	 * @return String - The message colorized
	 */
	public static String colorize(String message) {

		return message.replace("%plugin_prefix%", StellarPlugin.getBasicMessagesInstance().getPluginPrefix())
				.replaceAll("%plugin_prefix_debug%", StellarPlugin.getBasicMessagesInstance().getPluginDebugPrefix())
				.replaceAll("&", "§");
	}

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

		if (player.hasPermission(StellarPlugin.getPluginInstance().getPluginName() + ".*") || player.hasPermission(StellarPlugin.getPluginInstance().getPluginName() + "." + permission)) {
			return true;
		} else if (sendNoPermissionMessage) {
			player.sendMessage(StellarUtils.colorize(StellarPlugin.getBasicMessagesInstance().getNoPermission()));
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
		StellarPlugin.getPluginInstance().getConsoleLogger().info(colorize(message)); // NOSONAR
	}

	/**
	 * Send a warning message to the console with yellow color Use colorize() method to colorize the message
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendConsoleWarnMessage(String message) {
		StellarPlugin.getPluginInstance().getConsoleLogger().warn(colorize(message)); // NOSONAR
	}

	/**
	 * Send a severe message to the console with red color Use colorize() method to colorize the message
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendConsoleErrorMessage(String message) {
		StellarPlugin.getPluginInstance().getConsoleLogger().error(colorize(message)); // NOSONAR
	}

	/**
	 * Send a message to the player Use colorize() method to colorize the message
	 *
	 * @param player  - The player to send the message
	 * @param message - The message to send to the player
	 */
	public static void sendMessagePlayer(Player player, String message) {
		if (message != null) {
			player.sendMessage(colorize(message));
		}
	}

	/**
	 * Send a DEBUG message to the console with the DEBUG prefix
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendDebugMessage(String message) {
		if (StellarConfig.getDebug()) { // NOSONAR
		StellarPlugin.getPluginInstance().getConsoleLogger().debug(colorize(message)); //  NOSONAR
		logDebugMessage(message);
		}
	}

	public static void sendMessageDebugStatus() {

		if (StellarConfig.getDebug()) { // NOSONAR
			StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode is enabled V");
		} else {
			StellarUtils.sendConsoleInfoMessage("&ei &7Debug mode is disabled X");
		}

	}


	// METHODS RELATED TO LOAD PLUGIN CONFIG, MESSAGES AND TO REGISTER THINGS LIKE COMMANDS


	/**
	 * Check if a file or folder exists, if not, create it
	 *
	 * @param file     - The file or folder to check
	 * @param isFolder - If the file is a folder or not
	 */
	public static boolean filePluginExist(File file, boolean isFolder) {

		try {
			if (!file.exists()) {

				boolean result;

				if (isFolder) {
					sendDebugMessage("Creating folder named: " + file.getName() + " in path: " + file.getParent());
					result = file.mkdir();
					sendDebugMessage("Folder created: " + result);
				} else {
					sendDebugMessage("Creating file named: " + file.getName() + " in path: " + file.getParent());
					result = file.createNewFile();
					sendDebugMessage("File created: " + result);
				}
				return result;

			} else {
				sendDebugMessage("File or folder named: " + file.getName() + " already exists in path: " + file.getParent());
				return true;
			}

		} catch (Exception ex) {
			logErrorException(ex, "default");
			return false;
		}

	}

	/**
	 * Load the plugin configs and messages with the lang system
	 */
	public static void loadPluginConfigs() {

		if (StellarPlugin.getBasicConfigInstance() != null){
			StellarConfig.setGeneralVariables();
			StellarPlugin.getBasicConfigInstance().loadConfigFile();
		}

		if (StellarPlugin.getBasicMessagesInstance() != null){
			StellarPlugin.getBasicMessagesInstance().loadMessagesFile();
		}

	}

	public static void registerCommands() {

		StHelpCmd.registerInfoCommand();
		StDebugCmd.registerDebugCommand();
		StReloadCmd.registerReloadCommand();

		if (StellarPlugin.getPluginInstance().isDebugReportEnabled()) {
			StDebugReportCmd.registerDebugReportCommand();
		}

		if (StellarPlugin.getPluginInstance().isAddonsEnabled()){

			StDisableAddonCmd.registerDisableAddonCmd();
			StEnableAddonCmd.registerEnableAddonCmd();
			StReloadAddonCmd.registerReloadAddonCmd();
			StListDisabledAddonsCmd.registerListDisabledAddonsCmd();
			StListEnabledAddonsCmd.registerListEnabledAddonsCommand();
		}

	}

	// METHODS RELATED TO THE ERRORS_LOGS

	/**
	 * Log an exception in the errors.log file
	 * Check if the errors.log file exists, if not, create it
	 * If there is an error while logging the exception, log it in the console
	 * Both, the error exception and the argument exception of the method
	 * Too send a message to the console saying that an error ocurred with the plugin
	 *
	 * @param ex - The error exception to log
	 */
	public static void logErrorException(Exception ex, String consoleErrorMsg) {

		if (mustSendConsoleErrorMsg()){
			sendPluginErrorConsole(ex);
			return;
		}

		final File errorsLog = StellarPlugin.getPluginInstance().getErrorsLog();

		if (!filePluginExist(errorsLog, false)) {
			sendPluginErrorConsole(ex);
			return;
		}

		String formattedDate = getFormatDate();
		String exceptionStack = getExceptionStackTrace(ex);

		writeToErrorsLog(errorsLog, formattedDate, ex, exceptionStack);
		sendConsoleErrorMsg(consoleErrorMsg);

	}

	private static boolean mustSendConsoleErrorMsg(){
		return StellarPlugin.getPluginInstance() == null || StellarPlugin.getPluginInstance().getPluginFolder() == null;
	}

	private static String getFormatDate() {
		Date date = new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	private static String getExceptionStackTrace(Exception ex) {
		return Arrays.stream(ex.getStackTrace())
				.map(StackTraceElement::toString)
				.collect(Collectors.joining("\n"));
	}

	private static void writeToErrorsLog(File errorsLog, String formattedDate, Exception ex, String exceptionStack) {

		try {

			try (FileWriter writer = new FileWriter(errorsLog, true);
				 PrintWriter printWriter = new PrintWriter(writer)) {

				printWriter.println("### <span style='color:#046E70'>Error date</span>");
				printWriter.println("**<span style='color:#1D7C7E'>" + formattedDate + "</span>**");
				printWriter.println("### <span style='color:#F1C232'>Exception type</span>");
				printWriter.println("**<span style='color:#F1C232'>" + ex + "</span>**");
				printWriter.println("### <span style='color:#F22424'>Exception StackTrace</span>");
				printWriter.println("```");
				printWriter.println(exceptionStack);
				printWriter.println("```");
				printWriter.println("# ");
			}

		} catch (Exception e) {
			sendPluginErrorConsole(e);
		}

	}

	private static void sendConsoleErrorMsg(String consoleErrorMsg){

		final String defaultConsoleMessage = "An error ocurred with the plugin, please check the errors.log file in the plugin folder.";

		if ("default".equals(consoleErrorMsg)) {
			sendConsoleErrorMessage(defaultConsoleMessage);
		} else {
			sendConsoleErrorMessage(consoleErrorMsg);
		}
	}



	// METHODS RELATED TO THE OTHER ERRORS

	/**
	 * Send the error message to the console when a SMALL error ocurred with the plugin
	 *
	 * @param message - The message to send to the console
	 */
	public static void sendErrorMessageConsole(String message) {
		sendConsoleErrorMessage(message);
	}

	/**
	 * Method used for manage some errors in the plugin that I cant always manage with errors.log
	 *
	 * @param ex - The exception to send to the console
	 */
	private static void sendPluginErrorConsole(Exception ex) {
		sendConsoleWarnMessage("---------------------------------------");
		sendConsoleWarnMessage("          " + StellarPlugin.getPluginInstance().getPluginName());
		sendConsoleWarnMessage("              Error ocurred:");

		Arrays.stream(ex.getStackTrace())
				.map(StackTraceElement::toString)
				.forEach(StellarUtils::sendConsoleWarnMessage);

		sendConsoleWarnMessage("---------------------------------------");
	}



	// OTHER METHODS

	/**
	 * Check if a plugin is enabled
	 *
	 * @return - If the plugin is enabled or not
	 */
	public static boolean isPluginEnabled(String pluginName) {
		Plugin plugin = StellarPlugin.getPluginInstance().getBukkitPluginsManager().getPlugin(pluginName);
		return plugin != null && plugin.isEnabled();
	}

	/**
	 * Disable the plugin
	 */
	public static void disableThisPlugin() {
		StellarPlugin.getPluginInstance().getBukkitPluginsManager().disablePlugin(StellarPlugin.getPluginInstance());
	}

	public static void writeToFile(File file, String content) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(content);
		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}


	private static void logDebugMessage(String message) {

		try {

			final File debugLogsFolder = new File(StellarPlugin.getPluginInstance().getPluginFolder(), "DebugLogs");

			filePluginExist(debugLogsFolder, true);

			final File todayDebugLog = checkDebugLogExist(debugLogsFolder);

			writeToFile(todayDebugLog, "<span style='color:#046E70'> [DEBUG] </span>" + "<span style='color:#07B7BA'>" + message + "</span>");

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}



	private static File checkDebugLogExist(File folderPath) {

		final String currentDate = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		final File debugLog = new File(folderPath, "debugLog-" + currentDate + ".md");

		if (!debugLog.exists()) {
			filePluginExist(debugLog, false);
		}
		return debugLog;

	}




	// PLAYERS UTILS

	// TODO: Add a method to send a actionbar message to the player

}
