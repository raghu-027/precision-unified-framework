Feature: Get All Products List

  Scenario: Verify all products are returned successfully
    Given the API is available
    When user sends GET request to products list
    Then response status code should be 200
    And products list should not be empty