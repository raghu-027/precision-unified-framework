Feature: Cart Management
  As a logged in user
  I want to manage my cart
  So that I can purchase products

  Background:
    Given user launches the application
    When user clicks on Signup Login button
    And user logs in with valid credentials

  @regression @cart
  Scenario: TC4 - Add product to cart and validate
    When user adds first product to cart
    And user clicks continue shopping
    And user clicks on Cart button
    Then cart page should be displayed
    And cart should contain the added product

  @regression @cart
  Scenario: TC5 - Remove item from cart and validate
    When user adds first product to cart
    And user clicks continue shopping
    And user clicks on Cart button
    And user removes item at index 0
    Then cart should be empty