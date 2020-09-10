Feature: Book Transactions

  Background:
    Given Application is opened

  @logout @cancelHold
  Scenario: Hold
    When I add 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is present on Accounts screen
    When I enter credentials for 'Hartford Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'Hartford Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
      And Reserve book save for cancel reservation and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain CANCEL button at catalog books screen
    When I click on the book 'bookInfo' button CANCEL on catalog books screen
    Then Book saved as 'bookInfo' should contain RESERVE button at catalog books screen
    When I open the book details for the subsequent reservation and save it as 'bookInfo'
    Then I check that opened book contains CANCEL button at book details screen

  Scenario: Download from Bookshelf list
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
    And I Download book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen

  Scenario: Delete from Bookshelf list
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
    And I Download book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
    And I delete book from book details screen
    And I open Books
    Then Book 'bookInfo' is not present in Books List

  @logout @cancelGet
  Scenario: Check out
    When I add 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is present on Accounts screen
    When I enter credentials for 'Hartford Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
    And I switch to 'Hartford Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
      And I GET book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain REMOVE button at catalog books screen
    When I click on the book 'bookInfo' button REMOVE on catalog books screen
    Then Book saved as 'bookInfo' should contain GET button at catalog books screen
    When I open the book details for the subsequent GET and save it as 'bookInfo'
    Then I check that opened book contains REMOVE button at book details screen

  Scenario: Download from Book detail view
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
    When I open the book details for the subsequent DOWNLOAD and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at book detail view