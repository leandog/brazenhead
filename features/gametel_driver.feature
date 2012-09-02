@focus
Feature: Functionality provided by the GametelDriver module

  Scenario: Calling a method which takes no parameters
    When I call the method "scroll_down" on the GametelDriver module
    Then I should receive a successful result from the GametelDriver module
    
