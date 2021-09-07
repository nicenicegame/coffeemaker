package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Inventory class.
 *
 * @author Tatpol Samakpong
 */
public class InventoryTest {
    private Inventory inventory;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;

    @Before
    public void setUp() throws RecipeException {
        inventory = new Inventory();
        //Set up for recipe1
        recipe1 = CoffeeMakerTest.createRecipe("Coffee", "0", "3", "1", "1", "50");

        //Set up for recipe2
        recipe2 = CoffeeMakerTest.createRecipe("Mocha", "20", "1", "1", "54", "75");

        //Set up for recipe3
        recipe3 = CoffeeMakerTest.createRecipe("Latte", "0", "3", "3", "1", "100");

        //Set up for recipe4
        recipe4 = CoffeeMakerTest.createRecipe("Hot Chocolate", "4", "56", "32", "1", "65");
    }

    @Test(expected = InventoryException.class)
    public void testAddStringCoffeeAmount() throws InventoryException {
        inventory.addCoffee("three");
    }

    @Test(expected = InventoryException.class)
    public void testAddNegativeCoffeeAmount() throws InventoryException {
        inventory.addCoffee("-3");
    }

    @Test(expected = InventoryException.class)
    public void testAddStringMilkAmount() throws InventoryException {
        inventory.addMilk("four");
    }

    @Test(expected = InventoryException.class)
    public void testAddNegativeMilkAmount() throws InventoryException {
        inventory.addMilk("-4");
    }

    @Test(expected = InventoryException.class)
    public void testAddStringSugarAmount() throws InventoryException {
        inventory.addSugar("five");
    }

    @Test(expected = InventoryException.class)
    public void testAddNegativeSugarAmount() throws InventoryException {
        inventory.addSugar("-5");
    }

    @Test(expected = InventoryException.class)
    public void testAddStringChocolateAmount() throws InventoryException {
        inventory.addChocolate("two");
    }

    @Test(expected = InventoryException.class)
    public void testAddNegativeChocolateAmount() throws InventoryException {
        inventory.addChocolate("-2");
    }

    @Test
    public void testSetInventory() {
        String inventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
        assertEquals(inventoryString, inventory.toString());
        inventory.setChocolate(3);
        inventory.setCoffee(5);
        inventory.setMilk(5);
        inventory.setSugar(10);
        inventoryString = "Coffee: 5\nMilk: 5\nSugar: 10\nChocolate: 3\n";
        assertEquals(inventoryString, inventory.toString());
    }

    @Test
    public void testSetInvalidInventory() {
        String inventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
        assertEquals(inventoryString, inventory.toString());
        inventory.setChocolate(-4);
        inventory.setCoffee(-3);
        inventory.setMilk(-1);
        inventory.setSugar(-3);
        // inventory amount should be the same
        assertEquals(inventoryString, inventory.toString());
    }

    @Test
    public void testEnoughIngredients() {
        assertTrue(inventory.enoughIngredients(recipe1));
        assertTrue(inventory.enoughIngredients(recipe3));
    }

    @Test
    public void testNotEnoughIngredients() {
        assertFalse(inventory.enoughIngredients(recipe2));
        assertFalse(inventory.enoughIngredients(recipe4));
    }
}
