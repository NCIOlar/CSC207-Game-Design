package Tests;

import AdventureModel.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DEV_Shop {
    /**
     * Test object quantities in shop and inventory after each purchase
     */
    @Test
    void updateQuantityTest() {
        AdventureGame game = new AdventureGame("EasyGame");
        game.player.funds = 100;
        Shop shop = new Shop("EasyGame");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("VEST")) {
                game.player.buyObject(object, shop);
                assertEquals(0, shop.objectsForSale.get(object));
                assertEquals(1, game.player.getInventory().size());
                break;
            }
        }
    }

    /**
     * Test the status effects applied onto player from shop item
     */
    @Test
    void applyObjectTest() {
        AdventureGame game = new AdventureGame("EasyGame");
        game.player.funds = 100;
        game.player.defense = 0;
        Shop shop = new Shop("EasyGame");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("VEST")) {
                game.player.buyObject(object, shop);
                game.player.useItem(object);
                assertEquals(10, game.player.getDefense());
                assertEquals(0, game.player.getInventory().size());
                break;
            }
        }
    }
    /**
     * Test if funds get depleted after purchase
     */
    @Test
    void fundsTest() {
        AdventureGame game = new AdventureGame("EasyGame");
        game.player.funds = 100;
        Shop shop = new Shop("EasyGame");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("VEST")) {
                game.player.buyObject(object, shop);
                assertEquals(10, object.getCost());
                assertEquals(90, game.player.getFunds());
                break;
            }
        }
    }
    /**
     * Test if out of stock attributes is true.
     */
    @Test
    void outOfStockTest() {
        AdventureGame game = new AdventureGame("EasyGame");
        game.player.funds = 100;
        Shop shop = new Shop("EasyGame");
        for (AdventureObject object: shop.objectsForSale.keySet()) {
            if (object.getName().equals("VEST")) {
                game.player.buyObject(object, shop);
                assertFalse(shop.outOfStock);
                assertFalse(game.player.buyObject(object, shop));
                break;
            }
        }
    }
}
