package de.axiomlab.oceanclient.client.data;

public class ShopItem {
    private final int id;
    private final String name;
    private final String type;
    private final int price;
    private final String textureName;
    private boolean equipped;

    public ShopItem(int id, String name, String type, int price, String textureName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.textureName = textureName;
        this.equipped = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public String getTextureName() {
        return textureName;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
