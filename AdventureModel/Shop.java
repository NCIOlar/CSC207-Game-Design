package AdventureModel;
import AdventureModel.AdventureObject;
import AdventureModel.Player;

import java.util.HashMap;

public class Shop {

    public HashMap<AdventureObject, Integer> objectsForSale;
    public boolean outOfStock;

    public Shop() {
        this.objectsForSale = null;
        this.outOfStock = false;
    }

    // When the supply is depleted
    public void setOutOfStock() {
        this.outOfStock = true;
    }

    // Create new instance of Medkit item sold in shop
    public AdventureObject newMedkit() {
        return new AdventureObject("Medkit",
                "A state-of-the-art medical kit equipped with advanced healing technology.\nThis heals the player for 20HP when consumed.",
                null, "Consumable", 20, 100);
    }

    // Create new instance of Vest item sold in shop
    public AdventureObject newVest() {
        return new AdventureObject("Vest",
                "A bulletproof vest crafted from durable kevlar.\nThis gives the player 10 defense when consumed.",
                null, "Consumable", 10, 200);
    }

    // Create new instance of Mask item sold in shop
    public AdventureObject newMask() {
        return new AdventureObject("Mask",
                "A gas mask blocks off noxious fumes. Perfect for exploring hazardous environments.\nThis makes the player immune to toxic gas when consumed.",
                null, "Consumable", 0, 200);
    }

    // Create new instance of Stim item sold in shop
    public AdventureObject newStim() {
        return new AdventureObject("Stim",
                "A slender syringe containing a potent cocktail of stimulants.\nThis increases player's damage by 20 when consumed",
                null, "Consumable", 20, 200);
    }

    // Create new instance of Skip item sold in shop
    public AdventureObject newSkip() {
        return new AdventureObject("Skip",
                "The Glock. One shot, one kill.\nThis item skips the current fight when consumed.",
                null, "Consumable", 10000, 200);
    }
}
