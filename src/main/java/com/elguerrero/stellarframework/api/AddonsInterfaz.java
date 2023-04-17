package com.elguerrero.stellarframework.api;

import org.bukkit.event.Listener;
import java.util.Set;

public interface AddonsInterfaz {

	void addonLoad();
	void addonConfigReload();
	Set<Listener> getEventListeners();
	Set<String> getCommandNames();

	void setAddonMinigameName(String minigameName);

}
