Feature: Create User Account

  Scenario: TC 3: Create/Register User Account
    Given the API is available
    When user sends POST request to create account with valid details
    Then create response status code should be 200
    And response message should confirm user created
