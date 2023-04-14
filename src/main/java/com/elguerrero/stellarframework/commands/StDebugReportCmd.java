package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPlugin;
import com.elguerrero.stellarframework.utils.StellarDebugReport;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;


public class StDebugReportCmd {

	private StDebugReportCmd() {
	}

	public static void registerDebugReportCommand() {

		try {
			new CommandAPICommand(StellarPlugin.getPluginInstance().getPluginName() + "-debugreport")
					.withRequirement(StellarUtils::senderIsConsole)
					.executesConsole((sender, args) -> {

						StellarDebugReport.getInstance().generateDebugReport();

					})
					.register();

		} catch (Exception ex) {
			StellarUtils.logErrorException(ex, "default");
		}

	}
}
