Feature: Read EPUB

  Scenario: Navigate by Page
    Given Application is opened
    When I open Catalog
    Then Books feed is loaded
    When I open category by chain:
      | Fiction |
      | Drama   |
      And DOWNLOAD book of 'eBook' type and save it as 'bookInfo'
    Then Book saved as 'bookInfo' should contain READ button at catalog books screen
    When I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button READ
    Then Book 'bookInfo' is present on screen
      And Book page number is 1
    When I save page info as 'pageInfo'
      And I click on right book corner
    Then Book page number is bigger then previous 'pageInfo'
    When I save page info as 'pageInfo'
      And I swipe from right to left book corner
    Then Book page number is bigger then previous 'pageInfo'
    When I save page info as 'pageInfo'
      And I click on left book corner
    Then Book page number is smaller then previous 'pageInfo'
    When I save page info as 'pageInfo'
      And I swipe from left to right book corner
    Then Book page number is 1