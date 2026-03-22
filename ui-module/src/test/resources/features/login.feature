Feature: User Login
  As a registered user
  I want to login to the application
  So that I can access my account

  @regression @login
  Scenario: TC2 - Login with valid credentials
    Given user launches the application
    When user clicks on Signup Login button
    And user logs in with valid credentials
    Then user should be logged in successfully

  @regression @login
  Scenario: TC3 - Login with invalid credentials
    Given user launches the application
    When user clicks on Signup Login button
    And user logs in with invalid credentials
    Then error message should be displayed