Feature: Read PDF
  @logout @cancelGet
  Scenario: Open document
    Given Application is opened
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Text on Login button is changed to Log out on Account screen
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
      And Pdf book page number is 1

  @logout @cancelGet
  Scenario: Navigate by page
    Given Application is opened
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Text on Login button is changed to Log out on Account screen
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
      And Pdf book page number is 1
    When I go to next page in pdf book
    Then Pdf book page number is 2
    When I go to previous page in pdf book
    Then Pdf book page number is 1

  @logout @cancelGet
  Scenario: Navigate by Table of Contents Menu
    Given Application is opened
    When I add 'LYRASIS' account
    Then Account 'LYRASIS' is present on Accounts screen
    When I enter credentials for 'LYRASIS' account
    Then Text on Login button is changed to Log out on Account screen
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
      And Each chapter of pdf book can be opened from Table of Contents
