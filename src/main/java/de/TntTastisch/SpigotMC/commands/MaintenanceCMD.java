package de.TntTastisch.SpigotMC.commands;

import de.TntTastisch.SpigotMC.MaintenanceSystem;
import de.TntTastisch.SpigotMC.system.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class MaintenanceCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(Data.adminPermission)){
            if(strings.length == 0){
                commandSender.sendMessage("§8§m--------------§r §4§lMAINTENANCE §8§m--------------§r");
                commandSender.sendMessage("§e/maintenance reload §8- §7Reload the config");
                commandSender.sendMessage("§e/maintenance setMaxPlayers §8- §7Set the maxplayers");
                commandSender.sendMessage("§e/maintenance <Reason> §8- §7Activate the maintenance mode with reason");
                commandSender.sendMessage("§e/maintenance off §8- §7Deactivate the maintenance mode");
                commandSender.sendMessage("§8§m--------------§r §4§lMAINTENANCE §8§m--------------§r");
            } else if(strings.length >= 1) {
                if(strings[0].equalsIgnoreCase("reload")){
                    try {
                        Data.configurationCFG.load(Data.configuration);

                        Data.prefix = Data.configurationCFG.getString("Maintenance.Messages.general.prefix");
                        Data.noPerms = Data.configurationCFG.getString("Maintenance.Messages.general.noPerms");
                        Data.noPlayer = Data.configurationCFG.getString("Maintenance.Messages.general.noPlayer");
                        Data.reloadConfig = Data.configurationCFG.getString("Maintenance.Messages.general.reloadConfig");
                        Data.enabled = Data.configurationCFG.getBoolean("Maintenance.enabled");
                        Data.adminPermission = Data.configurationCFG.getString("Maintenance.adminPermission");
                        Data.byPassPermission = Data.configurationCFG.getString("Maintenance.bypassPermission");
                        Data.maintenanceLine1 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.maintenanceLine1");
                        Data.maintenanceLine2 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.maintenanceLine2");
                        Data.normalLine1 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.normalLine1");
                        Data.normalLine2 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.normalLine2");
                        Data.alreadyEnabled = Data.configurationCFG.getString("Maintenance.Messages.errors.enabled");
                        Data.alreadyDisabled = Data.configurationCFG.getString("Maintenance.Messages.errors.disabled");
                        Data.successfullyEnabled = Data.configurationCFG.getString("Maintenance.Messages.success.enabled");
                        Data.successfullyDisabled = Data.configurationCFG.getString("Maintenance.Messages.success.disabled");
                        Data.maxPlayers = Data.configurationCFG.getInt("Maintenance.maxPlayers");
                        Data.maintenanceReason = Data.configurationCFG.getString("Maintenance.reason");
                        Data.kickMessage = Data.configurationCFG.getString("Maintenance.Messages.kick.now");
                        Data.maintenanceMessage = Data.configurationCFG.getString("Maintenance.Messages.kick.currently");

                        commandSender.sendMessage(Data.reloadConfig.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidConfigurationException e) {
                        e.printStackTrace();
                    }
                } else if(strings[0].equalsIgnoreCase("setMaxPlayers")){
                    try {
                        int maxPlayers = Integer.parseInt(strings[1]);
                        if(maxPlayers > 0) {
                            Data.maxPlayers = maxPlayers;
                            Data.configurationCFG.set("Maintenance.maxPlayers", maxPlayers);

                            try {
                                Data.configurationCFG.save(Data.configuration);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            commandSender.sendMessage(Data.prefix.replaceAll("&", "§") + "§7You §asuccessfully set the §6maximum number of players §7to §c" + maxPlayers + ".");
                        } else {
                            commandSender.sendMessage("§cYou cannot set the maximum number of players in the negative numbers!");
                        }
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage("§cPlease enter a valid amount!");
                    }
                } else if(strings[0].equalsIgnoreCase("off")){
                    if(Data.enabled){
                        Data.enabled = false;
                        Data.configurationCFG.set("Maintenance.enabled", false);

                        try {
                            Data.configurationCFG.save(Data.configuration);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        commandSender.sendMessage(Data.successfullyDisabled.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")));
                    } else {
                        commandSender.sendMessage(Data.alreadyDisabled.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")));
                    }
                } else {
                    if(!Data.enabled) {
                        String reason = "";
                        for (int i = 0; i != strings.length; i++) {
                            reason = reason + " " + strings[i];
                        }

                        Data.maintenanceReason = reason;
                        Data.enabled = true;
                        Data.maxPlayers = 0;
                        Data.configurationCFG.set("Maintenance.enabled", true);
                        Data.configurationCFG.set("Maintenance.reason", reason);
                        Data.configurationCFG.set("Maintenance.maxPlayers", 0);

                        try {
                            Data.configurationCFG.save(Data.configuration);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        commandSender.sendMessage(Data.successfullyEnabled.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")).replace("%reason%", reason));

                        for(Player all : Bukkit.getOnlinePlayers()){
                            if(!(all.hasPermission(Data.byPassPermission) || all.hasPermission(Data.adminPermission))) {
                                all.kickPlayer(Data.kickMessage.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")).replace("%reason%", reason));
                            }
                        }
                    } else {
                        commandSender.sendMessage(Data.alreadyEnabled.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")));
                    }
                }
            }
        } else {
            commandSender.sendMessage(Data.noPerms.replaceAll("&", "§").replace("%prefix%", Data.prefix.replaceAll("&", "§")));
        }
        return false;
    }
}
