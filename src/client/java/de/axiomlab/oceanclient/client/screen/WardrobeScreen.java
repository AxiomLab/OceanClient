package de.axiomlab.oceanclient.client.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import java.util.List;
import de.axiomlab.oceanclient.client.data.ShopItem;


public class WardrobeScreen extends Screen {
    private List<ShopItem> ownedItems;

    public WardrobeScreen() {
        super(Text.literal("Wardrobe"));
        loadOwnedItems();
    }

    private void loadOwnedItems() {
        // Load owned items from database
    }

    @Override
    protected void init() {
        int y = 50;
        for (ShopItem item : ownedItems) {
            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal(item.getName()),
                    button -> equipItem(item)
            ).dimensions(this.width / 2 - 100, y, 200, 20).build());
            y += 25;
        }
    }

    private void equipItem(ShopItem item) {
        // Equip logic
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(this.textRenderer,
                Text.literal("Your Wardrobe"),
                this.width / 2 - 50, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
