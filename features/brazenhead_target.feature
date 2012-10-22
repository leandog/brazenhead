Feature: Using the Brazenhead target

  Scenario: Retrieving Id Values
    When I call "id_from_name" with "snack" on the target "Brazenhead"
    Then I should receive an id value back from the Brazenhead module

  Scenario: Clicking on spinner items by id
    Given I'm on the controls screen
    When I select item "4" from the spinner with id "spinner1"
    Then the text "Jupiter" is selected in the spinner

  Scenario: Clicking on spinner items by view
    Given I'm on the controls screen
    When I select item "6" from the spinner view with id "spinner1"
    Then the text "Uranus" is selected in the spinner

  @wip
  Scenario: Finding list items by text
    Given I'm on the lists screen
    When I select the list item that contains "Lorem ipsum"
    Then the found list item should be a "android.widget.RelativeLayout"
