Feature: Search Product

  Scenario: TC 2: Get Single Product Details
    Given the API is available
    When user searches for product "top"
    Then response status code should be 200
    And search results should not be empty