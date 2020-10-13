Feature: Read PDF

  Background:
    Given Application is opened
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
    Then Books feed is loaded
    When I open search modal
    Then Search modal is opened
    When I set text to the search textBox 'PDF'
      And I click apply search button
    Then Search modal is closed
      And Search page is opened
    When DOWNLOAD book of 'PDF' type and save it as 'bookInfo'
      And Save current 'LYRASIS' library for CANCEL_GET books after test
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen

  @logout @cancelGet @tier1 @exclude_ios
  Scenario: Open document
      And Pdf book page number is 1

  @logout @cancelGet @tier1 @exclude_ios
  Scenario: Navigate by page
      And Pdf book page number is 1
    When I go to next page in pdf book
    Then Pdf book page number is 2
    When I go to previous page in pdf book
    Then Pdf book page number is 1

  @logout @cancelGet @tier1 @exclude_ios
  Scenario: Navigate by Table of Contents Menu
      And Each chapter of pdf book can be opened from Table of Contents

  @logout @cancelGet @tier1 @exclude_ios
  Scenario: Open book to last page read
    When I scroll pdf page forward from 10 to 20 times
      And I save pdf page number as 'pageNumber'
      And I return to previous screen
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
      And Pdf page number 'pageNumber' is correct
    When I restart app
      And I open Books
    Then Book 'bookInfo' is present in Books List
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
      And Pdf page number 'pageNumber' is correct

  @logout @cancelGet @tier1 @exclude_ios
  Scenario: Close book
      And I return to previous screen
    Then I check that opened book contains READ button at book details screen

  @logout @cancelGet @tier1 @exclude_ios
  Scenario: Navigate by Page slider
    When I save pdf page number as 'pageNumber'
      And Slide page slider RIGHT
    Then Pdf saved page number 'pageNumber' should not be equal to current
    When I save pdf page number as 'pageNumber'
      And Slide page slider LEFT
    Then Pdf saved page number 'pageNumber' should not be equal to current
