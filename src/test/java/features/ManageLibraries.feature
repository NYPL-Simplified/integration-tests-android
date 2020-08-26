Feature: Manage Libraries

  Background:
    When I open application
    And I add 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is present on Accounts screen

  @logout
  Scenario: Add Library
    When I enter credentials for 'Hartford Public Library' account
    Then Text on Login button is changed to Log out on Account screen

  Scenario: Switch Library Catalogs
    When I open Catalog
      And I approve that User is 13 or older
    Then Books feed is loaded
    When I get names of books in first lane and save them as 'nameOfBooks'
      And I open 'Hartford Public Library' from side menu
    Then Books feed is loaded
      And List of books is not equal to list of books saved as 'nameOfBooks'
