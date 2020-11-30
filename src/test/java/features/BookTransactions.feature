Feature: Book Transactions

  Background:
    Given Application is opened

  @logout @cancelHold @tier2
  Scenario: Hold
    When I add 'The New York Public Library' account
      And I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
      And I open category by chain:
        | 2020's Hottest Books |
      And Change books visibility to show ALL
      And I open the book details for the subsequent RESERVE and save it as 'bookInfo'
    Then I check that opened book contains CANCEL button at book details screen
    When I open Holds
    Then Book 'bookInfo' is present in Holds List

  @tier2
  Scenario: Download from Bookshelf list
    When I open Catalog
      And I open category by chain:
        | Fiction |
        | Drama   |
      And DOWNLOAD book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen

  @tier2
  Scenario: Read from Bookshelf list
    When I open Catalog
      And I open category by chain:
        | Fiction |
        | Drama   |
      And DOWNLOAD book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
      And I Read book 'bookInfo'
    Then Book 'bookInfo' is present on screen

  @logout @cancelGet @tier2
  Scenario: Return from Bookshelf list
    When I add 'LYRASIS' account
      And I enter credentials for 'LYRASIS' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
      And I open category by chain:
        | Fiction   |
        | Adventure |
      And GET book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button RETURN
    Then I check that the action button text equal to the GET
    When I open Books
    Then Book 'bookInfo' is not present in Books List

  @tier2
  Scenario: Delete from Bookshelf list
    When I open Catalog
      And I open category by chain:
        | Fiction |
        | Drama   |
      And DOWNLOAD book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open Books
      And I open book 'bookInfo' details by clicking on cover
      And I delete book from book details screen
      And I open Books
    Then Book 'bookInfo' is not present in Books List

  @logout @cancelGet @tier2 @exclude_android
  Scenario: Check out from Book Detail View
    When I add 'LYRASIS' account
      And I enter credentials for 'LYRASIS' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
      And I open category by chain:
        | Nonfiction         |
        | Biography & Memoir |
    When I open the book details for the subsequent GET and save it as 'bookInfo'
    Then I check that opened book contains READ button at book details screen

  @logout @cancelGet @tier2 @logout @exclude_ios
  Scenario Outline: Check out from Book Detail View (feed)
    When I add custom '<feedName>' opds feed
      And I open <bookType> book from '<laneName>' lane and save book info as 'bookInfo'
      And Get book on the book details screen
    Then Opened book contains read button at book details screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
    Then Opened book contains read button at book details screen
    When I read <bookType> book
    Then Reader screen for <bookType> type book 'bookInfo' is present

    Scenarios:
      | feedName                            | laneName                                      | bookType  |
      | New York Public Library - QA Server | Axis 360                                      | AUDIOBOOK |
      | New York Public Library - QA Server | Axis 360                                      | EBOOK     |
      | New York Public Library - QA Server | Plympton                                      | EBOOK     |
      | New York Public Library - QA Server | Plympton                                      | AUDIOBOOK |
      | New York Public Library - QA Server | Bibliotheca                                   | EBOOK     |
      | New York Public Library - QA Server | Bibliotheca                                   | AUDIOBOOK |
      | New York Public Library - QA Server | Library Simplified Open Access Content Server | EBOOK     |
      | New York Public Library - QA Server | Library Simplified Open Access Content Server | AUDIOBOOK |
      | New York Public Library - QA Server | Overdrive                                     | EBOOK     |
      | New York Public Library - QA Server | Overdrive                                     | AUDIOBOOK |

  @tier2
  Scenario: Download from Book detail view
    When I open Catalog
      And I open category by chain:
        | Fiction   |
        | Mysteries |
    When I open the book details for the subsequent DOWNLOAD and save it as 'bookInfo'
    Then I check that opened book contains READ button at book details screen

  @logout @cancelGet @tier2 @exclude_android
  Scenario: Return from Book Detail View
    When I add 'LYRASIS' account
      And I enter credentials for 'LYRASIS' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
      And I open category by chain:
        | Nonfiction   |
        | Art & Design |
        | Art          |
      And I open the book details for the subsequent GET and save it as 'bookInfo'
    Then I check that opened book contains READ button at book details screen
    When I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button RETURN
    Then I check that the action button text equal to the GET
    When I open Books
    Then Book 'bookInfo' is not present in Books List

  @logout @cancelGet @tier2 @exclude_ios
  Scenario Outline: Return from Book Detail View (feed)
    When I add custom '<feedName>' opds feed
      And I open <bookType> book from '<laneName>' lane and save book info as 'bookInfo'
      And Get book on the book details screen
    Then Opened book contains read button at book details screen
    When I open Books
      And I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button RETURN
    Then I check that the action button text equal to the GET
    When I open Books
    Then Book 'bookInfo' is not present in Books List

    Scenarios:
      | feedName                            | laneName                                      | bookType  |
      | New York Public Library - QA Server | Axis 360                                      | AUDIOBOOK |
      | New York Public Library - QA Server | Axis 360                                      | EBOOK     |
      | New York Public Library - QA Server | Plympton                                      | EBOOK     |
      | New York Public Library - QA Server | Plympton                                      | AUDIOBOOK |
      | New York Public Library - QA Server | Bibliotheca                                   | EBOOK     |
      | New York Public Library - QA Server | Bibliotheca                                   | AUDIOBOOK |
      | New York Public Library - QA Server | Library Simplified Open Access Content Server | EBOOK     |
      | New York Public Library - QA Server | Library Simplified Open Access Content Server | AUDIOBOOK |
      | New York Public Library - QA Server | Overdrive                                     | EBOOK     |
      | New York Public Library - QA Server | Overdrive                                     | AUDIOBOOK |

  @logout @cancelHold @tier2
  Scenario: Remove a Reserved Book
    When I add 'The New York Public Library' account
      And I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
      And I open Catalog
      And I open search modal
      And I set text to the search textBox 'Party of Two'
      And I click apply search button
    Then Search modal is closed
      And Search page is opened
    When RESERVE book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain CANCEL button at catalog books screen
    When I click on the book 'bookInfo' button CANCEL on catalog books screen
    Then Book saved as 'bookInfo' should contain RESERVE button at catalog books screen
    When I open the book details for the subsequent RESERVE and save it as 'bookInfo'
    Then I check that opened book contains CANCEL button at book details screen
    When Press on the book details screen at the action button CANCEL
    Then I check that the action button text equal to the RESERVE
    When Press on the book details screen at the action button RESERVE
    Then I check that opened book contains CANCEL button at book details screen
    When I open Holds
    Then Book 'bookInfo' is present in Holds List
    When I click on the book 'bookInfo' button CANCEL on the holds screen
    Then Book saved as 'bookInfo' should contain RESERVE button at the hold screen

  @logout @cancelHold @tier3 @exclude_android
  Scenario: Cancel remove
    When I add 'The New York Public Library' account
      And I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
      And I open category by chain:
        | 2020's Hottest Books |
      And Change books visibility to show ALL
      And I open the book details for the subsequent RESERVE and save it as 'bookInfo'
    Then I check that opened book contains CANCEL button at book details screen
    When I open Holds
    Then Book 'bookInfo' is present in Holds List
    When I click on the book 'bookInfo' button CANCEL on the holds screen and don't click on the popup button
    Then I click at the popup approve CANCEL the button CANCEL_POPUP
      And Book saved as 'bookInfo' should contain RESERVE button at the hold screen

  @logout @cancelHold @tier2
  Scenario: Reserve Book (hold)
    When I add 'The New York Public Library' account
      And I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
      And I open category by chain:
        | 2020's Hottest Books |
      And Change books visibility to show ALL
      And RESERVE book and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain CANCEL button at catalog books screen
