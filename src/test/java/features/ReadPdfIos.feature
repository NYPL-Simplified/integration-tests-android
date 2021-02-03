Feature: Read PDF IOS

  Background:
    Given Application is opened
    When I add 'LYRASIS' account
      And I enter credentials for 'LYRASIS' account
    Then Login is performed successfully
    When I open Catalog
      And I switch to 'LYRASIS' from side menu
      And I open search modal

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Open document
    When I search for 'Enterprise Pharo a Web Perspective'
      And I GET book by name 'Enterprise Pharo a Web Perspective' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
      And Pdf book page number is 1

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Navigate by page
    When I search for 'Fundamentals of Business'
      And I GET book by name 'Fundamentals of Business' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
      And Pdf book page number is 1
    When I go to next page in pdf book
    Then Pdf book page number is 2
    When I go to previous page in pdf book
    Then Pdf book page number is 1

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Navigate by Table of Contents Menu
    When I search for 'Communication Beginnings: An Introductory Listening and Speaking Text for English Language Learners'
      And I GET book by name 'Communication Beginnings: An Introductory Listening and Speaking Text for English Language Learners' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
      And Each chapter of pdf book can be opened from Table of Contents

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Open book to last page read
    When I search for 'Enterprise Pharo a Web Perspective'
      And I GET book by name 'Enterprise Pharo a Web Perspective' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
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
    When I search for 'Fundamentals of Business'
      And I GET book by name 'Fundamentals of Business' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
    When I open gallery menu
    Then Gallery is opened
      And I save count of books on gallery to 'countOfTheBookPages'
    When I scroll the gallery page DOWN
    Then Page has scrolled and count of books have changed 'countOfTheBookPages'
    When I open any page on the gallery screen
    Then Pdf book 'bookInfo' is present on screen
      And Pdf book page number is not 1

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Search document
    When I search for 'Communication Beginnings: An Introductory Listening and Speaking Text for English Language Learners'
      And I GET book by name 'Communication Beginnings: An Introductory Listening and Speaking Text for English Language Learners' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
    When I click the search in the pdf button
    Then The search in the pdf page opened
    When I am typing 'Ronelle Alexander' to the search field and apply search
    Then Found lines should contain 'Ronelle Alexander' in themselves

  @logout @cancelGet @tier1 @exclude_android
  Scenario: Navigate to Search Term
    When I search for 'Enterprise Pharo a Web Perspective'
      And I GET book by name 'Enterprise Pharo a Web Perspective' and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Pdf book 'bookInfo' is present on screen
    When I click the search in the pdf button
      And I am typing 'LiveDebugging' to the search field and apply search
      And I save page number as 'pageNumber' of the first item
      And I open the first found item
    Then Pdf page number 'pageNumber' is correct
