package de.TntTastisch.SpigotMC.commands;

import de.TntTastisch.SpigotMC.MaintenanceSystem;
import de.TntTastisch.SpigotMC.api.Color;
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
                commandSender.sendMessage(Color.apply("&8&m--------------&r &4&lMAINTENANCE &8&m--------------&r"));
                commandSender.sendMessage(Color.apply("&e/maintenance reload &8- &7Reload the config"));
                commandSender.sendMessage(Color.apply("&e/maintenance setMaxPlayers &8- &7Set the maxplayers"));
                commandSender.sendMessage(Color.apply("&e/maintenance <Reason> &8- &7Activate the maintenance mode with reason"));
                commandSender.sendMessage(Color.apply("&e/maintenance off &8- &7Deactivate the maintenance mode"));
                commandSender.sendMessage(Color.apply("&8&m--------------&r &4&lMAINTENANCE &8&m--------------&r"));
            } else if(strings.length >= 1) {
                if(strings[0].equalsIgnoreCase("reload")){
                    try {
                        Data.configurationCFG.load(Data.configuration);

                        Data.prefix = Data.configurationCFG.getString("Maintenance.Messages.general.prefix");
                        Data.noPerms = Data.configurationCFG.getString("Maintenance.Messages.general.noPerms");
                        Data.noPlayer = Data.configurationCFG.getString("Maintenance.Messages.general.noPlayer");
                        Data.version = Data.configurationCFG.getString("Maintenance.version");
                        Data.reloadConfig = Data.configurationCFG.getString("Maintenance.Messages.general.reloadConfig");
                        Data.enabled = Data.configurationCFG.getBoolean("Maintenance.enabled");
                        Data.adminPermission = Data.configurationCFG.getString("Maintenance.adminPermission");
                        Data.byPassPermission = Data.configurationCFG.getString("Maintenance.bypassPermission");
                        Data.setMaxPlayers = Data.configurationCFG.getString("Maintenance.Messages.maxplayers");
                        Data.maintenanceLine1 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.maintenanceLine1");
                        Data.maintenanceLine2 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.maintenanceLine2");
                        Data.normalLine1 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.normalLine1");
                        Data.normalLine2 = Data.configurationCFG.getString("Maintenance.Messages.MOTD.normalLine2");
                        Data.alreadyEnabled = Data.configurationCFG.getString("Maintenance.Messages.errors.enabled");
                        Data.alreadyDisabled = Data.configurationCFG.getString("Maintenance.Messages.errors.disabled");
                        Data.invalidAmount = Data.configurationCFG.getString("Maintenance.Messages.errors.invalidAmount");
                        Data.noNegativeAmount = Data.configurationCFG.getString("Maintenance.Messages.errors.noNegativeAmount");
                        Data.successfullyEnabled = Data.configurationCFG.getString("Maintenance.Messages.success.enabled");
                        Data.successfullyDisabled = Data.configurationCFG.getString("Maintenance.Messages.success.disabled");
                        Data.maxPlayers = Data.configurationCFG.getInt("Maintenance.maxPlayers");
                        Data.maintenanceReason = Data.configurationCFG.getString("Maintenance.reason");
                        Data.kickMessage = Data.configurationCFG.getString("Maintenance.Messages.kick.now");
                        Data.maintenanceMessage = Data.configurationCFG.getString("Maintenance.Messages.kick.currently");

                        commandSender.sendMessage(Color.apply(Data.reloadConfig.replace("%prefix%", Data.prefix)));
                    } catch (IOException | InvalidConfigurationException e) {
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
                            commandSender.sendMessage(Color.apply(Data.prefix + Data.setMaxPlayers.replaceAll("%max%", String.valueOf(maxPlayers))));
                        } else {
                            commandSender.sendMessage(Color.apply(Data.noNegativeAmount.replace("%prefix%", Data.prefix)));
                        }
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Color.apply(Data.invalidAmount.replace("%prefix%", Data.prefix)));
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

                        commandSender.sendMessage(Color.apply(Data.successfullyDisabled.replace("%prefix%", Data.prefix)));
                    } else {
                        commandSender.sendMessage(Color.apply(Data.alreadyDisabled.replace("%prefix%", Data.prefix)));
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

                        commandSender.sendMessage(Color.apply(Data.successfullyEnabled.replace("%prefix%", Data.prefix).replace("%reason%", reason)));

                        for(Player all : Bukkit.getOnlinePlayers()){
                            if(!(all.hasPermission(Data.byPassPermission) || all.hasPermission(Data.adminPermission))) {
                                all.kickPlayer(Color.apply(Data.kickMessage.replace("%prefix%", Data.prefix).replace("%reason%", reason)));
                            }
                        }
                    } else {
                        commandSender.sendMessage(Color.apply(Data.alreadyEnabled.replace("%prefix%", Data.prefix)));
                    }
                }
            }
        } else {
            commandSender.sendMessage(Color.apply(Data.noPerms.replace("%prefix%", Data.prefix)));
        }
        return false;
    }
}
