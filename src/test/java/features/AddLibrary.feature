Feature: AddLibrary

  Background:
    Given Application is opened

  @tier1 @smoke
  Scenario: Add Library
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
