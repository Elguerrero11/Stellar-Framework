package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.StellarPluginFramework;
import com.elguerrero.stellarframework.utils.DebugReport;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;


public abstract class StellarDebugReportCommand {

	public static void registerDebugReportCommand() {
		new CommandAPICommand(StellarPluginFramework.getPLUGIN_NAME() + "-debugreport")
				.withRequirement(StellarUtils::senderIsConsole)
				.executesConsole((sender, args) -> {

					DebugReport.generateDebugReport();

				})
				.register();

	}
}
