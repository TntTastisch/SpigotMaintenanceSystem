package de.TntTastisch.SpigotMC.listeners;

import de.TntTastisch.SpigotMC.api.Color;
import de.TntTastisch.SpigotMC.system.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class MainListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(Data.enabled){
            if(!(player.hasPermission(Data.byPassPermission) || player.hasPermission(Data.adminPermission))) {
                player.kickPlayer(Color.apply(Data.maintenanceMessage.replace("%prefix%", Data.prefix).replace("%reason%", Data.maintenanceReason)));
            }
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event){
        event.setMaxPlayers(Data.maxPlayers);
        if(Data.enabled){
            event.setMotd(Color.apply(Data.maintenanceLine1.replace("%prefix%", Data.prefix).replace("%reason%", Data.maintenanceReason) + "\n" + Data.maintenanceLine2.replace("%prefix%", Data.prefix).replace("%reason%", Data.maintenanceReason)));
        } else {
            event.setMotd(Color.apply(Data.normalLine1.replace("%prefix%", Data.prefix) + "\n" + Data.normalLine2.replace("%prefix%", Data.prefix)));
        }
    }
}
