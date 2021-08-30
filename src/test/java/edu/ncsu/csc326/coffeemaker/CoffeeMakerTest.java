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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

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
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for r1
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("50");

        //Set up for r2
        recipe2 = new Recipe();
        recipe2.setName("Mocha");
        recipe2.setAmtChocolate("20");
        recipe2.setAmtCoffee("3");
        recipe2.setAmtMilk("1");
        recipe2.setAmtSugar("1");
        recipe2.setPrice("75");

        //Set up for r3
        recipe3 = new Recipe();
        recipe3.setName("Latte");
        recipe3.setAmtChocolate("0");
        recipe3.setAmtCoffee("3");
        recipe3.setAmtMilk("3");
        recipe3.setAmtSugar("1");
        recipe3.setPrice("100");

        //Set up for r4
        recipe4 = new Recipe();
        recipe4.setName("Hot Chocolate");
        recipe4.setAmtChocolate("4");
        recipe4.setAmtCoffee("0");
        recipe4.setAmtMilk("1");
        recipe4.setAmtSugar("1");
        recipe4.setPrice("65");
    }

    @Test
    public void testAddRecipe() {
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
        assertEquals(recipe3, coffeeMaker.getRecipes()[2]);
    }

    @Test
    public void testAddRecipeMoreThanThree() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe2));
        assertTrue(coffeeMaker.addRecipe(recipe3));
        // This should return false
        assertFalse(coffeeMaker.addRecipe(recipe4));
    }

    @Test
    public void testAddDuplicateRecipe() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        // The duplicated recipe should not be added
        assertFalse(coffeeMaker.addRecipe(recipe1));
        assertNull(coffeeMaker.getRecipes()[1]);
    }

    @Test
    public void testDeleteRecipe() {
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[0]);
        // Return the deleted recipe name
        assertEquals(recipe3.getName(), coffeeMaker.deleteRecipe(0));
        assertNull(coffeeMaker.getRecipes()[0]);
    }

    @Test
    public void testDeleteNonExistingRecipe() {
        assertNull(coffeeMaker.deleteRecipe(0));

        coffeeMaker.addRecipe(recipe2);
        assertEquals(recipe2.getName(), coffeeMaker.deleteRecipe(0));
        // The deleted recipe doesn't exist
        assertNull(coffeeMaker.deleteRecipe(0));
    }

    @Test
    public void testEditRecipe() {
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[0]);
        assertEquals(recipe3.getName(), coffeeMaker.editRecipe(0, recipe1));
        // Edit recipe 3 to recipe 1
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
    }

    @Test
    public void testEditNonExistingRecipe() {
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
     * @throws InventoryException if there was an error parsing the quanity
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
     * @throws InventoryException if there was an error parsing the quanity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventoryException() throws InventoryException {
        coffeeMaker.addInventory("4", "-1", "asdf", "3");
        coffeeMaker.addInventory("2", "-8", "3", "1");
        coffeeMaker.addInventory("one", "two", "three", "four");
    }

    @Test
    public void testCheckInventory() throws InventoryException {
        // Initial amount should be 15
        String inventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
        assertEquals(inventoryString, coffeeMaker.checkInventory());
        coffeeMaker.addInventory("2", "3", "1", "4");
        inventoryString = "Coffee: 17\nMilk: 18\nSugar: 16\nChocolate: 19\n";
        assertEquals(inventoryString, coffeeMaker.checkInventory());
    }

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

    @Test
    public void testMakeCoffeeWithNotEnoughMoney() {
        coffeeMaker.addRecipe(recipe4);
        assertEquals(50, coffeeMaker.makeCoffee(0, 50));
    }

    @Test
    public void testMakeCoffeeWithNotEnoughInventory() {
        coffeeMaker.addRecipe(recipe2);
        assertEquals(100, coffeeMaker.makeCoffee(0, 100));
    }
}