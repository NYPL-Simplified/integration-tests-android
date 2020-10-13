Feature: Read PDF IOS

  Background:
    Given Application is opened
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
#    When I open search modal
#    Then Search modal is opened
#    When I set text to the search textBox 'Bosnian, Croatian, Serbian, a Grammar'
#      And I click apply search button
#    Then Search modal is closed
#      And Search page is opened
#    When I GET book by name 'Bosnian, Croatian, Serbian, a Grammar' and save it as 'bookInfo'
#      And Save current 'The New York Public Library' library for CANCEL_GET books after test
#    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
#    When I open book 'bookInfo' details by clicking on cover
#      And Press on the book details screen at the action button READ
    When I open Books
    Then Book 'The Tree of Life, Book Two' is present in Books List
    When I open book 'The Tree of Life, Book Two' details by clicking on cover
    And Press on the book details screen at the action button DOWNLOAD
    And Press on the book details screen at the action button READ

    Then Pdf book 'bookInfo' is present on screen

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Open document
      And Pdf book page number is 1

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Navigate by page
      And Pdf book page number is 1
    When I go to next page in pdf book
    Then Pdf book page number is 2
    When I go to previous page in pdf book
    Then Pdf book page number is 1

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Navigate by Table of Contents Menu
      And Each chapter of pdf book can be opened from Table of Contents

  @logout @cancelGet @tier1 @exclude_android
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

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Navigate by Gallery
    When I open gallery menu
    Then Gallery is opened
      And I save count of books on gallery to 'countOfTheBookPages'
    When I scroll the gallery page DOWN
    Then Page has scrolled and count of books have changed 'countOfTheBookPages'
    When I open any page on the gallery screen
    Then Pdf book 'bookInfo' is present on screen
      And Pdf book page number is not 1


