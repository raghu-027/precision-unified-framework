Feature: Negative Validation

  Scenario: Verify error when searching without search parameter
    Given the API is available
    When user sends POST request to search without any parameter
    Then response should indicate missing parameter
