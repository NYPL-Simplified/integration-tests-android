Feature: Manage Libraries

  Background:
    When I accept EULA agreement
      And I select Add a Library Later button on Main screen
      And I open Settings from Menu pane
      And I open Accounts on Settings screen
      And I click Add button on Accounts screen
      And I select 'Hartford Public Library' on Add Account screen
    Then Account 'Hartford Public Library' is present on Accounts screen

  @logout
  Scenario: Add Library
    When I click 'Hartford Public Library' account on Accounts screen
      And I enter credentials for ebook user on Account screen
    Then Text on Login button is changed to Log out on Account screen

  Scenario: Switch Library Catalogs
    When I open Catalog
      And I approve that User is 13 or older
    Then Books feed is loaded
    When I get names of books in first lane and save them as 'nameOfBooks'
      And I open 'Hartford Public Library' from side menu
    Then Books feed is loaded
      And List of books is not equal to list of books saved as 'nameOfBooks'
