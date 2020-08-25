Feature: Manage Libraries

  @logout
  Scenario: Add Library
    When I open application
      And I add 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is present on Accounts screen
    When I enter credentials for 'Hartford Public Library' account
    Then Text on Login button is changed to Log out on Account screen

