Feature: Read EPUB

  Background:
    Given Application is opened

  Scenario: Navigate by Page
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

  Scenario: Navigate by Table of Contents Menu
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
      And Each chapter can be opened from Table of Contents

  Scenario: Navigate View options
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
    When I return to previous screen
      And Press on the book details screen at the action button READ
    Then Book 'bookInfo' is present on screen
    When I open font choices for book
    Then Font choices screen is present
    When I return to previous screen
      And I open Table of Contents
    Then Table of Contents is opened

  Scenario: Change, View Font and Contrast Settings
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
    When I save font size as 'fontSize'
      And I increase text font size
    Then Font size 'fontSize' is increased
    When I save font size as 'fontSize'
      And I decrease text font size
    Then Font size 'fontSize' is decreased
    When I change font style to serif
    Then Book text displays in 'serif !important' font
    When I change font style to sans-serif arial
    Then Book text displays in 'sans-serif !important' font
    When I change font style to alternative sans
    Then Book text displays in 'OpenDyslexic3 !important' font
    When I change contrast to white text on black
    Then Book text displays white-text on black
    When I change contrast to black text on white
    Then Book text displays black-text on white
    When I change contrast to black text on sepia
    Then Book text displays black-text on sepia
