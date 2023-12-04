package AdventureModel;
import AdventureModel.AdventureObject;
import AdventureModel.Player;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import java.util.HashMap;

public class Shop {

    public HashMap<AdventureObject, Integer> objectsForSale = new HashMap<AdventureObject, Integer>();
    public boolean outOfStock;
    public String adventureName;

    // Create new instance of Medkit item sold in shop
    public AdventureObject medkit = new AdventureObject("MEDKIT",
            "A state-of-the-art medical kit equipped with advanced healing technology.\nThis heals the player for 20HP when consumed.",
            null, "Consumable", 20, 5);
    // Create new instance of Vest item sold in shop
    public AdventureObject vest = new AdventureObject("VEST",
            "A bulletproof vest crafted from durable kevlar.\nThis gives the player 10 defense when consumed.",
            null, "Consumable", 10, 10);
    // Create new instance of Mask item sold in shop
    public AdventureObject mask = new AdventureObject("MASK",
            "A gas mask blocks off noxious fumes. Perfect for exploring hazardous environments.\nThis makes the player immune to toxic gas when consumed.",
            null, "Consumable", 0, 10);
    // Create new instance of Stim item sold in shop
    public AdventureObject stim = new AdventureObject("STIM",
            "A slender syringe containing a potent cocktail of stimulants.\nThis increases player's damage by 20 when consumed",
            null, "Consumable", 20, 10);
    // Create new instance of Skip item sold in shop
    public AdventureObject skip = new AdventureObject("SKIP",
                "The Glock. One shot, one kill.\nThis item skips the current fight when consumed.",
                null, "Consumable", 10000, 10);

    public Shop(String adventureName) {
        this.adventureName = adventureName;
        this.outOfStock = false;
        if (adventureName.equals("EasyGame")) {
            objectsForSale.put(medkit, 3);
            objectsForSale.put(stim, 1);
            objectsForSale.put(vest, 1);

        } else if (adventureName.equals("MediumGame")) {
            objectsForSale.put(medkit, 3);
            objectsForSale.put(stim, 1);
            objectsForSale.put(vest, 1);
            objectsForSale.put(mask, 1);
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
