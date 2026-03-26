Feature: Update User Account

  Scenario: Verify user account is updated successfully
    Given the API is available
    When user sends PUT request to update account with new details
    Then update response status code should be 200
    And response message should confirm user updated
