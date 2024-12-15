package de.axiomlab.oceanclient.client.features;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class HeartPrefix {
    public static void init() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            MinecraftClient.getInstance().player.setCustomName(Text.literal("❤ ").append(client.player.getName()));
        });
    }
}
