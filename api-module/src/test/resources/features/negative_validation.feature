Feature: Negative Validation

  Scenario: Verify error when searching without search parameter
    Given the API is available
    When user sends POST request to search without parameter
    Then response status code should be 200
    And response code should indicate bad request