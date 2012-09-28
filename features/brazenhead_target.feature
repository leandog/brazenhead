Feature: Using the Brazenhead target

  Scenario: Retrieving Id Values
    When I call "id_from_name" with "snack" on the target "Brazenhead"
    Then I should receive an id value back from the Brazenhead module
