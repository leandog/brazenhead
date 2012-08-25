Feature:  Calling Robotium methods

  Scenario: Getting a result from a parameterless method
    When I do nothing but "scrollDown"
    Then I should receive a successful result

  Scenario: Calling a method with integers
    When I call a method with an integer
    Then I should receive a successful result

  Scenario: Calling a method with strings
    When I call a method with a string
    Then I should receive a successful result

  Scenario:  Calling a method with floats
    When I call a method with a float
    Then I should receive a successful result

  Scenario:  Calling a method with booleans
    When I call a method with a boolean
    Then I should receive a successful result

  Scenario:  Getting useful View information
    When I get the first "Text" View I find
    Then I should receive a successful result
    And the view should have some basic information
