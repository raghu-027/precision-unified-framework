Feature: Create User Account

  Scenario: Verify user account is created successfully
    Given the API is available
    When user sends POST request to create account with valid details
    Then create response status code should be 200
    And response message should confirm user created
