Feature: Manage Libraries

  @logout
  Scenario: Add Library
    When I accept EULA agreement
      And I select Add a Library Later button on Main screen
      And I open Settings from Menu pane
      And I open Accounts on Settings screen
      And I click Add button on Accounts screen
      And I select 'Hartford Public Library' on Add Account screen
    Then Account 'Hartford Public Library' is present on Accounts screen
    When I click 'Hartford Public Library' account on Accounts screen
      And I enter credentials for ebook user on Account screen
    Then Text on Login button is changed to Log out on Account screen

