package de.axiomlab.oceanclient.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import de.axiomlab.oceanclient.client.database.DatabaseManager;
import de.axiomlab.oceanclient.client.data.ShopItem;

import java.util.List;

public class ShopScreen extends Screen {
    private List<ShopItem> items;

    public ShopScreen() {
        super(Text.literal("Ocean Shop"));
        loadShopItems();
    }

    private void loadShopItems() {
        // Load items from database
    }

    @Override
    protected void init() {
        String uuid = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();



        int playerCoins = DatabaseManager.getCoins(uuid);

        int y = 50;
        for (ShopItem item : items) {
            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal(item.getName() + " (" + item.getPrice() + " coins)"),
                    button -> purchaseItem(item)
            ).dimensions(this.width / 2 - 100, y, 200, 20).build());
            y += 25;
        }
    }

    private void purchaseItem(ShopItem item) {
        // Purchase logic
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        String uuid = MinecraftClient.getInstance().getSession().getUuidOrNull().toString();

        int coins = DatabaseManager.getCoins(uuid);

        context.drawTextWithShadow(this.textRenderer,
                Text.literal("Your Coins: " + coins),
                this.width / 2 - 50, 20, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }
}
