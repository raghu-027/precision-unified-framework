Feature: Negative Validation

  Scenario: TC 6: Negative Validation
    Given the API is available
    When user sends POST request to search without any parameter
    Then response should indicate missing parameter
