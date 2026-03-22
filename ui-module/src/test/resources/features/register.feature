Feature: User Registration
  As a new user
  I want to register on the application
  So that I can access my account

  @regression @register
  Scenario: TC1 - Register a new user and validate
    Given user launches the application
    When user clicks on Signup Login button
    And user enters signup name and email
    And user clicks signup button
    And user fills the registration form
    And user clicks create account button
    Then account created message should be displayed
    When user clicks continue button
    Then user should be logged in successfully