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
    Then Books feed is loaded
    When I get names of books on screen and save them as 'nameOfBooks'
      And I switch to 'Hartford Public Library' from side menu
    Then Books feed is loaded
      And List of books on screen is not equal to list of books saved as 'nameOfBooks'

  Scenario: Remove library
    When I remove 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is not present on Accounts screen

  Scenario: Switch library bookshelf
    When I open Catalog
      And I switch to 'The SimplyE Collection' from side menu
      And I get first book from shelf and save it as 'bookInfo'
      And I open Books
    Then Book 'bookInfo' is present in Books List
    When I open Catalog
      And I return to previous page
      And I switch to 'Hartford Public Library' from side menu
      And I open Books
    Then No books are present in Books list
