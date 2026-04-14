
Feature: Hybrid API and UI validation

  @hybrid @regression
  Scenario: Create account via API and login via UI
    Given a unique API user payload is prepared
    When the user account is created through API
    Then the API should confirm account creation
    When the user logs into the UI with API created credentials
    Then the UI should show the user as logged in

  @hybrid @regression
  Scenario: Create account via API and verify UI login fails with invalid password
    Given a unique API user payload is prepared
    When the user account is created through API
    Then the API should confirm account creation
    When the user logs into the UI with API created email and invalid password
    Then the UI should show login error message

  @hybrid @regression
  Scenario: Create and delete account via API and verify UI login fails
    Given a unique API user payload is prepared
    When the user account is created through API
    Then the API should confirm account creation
    When the user account is deleted through API
    Then the API should confirm account deletion
    When the user logs into the UI with API created credentials
    Then the UI should show login error message

  @hybrid @regression @product
  Scenario: Validate product availability through API and UI
    When the product list is fetched through API
    Then the API should return a non-empty product list
    When the user navigates to the products page in UI
    Then the UI should show available products
