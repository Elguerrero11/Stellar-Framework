package es.elguerrero.stellarframework.commands;

import es.elguerrero.stellarframework.config.ConfigImplement;
import es.elguerrero.stellarframework.config.StellarPluginConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {


		if (cmd.getName().equalsIgnoreCase("plugint43 reload")) {

			if (!(sender instanceof Player)) {
				Player player = (Player) sender;

				if (player.hasPermission("stellarplugin.reload")) {
					ConfigImplement.getInstance().reload();
					player.sendMessage("Configuracion recargada");
				} else {
					player.sendMessage("No tienes permisos para ejecutar este comando");
				}

			} else {
				sender.sendMessage("Solo los jugadores pueden ejecutar este comando");
			}


		}

		StellarPluginConfig.get
		player.teleport(player.getWorld().getSpawnLocation());
		player.sendMessage("Te has teleportado a la base");

		return true;
	}


}
