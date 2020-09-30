Feature: Book Transactions

  Background:
    Given Application is opened

  @logout @cancelHold
  Scenario: Hold
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | 2020's Hottest Books |
      And Change books visibility to show ALL
    Then Current category name is '2020\'s Hottest Books'
    When I open the book details for the subsequent RESERVE and save it as 'bookInfo'
      And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
    Then I check that opened book contains CANCEL button at book details screen

  Scenario: Download from Bookshelf list
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
    And DOWNLOAD book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen

  Scenario: Read from Bookshelf list
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
    Then Subcategory screen is present
    When DOWNLOAD book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
      And I Read book 'bookInfo'
    Then Book 'bookInfo' is present on screen

  @logout @cancelGet
  Scenario: Return from Bookshelf list
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | Fiction   |
      | Adventure |
      And GET book and save it as 'bookInfo'
      And Save current 'LYRASIS' library for CANCEL_GET books after test
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button RETURN
    Then I check that the action button text equal to the GET
    When I open Books
    Then Book 'bookInfo' is not present in Books List

  Scenario: Delete from Bookshelf list
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
      And DOWNLOAD book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
      And I delete book from book details screen
      And I open Books
    Then Book 'bookInfo' is not present in Books List

  @logout @cancelGet
  Scenario: Check out from Book Detail View
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | Nonfiction         |
      | Biography & Memoir |
    Then Current category name is 'Biography & Memoir'
    When I open the book details for the subsequent GET and save it as 'bookInfo'
      And Save current 'LYRASIS' library for CANCEL_GET books after test
    Then I check that opened book contains READ button at book details screen

  Scenario: Download from Book detail view
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction   |
      | Mysteries |
    Then Current category name is 'Mysteries'
    When I open the book details for the subsequent DOWNLOAD and save it as 'bookInfo'
    Then I check that opened book contains READ button at book details screen

  @logout @cancelGet
  Scenario: Return from Book Detail View
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | Nonfiction   |
      | Art & Design |
      | Art          |
    Then Current category name is 'Art'
    When I open the book details for the subsequent GET and save it as 'bookInfo'
      And Save current 'LYRASIS' library for CANCEL_GET books after test
    Then I check that opened book contains READ button at book details screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button RETURN
    Then I check that the action button text equal to the GET
    When I open Books
    Then Book 'bookInfo' is not present in Books List

  @logout @cancelHold
  Scenario: Remove a Reserved Book
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
    Then Books feed is loaded
    When I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open Catalog
    Then Books feed is loaded
    When I open search modal
    Then Search modal is opened
    When I set text to the search textBox 'Party of Two'
      And I click apply search button
    Then Search modal is closed
      And Search page is opened
    When RESERVE book and save it as 'bookInfo'
      And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
    Then Book saved as 'bookInfo' should contain CANCEL button at catalog books screen
    When I click on the book 'bookInfo' button CANCEL on catalog books screen
    Then Book saved as 'bookInfo' should contain RESERVE button at catalog books screen
    When I open the book details for the subsequent RESERVE and save it as 'bookInfo'
      And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
    Then I check that opened book contains CANCEL button at book details screen
    When Press on the book details screen at the action button CANCEL
    Then I check that the action button text equal to the RESERVE
    When Press on the book details screen at the action button RESERVE
      And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
    Then I check that opened book contains CANCEL button at book details screen
    When I open Holds
    Then Book 'bookInfo' is present in Holds List
    When I click on the book 'bookInfo' button CANCEL on the holds screen
    Then Book saved as 'bookInfo' should contain RESERVE button at the hold screen

  @logout @cancelHold @smoke @exclude_android
  Scenario: Cancel remove
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
    And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | 2020's Hottest Books |
    And Change books visibility to show ALL
    Then Current category name is '2020\'s Hottest Books'
    When I open the book details for the subsequent RESERVE and save it as 'bookInfo'
    And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
    Then I check that opened book contains CANCEL button at book details screen
    When I open Holds
    Then Book 'bookInfo' is present in Holds List
    When I click on the book 'bookInfo' button CANCEL on the holds screen and don't click on the popup button
    Then I click at the popup approve CANCEL the button CANCEL_POPUP
      And Book saved as 'bookInfo' should contain RESERVE button at the hold screen


