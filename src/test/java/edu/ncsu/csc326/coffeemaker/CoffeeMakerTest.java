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
import static org.mockito.Mockito.*;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Tatpol Samakpong
 */
public class CoffeeMakerTest {

    /**
     * The object under test.
     */
    private CoffeeMaker coffeeMaker1;
    private CoffeeMaker coffeeMaker2;
    private RecipeBook mockRecipeBook;
    private Recipe[] mockRecipes;

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
        coffeeMaker1 = new CoffeeMaker();
        mockRecipeBook = mock(RecipeBook.class);
        coffeeMaker2 = new CoffeeMaker(mockRecipeBook, new Inventory());

        //Set up for recipe1
        recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");
        //Set up for recipe2
        recipe2 = createRecipe("Mocha", "20", "3", "1", "1", "75");
        //Set up for recipe3
        recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");
        //Set up for recipe4
        recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");

        mockRecipes = new Recipe[]{recipe1, recipe2, recipe3, recipe4};
    }

    /**
     * Given a coffee maker with no recipe in recipe book
     * When we add recipes
     * Then the recipes should be added to the recipe book of coffee maker
     */
    @Test
    public void testAddRecipe() {
        coffeeMaker1.addRecipe(recipe1);
        coffeeMaker1.addRecipe(recipe2);
        coffeeMaker1.addRecipe(recipe3);
        assertEquals(recipe1, coffeeMaker1.getRecipes()[0]);
        assertEquals(recipe2, coffeeMaker1.getRecipes()[1]);
        assertEquals(recipe3, coffeeMaker1.getRecipes()[2]);
    }

    /**
     * Given a recipe book of coffee maker already have 3 recipes
     * When we add a recipe
     * Then the recipe should not be added
     */
    @Test
    public void testAddRecipeMoreThanThree() {
        assertTrue(coffeeMaker1.addRecipe(recipe1));
        assertTrue(coffeeMaker1.addRecipe(recipe2));
        assertTrue(coffeeMaker1.addRecipe(recipe3));
        // This should return false
        assertFalse(coffeeMaker1.addRecipe(recipe4));
    }

    /**
     * Given a recipe book of coffee maker has a recipe
     * When we add the same recipe which is the one that already existed
     * Then the recipe should not be added
     */
    @Test
    public void testAddDuplicateRecipe() {
        coffeeMaker1.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker1.getRecipes()[0]);
        // The duplicated recipe should not be added
        assertFalse(coffeeMaker1.addRecipe(recipe1));
        assertNull(coffeeMaker1.getRecipes()[1]);
    }

    /**
     * Given a recipe book of coffee maker has a recipe
     * When we select recipe to delete by its index
     * Then the selected recipe should be deleted and return its name
     */
    @Test
    public void testDeleteRecipe() {
        coffeeMaker1.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker1.getRecipes()[0]);
        // Return the deleted recipe name
        assertEquals(recipe3.getName(), coffeeMaker1.deleteRecipe(0));
        assertNull(coffeeMaker1.getRecipes()[0]);
    }

    /**
     * Given the selected recipe index doesn't match any recipes
     * When we select that index to delete
     * Then nothing will happen and return null
     */
    @Test
    public void testDeleteNonExistentRecipe() {
        assertNull(coffeeMaker1.deleteRecipe(0));
        coffeeMaker1.addRecipe(recipe2);
        assertEquals(recipe2.getName(), coffeeMaker1.deleteRecipe(0));
        // The deleted recipe doesn't exist
        assertNull(coffeeMaker1.deleteRecipe(0));
    }

    /**
     * Given a recipe in recipe book of coffee maker
     * When we selected its index to edit and define a new recipe
     * Then the new recipe will be replaced and return the old one's name
     */
    @Test
    public void testEditRecipe() {
        coffeeMaker1.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker1.getRecipes()[0]);
        assertEquals(recipe3.getName(), coffeeMaker1.editRecipe(0, recipe1));
        // Edit recipe 3 to recipe 1
        assertEquals(recipe1, coffeeMaker1.getRecipes()[0]);
    }

    /**
     * Given the selected recipe index doesn't match any recipes
     * When select that index to edit and define a new recipe
     * Then nothing will happen, there will be no replacement and return null
     */
    @Test
    public void testEditNonExistentRecipe() {
        assertNull(coffeeMaker1.editRecipe(0, recipe4));
        coffeeMaker1.addRecipe(recipe4);
        assertEquals(recipe4.getName(), coffeeMaker1.deleteRecipe(0));
        assertNull(coffeeMaker1.getRecipes()[0]);
        assertNull(coffeeMaker1.editRecipe(0, recipe1));
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
        coffeeMaker1.addInventory("4", "7", "0", "9");
        coffeeMaker1.addInventory("2", "3", "5", "1");
        coffeeMaker1.addInventory("1", "2", "3", "4");
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
        coffeeMaker1.addInventory("4", "-1", "asdf", "3");
        coffeeMaker1.addInventory("2", "-8", "3", "1");
        coffeeMaker1.addInventory("one", "two", "three", "four");
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
        assertEquals(inventoryString, coffeeMaker1.checkInventory());
        coffeeMaker1.addInventory("2", "3", "1", "4");
        inventoryString = "Coffee: 17\nMilk: 18\nSugar: 16\nChocolate: 19\n";
        assertEquals(inventoryString, coffeeMaker1.checkInventory());
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
        coffeeMaker1.addInventory("a", "12", "1", "5"); // throws exception
        assertEquals(inventoryString, coffeeMaker1.checkInventory()); // amount should be the same
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying more than
     * the coffee costs
     * Then we get the correct change back.
     */
    @Test
    public void testMakeCoffee() {
        coffeeMaker1.addRecipe(recipe1);
        assertEquals(25, coffeeMaker1.makeCoffee(0, 75));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying less than
     * the coffee costs
     * Then we get the change as same as paid amount and fail purchasing
     */
    @Test
    public void testMakeCoffeeWithNotEnoughMoney() {
        coffeeMaker1.addRecipe(recipe4);
        assertEquals(50, coffeeMaker1.makeCoffee(0, 50));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the recipe which
     * require ingredients more than we have
     * Then we get the change as same as paid amount and fail purchasing
     */
    @Test
    public void testMakeCoffeeWithNotEnoughInventory() {
        coffeeMaker1.addRecipe(recipe2);
        assertEquals(100, coffeeMaker1.makeCoffee(0, 100));
    }

    /**
     * Given we select the recipe that doesn't exist
     * When we purchase coffee with that recipe
     * Then we get the change as same as paid amount and fail purchasing
     */
    @Test
    public void testMakeCoffeeWithNonExistentRecipe() {
        assertEquals(100, coffeeMaker1.makeCoffee(1, 100));
    }

    /**
     * Test purchasing a valid coffee recipe with mock recipeBook
     * and get correctly change amount
     */
    @Test
    public void testMakeCoffeeWithMock() {
        mockRecipes[0] = mock(Recipe.class);
        mockRecipes[1] = mock(Recipe.class);
        when(mockRecipes[0].getAmtChocolate()).thenReturn(0);
        when(mockRecipes[0].getAmtCoffee()).thenReturn(3);
        when(mockRecipes[0].getAmtMilk()).thenReturn(1);
        when(mockRecipes[0].getAmtSugar()).thenReturn(1);
        when(mockRecipes[0].getPrice()).thenReturn(50);

        when(mockRecipeBook.getRecipes()).thenReturn(mockRecipes);
        assertEquals(25, coffeeMaker2.makeCoffee(0, 75));

        verify(mockRecipeBook, times(4)).getRecipes();

        // Verify the selected recipe called each of get inventory method once
        verify(mockRecipes[0], atLeastOnce()).getAmtChocolate();
        verify(mockRecipes[0], atLeastOnce()).getAmtCoffee();
        verify(mockRecipes[0], atLeastOnce()).getAmtMilk();
        verify(mockRecipes[0], atLeastOnce()).getAmtSugar();
        // Other recipe will not be called
        verify(mockRecipes[1], never()).getAmtChocolate();
        verify(mockRecipes[1], never()).getAmtCoffee();
        verify(mockRecipes[1], never()).getAmtMilk();
        verify(mockRecipes[1], never()).getAmtSugar();
    }

    /**
     * Test purchasing a valid coffee recipe with not enough money to purchase,
     * so the purchasing failed and change will be the same as paid amount
     */
    @Test
    public void testMakeCoffeeNotEnoughMoneyWithMock() {
        // Recipe at index 3 has price more than paid amount
        when(mockRecipeBook.getRecipes()).thenReturn(mockRecipes);
        assertEquals(50, coffeeMaker2.makeCoffee(3, 50));

        verify(mockRecipeBook, times(2)).getRecipes();
    }

    /**
     * Test purchasing a valid coffee recipe with not enough inventory,
     * so the purchasing failed and change will be the same as paid amount
     */
    @Test
    public void testMakeCoffeeNotEnoughInventoryWithMock() {
        // Recipe at index 1 use higher inventory amount
        when(mockRecipeBook.getRecipes()).thenReturn(mockRecipes);
        assertEquals(100, coffeeMaker2.makeCoffee(1, 100));

        verify(mockRecipeBook, times(3)).getRecipes();
    }

    /**
     * Test purchasing an invalid coffee recipe, so the purchasing
     * failed and change will be the same as paid amount
     */
    @Test
    public void testMakeCoffeeNonExistentRecipeWithMock() {
        // Set recipe at index 1 to null
        Recipe[] newRecipesClone = mockRecipes.clone();
        newRecipesClone[1] = null;

        when(mockRecipeBook.getRecipes()).thenReturn(newRecipesClone);
        assertEquals(100, coffeeMaker2.makeCoffee(1, 100));

        verify(mockRecipeBook, times(1)).getRecipes();
    }
}