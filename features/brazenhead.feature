Feature: Functionality provided by the Brazenhead module

  Scenario: Calling a method which takes no parameters
    When I call the method "scroll_down" on the Brazenhead module
    Then I should receive a successful result from the Brazenhead module
    
  Scenario: Calling a method which takes a parameter
    When I call the method "click_on_text" on the Brazenhead module passing "Content"
    Then I should receive a successful result from the Brazenhead module

  Scenario: Chaining two method calls together
    When I chain together the methods "scroll_down" and "scroll_up" using the target "Robotium"
    Then I should receive a successful result from the Brazenhead module

  Scenario: Chaining two method calls together with second call on result of first
    When I chain together the methods "getCurrentViews" and "size" using the target "LastResultOrRobotium"
    Then I should receive a successful result from the Brazenhead module
    And the result from the chained calls should match "\d+"

  Scenario: Chaining two method calls should use "LastResultOrRobotium" target by default
    When I chain together the methods "getCurrentViews" and "size" on the Brazenhead module
    Then I should receive a successful result from the Brazenhead module
    And the result from the chained calls should match "\d+"

  Scenario: Chaining two method calls using a variable
    When I call "get_text" passing the argument "Graphics" and saving it into the variable "@@graphics@@"
    And then I call "click_on_view" using the variable "@@graphics@@" using the target "Robotium"
    Then I should see "AlphaBitmap" from the Brazenhead module

  Scenario: Chaining twice
    When I chain together the methods "getCurrentViews" and "size" on the Brazenhead module
    And I call "get_text" passing the argument "Graphics" and saving it into the variable "@@graphics@@"
    And then I call "click_on_view" using the variable "@@graphics@@" using the target "Robotium" on the same driver
    Then I should see "AlphaBitmap" from the Brazenhead module

