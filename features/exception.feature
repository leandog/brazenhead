Feature:  Relaying error information back to the client

  Scenario: Gracefully handing invalid commands
    When I attempt to call a method that does not exist
    Then I should receive an internal server error
    And the exception should have detailed information
