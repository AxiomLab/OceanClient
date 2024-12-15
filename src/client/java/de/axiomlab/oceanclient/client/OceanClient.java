package de.axiomlab.oceanclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import de.axiomlab.oceanclient.client.features.*;

public class OceanClient implements ClientModInitializer {
    public String version = "1.0 DEV-Beta";
    @Override
    public void onInitializeClient() {
        System.out.println("OceanClient Init Process started!");
        System.out.println("Welcome!");
        HeartPrefix.init();
        NameVisibility.init();
        // CapeRenderer.init();
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.getWindow() != null) {
                client.getWindow().setTitle("Ocean Client 1.0 DEV-Beta for MC 1.21.4");
            }
        });
        }
    }

