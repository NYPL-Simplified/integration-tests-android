Feature: Manage Libraries

  Background:
    Given Application is opened
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen

  @tier1
  Scenario: Switch Library Catalogs
    Given Catalog is opened
    Then Books feed is loaded
    When I get names of books on screen and save them as 'nameOfBooks'
      And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
      And List of books on screen is not equal to list of books saved as 'nameOfBooks'

  @tier1
  Scenario: Remove library
    When I remove 'The New York Public Library' account
    Then Account 'The New York Public Library' is not present on Accounts screen

  @tier2
  Scenario: Switch library bookshelf
    Given Catalog is opened
    When I switch to 'The SimplyE Collection' from side menu
    When I open category by chain:
      | Fiction |
      | Drama   |
      And DOWNLOAD book and save it as 'bookInfo'
      And I open Books
    Then Book 'bookInfo' is present in Books List
    When I open Catalog
      And I return to previous screen
      And I switch to 'The New York Public Library' from side menu
      And I open Books
    Then No books are present in Books list

  @logout @cancelHold @tier2
  Scenario: Switch Library Reservations
    When I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | 2020's Hottest Books |
      And Change books visibility to show ALL
      And I open the book details for the subsequent RESERVE and save it as 'bookInfo'
      And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
      And Open Holds
    Then Holds feed is loaded
      And Book 'bookInfo' is present in Holds List
    When I open Catalog
      And I open Catalog
      And I switch to 'The SimplyE Collection' from side menu
      And Open Holds
    Then Holds feed is loaded
      And No books are present in Holds list
