package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Inventory class.
 *
 * @author Tatpol Samakpong
 */
public class InventoryTest {
    /**
     * The object under test
     */
    private Inventory inventory;

    // Sample recipes
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;

    /**
     * Initializes some recipes to test with the {@link Inventory}
     * object we wish to test
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
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

    /**
     * Given we want to add coffee to the inventory with the string
     * that cannot be parsed to integer
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddStringCoffeeAmount() throws InventoryException {
        inventory.addCoffee("three");
    }

    /**
     * Given we want to add coffee to the inventory with the
     * string of negative number
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeCoffeeAmount() throws InventoryException {
        inventory.addCoffee("-3");
    }

    /**
     * Given we want to add milk to the inventory with the string
     * that cannot be parsed to integer
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddStringMilkAmount() throws InventoryException {
        inventory.addMilk("four");
    }

    /**
     * Given we want to add milk to the inventory with the
     * string of negative number
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeMilkAmount() throws InventoryException {
        inventory.addMilk("-4");
    }

    /**
     * Given we want to add sugar to the inventory with the string
     * that cannot be parsed to integer
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddStringSugarAmount() throws InventoryException {
        inventory.addSugar("five");
    }

    /**
     * Given we want to add sugar to the inventory with the
     * string of negative number
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeSugarAmount() throws InventoryException {
        inventory.addSugar("-5");
    }

    /**
     * Given we want to add chocolate to the inventory with the string
     * that cannot be parsed to integer
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddStringChocolateAmount() throws InventoryException {
        inventory.addChocolate("two");
    }

    /**
     * Given we want to add chocolate to the inventory with the
     * string of negative number
     * When we add inventory with that string
     * Then we get an exception thrown up
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeChocolateAmount() throws InventoryException {
        inventory.addChocolate("-2");
    }

    /**
     * Given we want to check whether inventory will be updated
     * after set the new amount
     * When we set the new amount to the inventory
     * Then the inventory amount should be updated
     */
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

    /**
     * Given we want to check whether inventory won't be updated
     * after set the new amount with negative value
     * When we set the new amount to the inventory
     * Then the inventory amount shouldn't be updated
     */
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

    /**
     * Given the inventory with default inventory amount
     * When we want to check if the selected recipe was enough to the inventory
     * Then it will return true if it was enough, otherwise return false
     */
    @Test
    public void testEnoughIngredients() {
        assertTrue(inventory.enoughIngredients(recipe1));
        assertTrue(inventory.enoughIngredients(recipe3));
        assertFalse(inventory.enoughIngredients(recipe2));
        assertFalse(inventory.enoughIngredients(recipe4));
    }
}
