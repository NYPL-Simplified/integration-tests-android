Feature: Testing threads1

  @exclude_android @wait1
  Scenario: Testing threads11
    When I wait for 5 seconds

  @wait1 @exclude_android
  Scenario: Testing threads12
    When I wait for 5 seconds
