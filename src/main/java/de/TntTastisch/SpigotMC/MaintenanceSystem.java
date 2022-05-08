package de.TntTastisch.SpigotMC;

import de.TntTastisch.SpigotMC.api.ProtocolImplementation;
import de.TntTastisch.SpigotMC.commands.MaintenanceCMD;
import de.TntTastisch.SpigotMC.listeners.MainListener;
import de.TntTastisch.SpigotMC.system.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class MaintenanceSystem extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        createSystemFiles();
        loadMessages();
        implementAPIs();

        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        this.getCommand("maintenance").setExecutor(new MaintenanceCMD());
    }

    @Override
    public void onDisable() {
    }

    public void implementAPIs() {
        ProtocolImplementation pli = new ProtocolImplementation(this);
        pli.setupIntegration();
    }

    public void loadMessages() {
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
    }

    public void createSystemFiles() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        Data.configuration = new File(getDataFolder().getPath(), "config.yml");
        Data.configurationCFG = YamlConfiguration.loadConfiguration(Data.configuration);

        if (!Data.configuration.exists()) {
            try {
                Data.configuration.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Data.configurationCFG.contains("Maintenance.enabled")) {
            Data.configurationCFG.set("Maintenance.enabled", false);
        }

        if (!Data.configurationCFG.contains("Maintenance.maxPlayers")) {
            Data.configurationCFG.set("Maintenance.maxPlayers", 20);
        }

        if (!Data.configurationCFG.contains("Maintenance.reason")) {
            Data.configurationCFG.set("Maintenance.reason", "Construction");
        }

        if (!Data.configurationCFG.contains("Maintenance.bypassPermission")) {
            Data.configurationCFG.set("Maintenance.bypassPermission", "maintenance.bypass");
        }

        if (!Data.configurationCFG.contains("Maintenance.adminPermission")) {
            Data.configurationCFG.set("Maintenance.adminPermission", "maintenance.admin");
        }

        if (!Data.configurationCFG.contains("Maintenance.version")) {
            Data.configurationCFG.set("Maintenance.version", "&4&lCurrently Maintenance");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.general.prefix")) {
            Data.configurationCFG.set("Maintenance.Messages.general.prefix", "&8[&4Maintenance&8] &7");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.general.noPerms")) {
            Data.configurationCFG.set("Maintenance.Messages.general.noPerms", "%prefix% &cYou do not have enough permission to execute this command!");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.general.noPlayer")) {
            Data.configurationCFG.set("Maintenance.Messages.general.noPlayer", "%prefix% &cYou must a player to perform this command!");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.general.reloadConfig")) {
            Data.configurationCFG.set("Maintenance.Messages.general.reloadConfig", "%prefix% &7You &asuccessfully &7reloaded the &6Configuration&7.");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.kick.now")) {
            Data.configurationCFG.set("Maintenance.Messages.kick.now", "&4ServerNetwork.com\n&7This network is now in &cmaintenance mode&7\n&4Reason &c%reason%\n\n&7You can contact us on\n&cour web www.servernetwork.com");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.kick.currently")) {
            Data.configurationCFG.set("Maintenance.Messages.kick.currently", "&4ServerNetwork.com\n&7This network is currently in &cmaintenance mode&7\n&4Reason &c%reason%\n\n&7You can contact us on\n&cour web www.servernetwork.com");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.MOTD.normalLine1")) {
            Data.configurationCFG.set("Maintenance.Messages.MOTD.normalLine1", "&4ServerNetwork.com &7- &5Minecraft Server &f1.8.X");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.MOTD.normalLine2")) {
            Data.configurationCFG.set("Maintenance.Messages.MOTD.normalLine2", "     &cMaintenance-System by TntTastisch");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.MOTD.maintenanceLine1")) {
            Data.configurationCFG.set("Maintenance.Messages.MOTD.maintenanceLine1", "&4ServerNetwork.com &7- &5Minecraft Server &f1.8.X");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.MOTD.maintenanceLine2")) {
            Data.configurationCFG.set("Maintenance.Messages.MOTD.maintenanceLine2", " &7This network is &ccurrently &7in &cmaintenance");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.errors.enabled")) {
            Data.configurationCFG.set("Maintenance.Messages.errors.enabled", "%prefix% &cThe maintenance mode is already enabled!");
        }

        if (!Data.configurationCFG.contains("Maintenance.Messages.maxplayers")) {
            Data.configurationCFG.set("Maintenance.Messages.maxplayers", "%prefix% You &asuccessfully set the &6maximum number of players &7to &c%max%&7.");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.errors.disabled")) {
            Data.configurationCFG.set("Maintenance.Messages.errors.disabled", "%prefix% &cThe maintenance mode is already disabled!");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.errors.noNegativeAmount")) {
            Data.configurationCFG.set("Maintenance.Messages.errors.disabled", "%prefix% &cYou can not set the maximum number of players in the negative numbers!");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.errors.invalidAmount")) {
            Data.configurationCFG.set("Maintenance.Messages.errors.disabled", "%prefix% &cPlease enter a valid amount!");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.success.enabled")) {
            Data.configurationCFG.set("Maintenance.Messages.success.enabled", "%prefix% &7You &asuccessfully &7enabled the &cmaintenance mode &7with reason &6%reason%&7.");
        }

        if(!Data.configurationCFG.contains("Maintenance.Messages.success.disabled")) {
            Data.configurationCFG.set("Maintenance.Messages.success.disabled", "%prefix% &7You &asuccessfully &7disabled the &cmaintenance mode&7.");
        }

        try {
            Data.configurationCFG.save(Data.configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveConfig();
    }
}
