package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.config.StellarLangManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class StellarDebugReport {

	private static StellarDebugReport instance = null;
	private File debugReportsFolder = null;
	private List<String> ignoreLines = new ArrayList<>();
	private Map<String, String> replaceLines = new HashMap<>();

	private StellarDebugReport() {
	}

	private void setVariables() {

		this.debugReportsFolder = new File(StellarPlugin.getPluginInstance().getPluginFolder(),"Debug reports");

		this.replaceLines.put("Host:", "# The Host: line is omited for security");
		this.replaceLines.put("Port:", "# The Port: line is omited for security");
		this.replaceLines.put("Database_Name:", "# The Database_Name: line is omited for security");
		this.replaceLines.put("Username:", "# The Username: line is omited for security");
		this.replaceLines.put("Password:", "# The Password: line is omited for security");

		this.ignoreLines.add("# Define the address and port for the database.");
		this.ignoreLines.add("# - The standard DB engine port is used by default");
		this.ignoreLines.add("#   (MySQL and MariaDB: 3306)");
		this.ignoreLines.add("# The name of the database to store LuckPerms data in.");
		this.ignoreLines.add("# Credentials for the database.");
	}

	public void generateDebugReport() {

		try {

			final String configPath = "config.yml";

			getInstance().setVariables();

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			final File debugReportFolder = new File(debugReportsFolder, "Debug_" + LocalDateTime.now().format(dateFormatter));

			if (!StellarUtils.pluginFileExist(debugReportsFolder, true)){
				return;
			}
			if (!debugReportFolder.mkdir()){
				return;
			}

			// Copy the config file to the debug report folder
			// Don't copy the lines that contains the strings in the ignoreLines list
			// Replace the lines that contains the strings in the replaceLines map with the value of the map

			final File originalConfigFile = new File(StellarPlugin.getPluginInstance().getPluginFolder().getPath(), configPath);
			final File debugConfigFile = new File(debugReportFolder.getPath(), configPath);
			FileUtils.copyFile(originalConfigFile, debugConfigFile,StandardCopyOption.COPY_ATTRIBUTES);


			final List<String> debugConfigLines = Files.readAllLines(originalConfigFile.toPath(), StandardCharsets.UTF_8).stream()
					.filter(line -> ignoreLines.stream().noneMatch(line::contains))
					.map(line -> replaceLines.getOrDefault(line, line))
					.toList();
			Files.write(debugReportFolder.toPath().resolve(configPath), debugConfigLines, StandardCharsets.UTF_8);


			// Copy the lang folder to the debug report folder with all the lang files

			final File originalLangFolder = new File(StellarLangManager.getLANG_FOLDER().getPath());
			final File debugLangFolder = new File(debugReportFolder.getPath());

			FileUtils.copyDirectory(originalLangFolder, debugLangFolder, true);

			// If exist, copy the errors.log file to the debug report folder

			final File originalErrorsLogFile = new File(StellarPlugin.getPluginInstance().getPluginFolder() + "errors.log");

			if (originalErrorsLogFile.exists()){

			final File debugReportErrorsLogFile = new File(debugReportFolder.getPath());
			FileUtils.copyFile(originalErrorsLogFile, debugReportErrorsLogFile, StandardCopyOption.COPY_ATTRIBUTES);

			}

			// Create the debug.log file with the debug info about the plugin and server

			final File debugLogFile = new File(debugReportFolder.getPath(), "debug.log");

			final boolean pluginEnabled = StellarPlugin.getPluginInstance().getPluginManager().isPluginEnabled(StellarPlugin.getPluginInstance());
			final String platform = Bukkit.getVersion().replace("git-", "").replace("\\(MC: .+\\)", "");


			String pluginsList = Arrays.stream(Bukkit.getPluginManager().getPlugins())
					.map(Plugin::getName)
					.collect(Collectors.joining(", "));


			try (FileWriter writer = new FileWriter(debugLogFile)) {

				writer.write("[Java version] " + System.getProperty("java.version") + "\n");
				writer.write("[Minecraft version] " + Bukkit.getBukkitVersion() + "\n");
				writer.write("[Server platform] " + platform + "\n");
				writer.write("[Plugin status] " + (pluginEnabled ? "Enabled V" : "Disabled X") + "\n");
				writer.write("[Version of " + StellarPlugin.getPluginInstance().getPluginName() + "] " + StellarPlugin.getPluginInstance().getPluginVersion() + "\n");
				writer.write("[Plugins] " + pluginsList + "\n");

			}


			// Create the debug.zip file with all the debug report files
			final File normalDebugFolder = new File(debugReportFolder.getPath());
			final File debugZippedFolder = new File(normalDebugFolder, normalDebugFolder.getName());


			// Create the ZIP file
			try (FileOutputStream fos = new FileOutputStream(debugZippedFolder)) {
				final ZipOutputStream zos = new ZipOutputStream(fos);

				// Add the original debug report folder to the debug report zip file
				for (File file : FileUtils.listFiles(normalDebugFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
					String relativePath = normalDebugFolder.toURI().relativize(file.toURI()).getPath();
					ZipEntry zipEntry = new ZipEntry(relativePath);
					zos.putNextEntry(zipEntry);
					zos.write(IOUtils.toByteArray(file.toURI()));
					zos.closeEntry();
				}

				// Close the zip objects
				zos.close();
			}

			// Remove the old debug report folder that is not zipped
			FileUtils.deleteDirectory(debugReportFolder);

			// Send to the console the message of that the debug report was generated with success in the debug report folder
			StellarUtils.sendConsoleInfoMessage("&aDebug report generated with success in: " + debugReportsFolder);

		} catch (Exception ex) {
			StellarUtils.sendPluginErrorConsole(ex);
		}
	}

	public static StellarDebugReport getInstance() {
		if (instance == null) {
			instance = new StellarDebugReport();
		}
		return instance;
	}

}
