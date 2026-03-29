Feature: Get All Products List

  Scenario: TC1: Get All Products List
    Given the API is available
    When user sends GET request to products list
    Then response status code should be 200
    And products list should not be empty