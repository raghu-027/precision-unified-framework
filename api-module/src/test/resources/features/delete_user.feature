Feature: Delete User Account

  Scenario: Verify user account is deleted successfully
    Given the API is available
    When user sends DELETE request with valid credentials
    Then response status code should be 200
    And response message should confirm account deleted