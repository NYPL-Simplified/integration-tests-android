Feature: Store library card

  Background:
    Given Application is opened

  @logout @tier2
  Scenario: Store library card
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account via keyboard
    Then Login is performed successfully
    When I open account 'LYRASIS'
    When I click the log out button on the account screen
    Then Text on Logout button is changed to Log in on Account screen
