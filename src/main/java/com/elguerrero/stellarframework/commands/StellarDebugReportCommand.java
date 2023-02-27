package com.elguerrero.stellarframework.commands;

import com.elguerrero.stellarframework.utils.StellarDebugReport;
import com.elguerrero.stellarframework.utils.StellarUtils;
import dev.jorel.commandapi.CommandAPICommand;


public abstract class StellarDebugReportCommand {

	public static void registerDebugReportCommand() {
		new CommandAPICommand("--debugreport")
				.withRequirement(StellarUtils::senderIsConsole)
				.executesConsole((sender, args) -> {

					StellarDebugReport.generateDebugReport();

				})
				.register();

	}
}
