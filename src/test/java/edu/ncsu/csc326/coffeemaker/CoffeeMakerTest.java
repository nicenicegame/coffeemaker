/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Tatpol Samakpong
 */
public class CoffeeMakerTest {

    /**
     * The object under test.
     */
    private CoffeeMaker coffeeMaker;

    // Sample recipes to use in testing.
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;

    /**
     * Utility function for create a recipe object with given name,
     * amount of chocolate, amount of coffee, amount of milk,
     * amount of sugar, and the price
     *
     * @param name      of the recipe
     * @param chocolate amount
     * @param coffee    amount
     * @param milk      amount
     * @param sugar     amount
     * @param price     of the recipe
     * @return recipe object of given parameters
     * @throws RecipeException if invalid string of
     *                         number cannot be parsed to integer
     */
    public static Recipe createRecipe(
            String name,
            String chocolate,
            String coffee,
            String milk,
            String sugar,
            String price
    ) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setAmtChocolate(chocolate);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setPrice(price);
        return recipe;
    }

    /**
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for recipe1
        recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");

        //Set up for recipe2
        recipe2 = createRecipe("Mocha", "20", "3", "1", "1", "75");

        //Set up for recipe3
        recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");

        //Set up for recipe4
        recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");
    }

    /**
     * Given a coffee maker with no recipe in recipe book
     * When we add recipes
     * Then the recipes should be added to the recipe book of coffee maker
     */
    @Test
    public void testAddRecipe() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
        assertEquals(recipe3, coffeeMaker.getRecipes()[2]);
    }

    /**
     * Given a recipe book of coffee maker already have 3 recipes
     * When we add a recipe
     * Then the recipe should not be added
     */
    @Test
    public void testAddRecipeMoreThanThree() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe2));
        assertTrue(coffeeMaker.addRecipe(recipe3));
        // This should return false
        assertFalse(coffeeMaker.addRecipe(recipe4));
    }

    /**
     * Given a recipe book of coffee maker has a recipe
     * When we add the same recipe which is the one that already existed
     * Then the recipe should not be added
     */
    @Test
    public void testAddDuplicateRecipe() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        // The duplicated recipe should not be added
        assertFalse(coffeeMaker.addRecipe(recipe1));
        assertNull(coffeeMaker.getRecipes()[1]);
    }

    /**
     * Given a recipe book of coffee maker has a recipe
     * When we select recipe to delete by its index
     * Then the selected recipe should be deleted and return its name
     */
    @Test
    public void testDeleteRecipe() {
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[0]);
        // Return the deleted recipe name
        assertEquals(recipe3.getName(), coffeeMaker.deleteRecipe(0));
        assertNull(coffeeMaker.getRecipes()[0]);
    }

    /**
     * Given the selected recipe index doesn't match any recipes
     * When we select that index to delete
     * Then nothing will happen and return null
     */
    @Test
    public void testDeleteNonExistentRecipe() {
        assertNull(coffeeMaker.deleteRecipe(0));
        coffeeMaker.addRecipe(recipe2);
        assertEquals(recipe2.getName(), coffeeMaker.deleteRecipe(0));
        // The deleted recipe doesn't exist
        assertNull(coffeeMaker.deleteRecipe(0));
    }

    /**
     * Given a recipe in recipe book of coffee maker
     * When we selected its index to edit and define a new recipe
     * Then the new recipe will be replaced and return the old one's name
     */
    @Test
    public void testEditRecipe() {
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[0]);
        assertEquals(recipe3.getName(), coffeeMaker.editRecipe(0, recipe1));
        // Edit recipe 3 to recipe 1
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
    }

    /**
     * Given the selected recipe index doesn't match any recipes
     * When select that index to edit and define a new recipe
     * Then nothing will happen, there will be no replacement and return null
     */
    @Test
    public void testEditNonExistentRecipe() {
        assertNull(coffeeMaker.editRecipe(0, recipe4));
        coffeeMaker.addRecipe(recipe4);
        assertEquals(recipe4.getName(), coffeeMaker.deleteRecipe(0));
        assertNull(coffeeMaker.getRecipes()[0]);
        assertNull(coffeeMaker.editRecipe(0, recipe1));
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with well-formed quantities
     * Then we do not get an exception trying to read the inventory quantities.
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test
    public void testAddInventory() throws InventoryException {
        coffeeMaker.addInventory("4", "7", "0", "9");
        coffeeMaker.addInventory("2", "3", "5", "1");
        coffeeMaker.addInventory("1", "2", "3", "4");
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with malformed quantities (i.e., a negative
     * quantity and a non-numeric string)
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventoryException() throws InventoryException {
        coffeeMaker.addInventory("4", "-1", "asdf", "3");
        coffeeMaker.addInventory("2", "-8", "3", "1");
        coffeeMaker.addInventory("one", "two", "three", "four");
    }

    /**
     * Given a coffee maker with default inventory
     * When we add valid inventory quantities to see if its change
     * Then we do not get an exception and the inventory quantities
     * will be updated correctly
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test
    public void testCheckInventory() throws InventoryException {
        // Initial amount should be 15
        String inventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
        assertEquals(inventoryString, coffeeMaker.checkInventory());
        coffeeMaker.addInventory("2", "3", "1", "4");
        inventoryString = "Coffee: 17\nMilk: 18\nSugar: 16\nChocolate: 19\n";
        assertEquals(inventoryString, coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with default inventory
     * When we add invalid inventory quantities to see if its doesn't change
     * Then we get an exception and the inventory quantities won't be updated
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testCheckInventoryException() throws InventoryException {
        String inventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
        coffeeMaker.addInventory("a", "12", "1", "5"); // throws exception
        assertEquals(inventoryString, coffeeMaker.checkInventory()); // amount should be the same
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying more than
     * the coffee costs
     * Then we get the correct change back.
     */
    @Test
    public void testMakeCoffee() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(25, coffeeMaker.makeCoffee(0, 75));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying less than
     * the coffee costs
     * Then we get the change as same as paid amount and fail purchasing
     */
    @Test
    public void testMakeCoffeeWithNotEnoughMoney() {
        coffeeMaker.addRecipe(recipe4);
        assertEquals(50, coffeeMaker.makeCoffee(0, 50));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the recipe which
     * require ingredients more than we have
     * Then we get the change as same as paid amount and fail purchasing
     */
    @Test
    public void testMakeCoffeeWithNotEnoughInventory() {
        coffeeMaker.addRecipe(recipe2);
        assertEquals(100, coffeeMaker.makeCoffee(0, 100));
    }

    /**
     * Given we select the recipe that doesn't exist
     * When we purchase coffee with that recipe
     * Then we get the change as same as paid amount and fail purchasing
     */
    @Test
    public void testMakeCoffeeWithNonExistentRecipe() {
        assertEquals(100, coffeeMaker.makeCoffee(1, 100));
    }
}