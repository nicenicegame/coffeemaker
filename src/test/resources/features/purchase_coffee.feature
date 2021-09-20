Feature: Customers can purchase coffee by menu number

  Coffee Menu
  1. Mocha 75 baht
  2. Latte 70 baht
  3. Americano 80 baht (not enough ingredients)

  Background:
    Given the coffee maker is ready for user order

  Scenario: Customer purchase coffee number 1 with money 80 baht
    Given the customer select coffee number 1
    When customer deposit 80 baht
    Then the customer get change 5 baht

  Scenario: Customer purchase coffee number 2 with money 60 baht (not enough money)
    Given the customer select coffee number 2
    When customer deposit 60 baht
    Then the customer get change 60 baht

  Scenario: Customer purchase coffee number 3 with money 100 baht (not enough inventory)
    Given the customer select coffee number 3
    When customer deposit 100 baht
    Then the customer get change 100 baht

  Scenario: Customer purchase coffee number 4 with money 50 baht (menu not available)
    Given the customer select coffee number 4
    When customer deposit 50 baht
    Then the customer get change 50 baht