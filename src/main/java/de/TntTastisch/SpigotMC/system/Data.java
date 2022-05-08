package de.TntTastisch.SpigotMC.system;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Data {

    public static File configuration;
    public static YamlConfiguration configurationCFG;

    public static String prefix;
    public static String noPlayer;
    public static String noPerms;
    public static String reloadConfig;
    public static boolean enabled;

    public static String adminPermission;
    public static String byPassPermission;

    public static String version;

    public static String maintenanceLine1;
    public static String maintenanceLine2;
    public static String normalLine1;
    public static String normalLine2;

    public static String alreadyEnabled;
    public static String alreadyDisabled;
    public static String successfullyEnabled;
    public static String successfullyDisabled;

    public static int maxPlayers;
    public static String setMaxPlayers;
    public static String invalidAmount;
    public static String noNegativeAmount;
    public static String maintenanceReason;

    public static String kickMessage;
    public static String maintenanceMessage;
}
