package de.TntTastisch.SpigotMC.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import de.TntTastisch.SpigotMC.MaintenanceSystem;
import de.TntTastisch.SpigotMC.system.Data;
import org.bukkit.Bukkit;

public class ProtocolImplementation {

    public static MaintenanceSystem maintenanceSystem;

    public ProtocolImplementation(MaintenanceSystem plugin) {
        setPlugin(plugin);
    }

    private static void setPlugin(MaintenanceSystem plugin) {
        maintenanceSystem = plugin;
    }

    public void setupIntegration() {
        Bukkit.getLogger().info("[MaintenanceSystem] Implementing Protocol Library.");
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(PacketAdapter.params(maintenanceSystem, PacketType.Status.Server.SERVER_INFO).optionAsync()) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        WrappedServerPing ping = event.getPacket().getServerPings().read(0);

                        if(Data.enabled) {
                            OutdatedClientText.activate(ping);
                            ping.setVersionProtocol(-1);
                        }
                    }
                });
    }
}
