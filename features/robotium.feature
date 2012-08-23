Feature:  Calling Robotium methods

  Scenario: Getting a result from a parameterless method
    When I do nothing but "scrollDown"
    Then I should receive "(true)|(false)"
