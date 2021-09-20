package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {
    private CoffeeMaker coffeeMaker;
    private int recipeToPurchase;
    private int depositedAmount;

    @Given("the coffee maker is ready for user order")
    public void theCoffeeMakerIsReadyForUserOrder() throws RecipeException {
        coffeeMaker = new CoffeeMaker();
        Recipe recipe1 = CoffeeMakerTest.createRecipe("Mocha", "3", "3", "3", "3", "75");
        Recipe recipe2 = CoffeeMakerTest.createRecipe("Latte", "2", "2", "5", "5", "70");
        Recipe recipe3 = CoffeeMakerTest.createRecipe("Americano", "15", "20", "5", "5", "80");
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);
        coffeeMaker.addRecipe(recipe3);
    }

    @Given("the customer select coffee number {int}")
    public void theCustomerSelectCoffeeNumber(int arg0) {
        recipeToPurchase = arg0 - 1;
    }

    @When("customer deposit {int} baht")
    public void customerDepositBaht(int arg0) {
        depositedAmount = arg0;
    }

    @Then("the customer get change {int} baht")
    public void theCustomerGetChangeBaht(int arg0) {
        assertEquals(arg0, coffeeMaker.makeCoffee(recipeToPurchase, depositedAmount));
    }
}
