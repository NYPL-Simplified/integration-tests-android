Feature: AddLibrary

  Background:
    Given Application is opened

  Scenario: Add Library
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
