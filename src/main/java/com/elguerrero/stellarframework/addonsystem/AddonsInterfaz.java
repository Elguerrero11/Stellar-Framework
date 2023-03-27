package com.elguerrero.stellarframework.addonsystem;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;

public interface AddonsInterfaz {

	void addonLoad();
	void addonConfigReload();
	Set<Listener> getEventListeners();
	Set<String> getCommandNames();

	void setAddonMinigameName(String minigameName);

}
