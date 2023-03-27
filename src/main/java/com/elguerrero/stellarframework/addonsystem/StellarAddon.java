package com.elguerrero.stellarframework.addonsystem;

import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class StellarAddon{

	@Getter
	private String addonName = "";
	@Getter
	private String addonVersion = "";
	@Getter
	private String mainClass = "";
	@Getter
	private String plugin = "";

	@Getter
	private YamlDocument addonConfig = null;

	private List<String> addonPluginsDependencies = new ArrayList<>();
	private List<String> addonAuthors = new ArrayList<>();
	@Getter
	private Set<Listener> eventsListeners = null;
    @Getter
	private Set<String> commandsNames = null;


	private boolean isStellarMinigameFrameworkAddon = false;

	protected StellarAddon(String addonName, String addonVersion, String mainClass, String plugin, List<String> addonPluginsDependencies, List<String> addonAuthors, Set<Listener> eventsListeners, Set<String> commandsNames) {

		this.addonName = addonName;
		this.addonVersion = addonVersion;
		this.mainClass = mainClass;
		this.plugin = plugin;
		this.addonPluginsDependencies = addonPluginsDependencies;
		this.addonAuthors = addonAuthors;
		this.eventsListeners = eventsListeners;
		this.commandsNames = commandsNames;

		AddonsManager.getInstance().registerAddon(this);

	}

	// Methods for get variables List copies


	public List<String> getAddonAuthors() {

		return Collections.unmodifiableList(addonAuthors);

	}

	public List<String> getAddonPluginsDependencies() {

		return Collections.unmodifiableList(addonPluginsDependencies);

	}


}
