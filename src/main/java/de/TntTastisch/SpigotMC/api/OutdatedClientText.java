package de.TntTastisch.SpigotMC.api;

import com.comphenix.protocol.wrappers.WrappedServerPing;
import de.TntTastisch.SpigotMC.system.Data;

public class OutdatedClientText {

    private OutdatedClientText() {
        throw new IllegalStateException("Utility class");
    }

    public static void activate(WrappedServerPing ping) {
        ping.setVersionName(Color.apply(Data.version));
    }
}
