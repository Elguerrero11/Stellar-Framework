package com.elguerrero.stellarframework.utils;

import com.elguerrero.stellarframework.StellarPlugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class StellarDebugReport {

	private static StellarDebugReport instance = null;
	private final File debugReportsFolder = new File(StellarPlugin.getPluginInstance().getPluginFolder(),"DebugReports");
	private List<String> ignoreLines = new ArrayList<>();
	private Map<String, String> replaceLines = new HashMap<>();
	private List<Path> filesToCopy = new ArrayList<>();

	private StellarDebugReport() {
	}

	// TODO: Change the way of ignore and reeplace lines to that check if start with the keys that will allways be like  Host: Is a more secure method for check
	private void setVariables() {

		final Path pluginFolder = StellarPlugin.getPluginInstance().getPluginFolder().toPath();
		StellarUtils.filePluginExist(debugReportsFolder, true);

		replaceLines.put("Host:", "# The Host: line is omited for security");
		replaceLines.put("Port:", "# The Port: line is omited for security");
		replaceLines.put("Database_Name:", "# The Database_Name: line is omited for security");
		replaceLines.put("Username:", "# The Username: line is omited for security");
		replaceLines.put("Password:", "# The Password: line is omited for security");

		ignoreLines.add("# Define the address and port for the database.");
		ignoreLines.add("# - The standard DB engine port is used by default");
		ignoreLines.add("#   (MySQL and MariaDB: 3306)");
		ignoreLines.add("# The name of the database to store LuckPerms data in.");
		ignoreLines.add("# Credentials for the database.");

		filesToCopy.add(pluginFolder.resolve("Lang"));
		filesToCopy.add(pluginFolder.resolve("errors.md"));

	}

	public void generateDebugReport() {

		try {

			final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			final File debugReportFolder = new File(debugReportsFolder, "DebugReport_" + LocalDateTime.now().format(dateFormatter));
			final Path debugReportFolderPath = debugReportFolder.toPath();


			if (!StellarUtils.filePluginExist(debugReportsFolder, true)){
				return;
			}

			if (!StellarUtils.filePluginExist(debugReportFolder, true)){
				return;
			}

			getInstance().setVariables();

			createDebugReportLog(debugReportFolder);
			copyConfigFile(debugReportFolderPath);
			copyFilesToCopy(debugReportFolderPath);

			zipDebugReportFolder(debugReportFolder);

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

	private void copyConfigFile(Path debugReportFolderPath) {

		try {

			final String configPath = "config.yml";
			final Path pluginFolderPath = StellarPlugin.getPluginInstance().getPluginFolder().toPath();
			final Path originalConfigPath = pluginFolderPath.resolve(configPath);
			final Path debugConfigPath = debugReportFolderPath.resolve(configPath);

			Files.copy(originalConfigPath, debugConfigPath, StandardCopyOption.COPY_ATTRIBUTES);
			StellarUtils.sendDebugMessage("Config copied to: " + debugConfigPath);

			final List<String> debugConfigLines = Files.readAllLines(debugConfigPath, StandardCharsets.UTF_8).stream()
					.filter(line -> ignoreLines.stream().noneMatch(line::contains))
					.map(line -> replaceLines.getOrDefault(line, line))
					.toList();

			Files.write(debugReportFolderPath.resolve(configPath), debugConfigLines, StandardCharsets.UTF_8);

		} catch(Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	private void createDebugReportLog(File debugReportFolder){

		try {

			final File debugLogFile = new File(debugReportFolder, "debugReportLog.md");
			final String platform = Bukkit.getVersion().replace("git-", "").replace("\\(MC: .+\\)", "");
			final String pluginsList = Arrays.stream(Bukkit.getPluginManager().getPlugins())
					.map(Plugin::getName)
					.collect(Collectors.joining(", "));

			StellarUtils.filePluginExist(debugLogFile, true);

			StellarUtils.writeToFile(debugLogFile, "[Java version] " + System.getProperty("java.version") + "\n");
			StellarUtils.writeToFile(debugLogFile, "[Minecraft version] " + Bukkit.getBukkitVersion() + "\n");
			StellarUtils.writeToFile(debugLogFile, "[Server platform] " + platform + "\n");
			StellarUtils.writeToFile(debugLogFile, "[Version of " + StellarPlugin.getPluginInstance().getPluginName() + "] " + StellarPlugin.getPluginInstance().getPluginVersion() + "\n");
			StellarUtils.writeToFile(debugLogFile, "[Plugins] " + pluginsList + "\n");

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}
	private void copyFilesToCopy(Path debugReportFolderPath) {

		try {

			for (Path file : filesToCopy) {

				Files.copy(file, debugReportFolderPath, StandardCopyOption.COPY_ATTRIBUTES);
				StellarUtils.sendDebugMessage("File copied: " + file + " to " + debugReportFolderPath);

			}

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	private void zipDebugReportFolder(File debugReportFolder) {

		final File parentFolder = debugReportFolder.getParentFile();
		final File debugZippedFolder = new File(parentFolder, debugReportFolder.getName() + ".zip");

		try (FileOutputStream fos = new FileOutputStream(debugZippedFolder);
			 ZipOutputStream zos = new ZipOutputStream(fos)) {

			for (File file : FileUtils.listFiles(debugReportFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
				String relativePath = debugReportFolder.toURI().relativize(file.toURI()).getPath();
				ZipEntry zipEntry = new ZipEntry(relativePath);
				zos.putNextEntry(zipEntry);
				zos.write(IOUtils.toByteArray(file.toURI()));
				zos.closeEntry();
			}

			FileUtils.deleteDirectory(debugReportFolder);

			StellarUtils.sendConsoleInfoMessage("&aDebug report generated with success in: " + debugReportsFolder);

		} catch (IOException ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}


	public void addFileToCopy(Path path) {
		filesToCopy.add(path);
	}

	public static StellarDebugReport getInstance() {
		if (instance == null) {
			instance = new StellarDebugReport();
		}
		return instance;
	}

}
