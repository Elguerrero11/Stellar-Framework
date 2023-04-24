package com.elguerrero.stellarframework;

import com.elguerrero.stellarframework.addonsystem.AddonsManager;
import com.elguerrero.stellarframework.config.StellarConfig;
import com.elguerrero.stellarframework.config.StellarMessages;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class StellarPlugin extends JavaPlugin{

	@Setter(AccessLevel.PROTECTED)
	private static StellarPlugin pluginInstance = null;

	@Getter
	private PluginManager bukkitPluginsManager = null;
	@Getter
	private Logger consoleLogger = null;
	@Getter
	private File pluginFolder = null;
	@Getter
	private File addonsFolder = null;
	@Getter
	private File errorsLog = null;

	@Setter(AccessLevel.PROTECTED)
	private StellarConfig configInstance = null;
	@Setter(AccessLevel.PROTECTED)
	private StellarMessages messagesInstance = null;

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private String pluginName = "";
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private String pluginFormat = "";
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private String pluginDescription = "";
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private String pluginVersion = "";
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private String pluginAuthor = "";

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private int helpCommandPages = 2;
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private boolean addonsEnabled = false;

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private boolean pluginIsAStellarMinigame = false;

	@Getter
	@Setter(AccessLevel.PROTECTED)
	private boolean debugReportEnabled = true;

	private StellarPlugin() {
	}

	@Override
	public void onLoad() {
		try {

			if (pluginInstance == null){
				setPluginInstance(this);
			}


			if (!StellarUtils.filePluginExist(pluginFolder, true)){
				return;
			}

			setVariables();
			StellarUtils.loadPluginConfigs();
			StellarUtils.sendDebugMessage("The instance of the stellar framework is the plugin:" + pluginInstance.getName() + " , who have the main class:" + pluginInstance.getClass().getName());

			if (addonsEnabled) {
				addonsFolder = new File(getPluginInstance().getDataFolder().getPath() + "Addons");
			}

			StellarUtils.sendMessageDebugStatus();
			CommandAPI.onLoad(new CommandAPIConfig().silentLogs(StellarConfig.getDebug()).verboseOutput(StellarConfig.getDebug()));

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}


	@Override
	public void onEnable() {
		try {

			CommandAPI.onEnable(pluginInstance);
			StellarUtils.registerCommands();

			if (addonsEnabled){
				AddonsManager.getInstance().loadAllAddons();
			}

			consoleSendPluginEnableMessage();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}
	}

	@Override
	public void onDisable() {

		try {

			CommandAPI.onDisable();
			consoleSendPluginDisableMessage();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}

	private void setVariables() {

		bukkitPluginsManager = Bukkit.getPluginManager();
		consoleLogger = LoggerFactory.getLogger("Logger");
		pluginFolder = pluginInstance.getDataFolder();
		errorsLog = new File(pluginFolder, "errors.md");
		pluginName = pluginInstance.getName();
		pluginDescription = pluginInstance.getDescription().getDescription();
		pluginVersion = pluginInstance.getDescription().getVersion();
		pluginAuthor = pluginInstance.getDescription().getAuthors().toString();

		setVariablesAbstract();

	}

	protected abstract void setVariablesAbstract();
	protected abstract void consoleSendPluginEnableMessage();
	protected abstract void consoleSendPluginDisableMessage();

	public static StellarPlugin getPluginInstance(){

		if (pluginInstance == null) {
			throw new IllegalStateException("The instance of the plugin is not initialized yet, please contact the developer of the plugin.");
		}
		return pluginInstance;
	}

	public static StellarConfig getBasicConfigInstance(){
		return pluginInstance.configInstance;
	}

	public static StellarMessages getBasicMessagesInstance(){
		return pluginInstance.messagesInstance;
	}

}
