package de.axiomlab.oceanclient.client.database;

import java.sql.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://axiom-mc.org:3306/ocean";
    private static final String DB_USER = "ocean_usr";
    private static final String DB_PASS = "Gg_4fp796";

    public static boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            return true;
        } catch (SQLException e) {
            showConnectionError();
            return false;
        }
    }

    public static int getCoins(String uuid) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT coins FROM players WHERE uuid = ?");
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("coins");
            }
        } catch (SQLException e) {
            showConnectionError();
        }
        return 0;
    }

    public static void addCoins(String uuid, String username, int amount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO players (uuid, username, coins) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE coins = coins + ?"
            );
            stmt.setString(1, uuid);
            stmt.setString(2, username);
            stmt.setInt(3, amount);
            stmt.setInt(4, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            showConnectionError();
        }
    }

    private static void showConnectionError() {
        Screen errorScreen = new Screen(Text.literal("Connection Error")) {
            @Override
            protected void init() {
                this.addDrawableChild(ButtonWidget.builder(Text.literal("OK"), button ->
                        MinecraftClient.getInstance().setScreen(null)
                ).dimensions(this.width / 2 - 100, this.height / 2 + 30, 200, 20).build());
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, float delta) {
                this.renderBackground(context, mouseX, mouseY, delta);
                context.drawTextWithShadow(this.textRenderer,
                        Text.literal("Cannot connect to the shop"),
                        this.width / 2 - 100, this.height / 2 - 20, 0xFFFFFF);
                context.drawTextWithShadow(this.textRenderer,
                        Text.literal("Please mail us: info@axiom-mc.org"),
                        this.width / 2 - 100, this.height / 2, 0xFFFFFF);
                super.render(context, mouseX, mouseY, delta);
            }
        };
        MinecraftClient.getInstance().setScreen(errorScreen);

    }
    public static String getEquippedCapeTexture(String uuid) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement stmt = conn.prepareStatement("""
            SELECT si.texture_name 
            FROM player_items pi 
            JOIN shop_items si ON pi.item_id = si.id 
            WHERE pi.uuid = ? AND pi.equipped = TRUE
        """);
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("texture_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
