Feature: Audiobook

  Background:
    Given Application is opened
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Login is performed successfully
    When I open Catalog
    And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I switch to 'Audiobooks' catalog tab
    Then Books feed is loaded
    When I open category by chain:
      | 125 NYC Books We Love |
    Then Current category name is '125 NYC Books We Love'
    When I open the book details for the subsequent GET and save it as 'bookInfo'
    Then I check that opened book contains LISTEN button at book details screen

  @logout @cancelGet @tier2 @exclude_android
  Scenario: Navigate by Table of Contents Menu
    When Press on the book details screen at the action button LISTEN
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select 2 chapter and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'

  @logout @cancelGet @tier2 @exclude_android
  Scenario: Return to Chapter (Bookmarking/Syncing)
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select 2 chapter and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'
    When I restart app
      And I open Catalog
      And I switch to 'The New York Public Library' from side menu
      And I open Books
      And I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button LISTEN
    Then I check that current chapter equal to remembered 'newChapter'

  @logout @cancelGet @tier2 @exclude_android
  Scenario: Play Audiobook
    When Press on the book details screen at the action button LISTEN
      And I click play button on player screen
    Then Pause button is present
      And Book is playing
    When I click pause button on player screen
    Then Play button is present
      And Book is not playing