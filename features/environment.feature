@environment
Feature:  Working with multiple environment setups

  Scenario: Missing ANDROID_HOME
    When the environment is missing ANDROID_HOME
    Then a Brazenhed::Environment error is raised about "ANDROID_HOME"