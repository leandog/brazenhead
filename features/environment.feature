@environment
Feature:  Working with multiple environment setups

  Scenario: Missing ANDROID_HOME
    When the environment is missing ANDROID_HOME
    Then a Brazenhed::Environment error is raised about "ANDROID_HOME"

  Scenario: Missing path to platform
    When we are missing the path to an android platform
    Then a Brazenhed::Environment error is raised about "path to android-14 was not found"
