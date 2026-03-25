Feature: Search Product

  Scenario: Verify search returns matching products
    Given the API is available
    When user searches for product "top"
    Then response status code should be 200
    And search results should not be empty