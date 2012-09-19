Feature:  Serializing Information

  Scenario: Getting useful view information
    When I call a method that returns a view
    Then we should have basic information about a view

#  Scenario: Getting text view information
#    When I call a method that returns a text view
#    Then the text for the view should be returned
