package de.axiomlab.oceanclient.client.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class NameVisibility {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                client.player.setCustomNameVisible(true);
                client.player.setGlowing(true);
                client.player.setCustomName(Text.literal("❤ ").append(client.player.getName()));
            }
        });
    }
}
