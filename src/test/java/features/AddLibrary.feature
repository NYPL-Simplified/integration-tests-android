Feature: AddLibrary

  Background:
    Given Application is opened

  @tier1
  Scenario: Add Library
    When I add 'Alameda County Library' account
    Then Account 'Alameda County Library' is present on Accounts screen
