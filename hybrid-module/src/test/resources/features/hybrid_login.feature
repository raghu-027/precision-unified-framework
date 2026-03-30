Feature: Hybrid API and UI validation

  @hybrid @regression
  Scenario: Create account via API and login via UI
    Given a unique API user payload is prepared
    When the user account is created through API
    Then the API should confirm account creation
    When the user logs into the UI with API created credentials
    Then the UI should show the user as logged in
