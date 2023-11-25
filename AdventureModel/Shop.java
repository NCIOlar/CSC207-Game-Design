package AdventureModel;
import AdventureModel.AdventureObject;
import AdventureModel.Player;

import java.util.HashMap;

public class Shop {

    public HashMap<AdventureObject, Integer> objectsForSale;
    public boolean outOfStock;
    public String adventureName;

    // Create new instance of Medkit item sold in shop
    public AdventureObject medkit = new AdventureObject("Medkit",
            "A state-of-the-art medical kit equipped with advanced healing technology.\nThis heals the player for 20HP when consumed.",
            null, "Consumable", 20, 100);
    // Create new instance of Vest item sold in shop
    public AdventureObject vest = new AdventureObject("Vest",
            "A bulletproof vest crafted from durable kevlar.\nThis gives the player 10 defense when consumed.",
            null, "Consumable", 10, 200);
    // Create new instance of Mask item sold in shop
    public AdventureObject mask = new AdventureObject("Mask",
            "A gas mask blocks off noxious fumes. Perfect for exploring hazardous environments.\nThis makes the player immune to toxic gas when consumed.",
            null, "Consumable", 0, 200);
    // Create new instance of Stim item sold in shop
    public AdventureObject stim = new AdventureObject("Stim",
            "A slender syringe containing a potent cocktail of stimulants.\nThis increases player's damage by 20 when consumed",
            null, "Consumable", 20, 200);
    // Create new instance of Skip item sold in shop
    public AdventureObject skip = new AdventureObject("Skip",
                "The Glock. One shot, one kill.\nThis item skips the current fight when consumed.",
                null, "Consumable", 10000, 200);

    public Shop(String adventureName) {
        this.adventureName = adventureName;
        this.outOfStock = false;
        this.objectsForSale = new HashMap<AdventureObject, Integer>();
        if (adventureName.equals("EasyGame")) {
            objectsForSale.put(medkit, 3);
            objectsForSale.put(stim, 1);
        } else if (adventureName.equals("MediumGame")) {
            objectsForSale.put(medkit, 3);
            objectsForSale.put(stim, 1);
            objectsForSale.put(vest, 1);
        } else {
            objectsForSale.put(medkit, 3);
            objectsForSale.put(stim, 1);
            objectsForSale.put(vest, 1);
            objectsForSale.put(mask, 1);
            objectsForSale.put(skip, 1);
        }
    }

    // When the supply is depleted
    public void setOutOfStock() {
        this.outOfStock = true;
    }


}
