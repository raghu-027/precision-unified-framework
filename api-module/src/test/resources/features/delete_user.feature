Feature: Delete User Account

  Scenario: TC4: Delete User Account
    Given the API is available
    When user sends DELETE request with valid credentials
    Then delete response status code should be 200
    And response message should confirm account deleted
