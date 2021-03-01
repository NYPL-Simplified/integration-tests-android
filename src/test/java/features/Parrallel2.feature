Feature: Parallel2

  @wait1
  Scenario: Wait2
    Given Application is opened
    When I open Catalog
    When I wait for 2 seconds

  @tier1 @wait1
  Scenario: Add Library
    When I add 'Alameda County Library' account
    Then Account 'Alameda County Library' is present on Accounts screen