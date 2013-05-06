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

  Scenario: Finding list items by text
    Given I'm on the custom lists screen
    When I select the list item that contains "Lorem ipsum"
    Then the found list item should be a "android.widget.RelativeLayout"

  Scenario: Finding list items by index
    Given I'm on the arrays list
    When I select the list item at index "12"
    Then the text of the found list item should be "Aisy Cendre"

  Scenario: Pressing list items by index
    When I'm on the lists screen
    Then I can select list item "18" even if it is off of the screen
    And I should see "Views.Lists.18. Custom items"

  Scenario: Getting web views in different ways
    When I'm on the web views screen
    Then I should be able to find web views with these properties:
      | property    | value                   |
      | id          | home-feature            |
      | cssSelector | div.navsection          |
      | textContent | Mobile                  |
      | tagName     | div                     |
