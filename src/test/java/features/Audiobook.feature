Feature: Audiobook

  Background:
    Given Application is opened
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
    And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I switch to 'Audiobooks' catalog tab
    Then Books feed is loaded
    When I open category by chain:
      | 125 Books We Love For Teens |
    Then Current category name is '125 Books We Love For Teens'
    When I open the book details for the subsequent GET and save it as 'bookInfo'
    And Save current 'The New York Public Library' library for CANCEL_GET books after test
    Then I check that opened book contains LISTEN button at book details screen

  @logout @cancelGet
  Scenario: Navigate by Table of Contents Menu
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select the chapter not equal to remembered 'defaultChapter' and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'

  @logout @cancelGet
  Scenario: Return to Chapter (Bookmarking/Syncing)
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select the chapter not equal to remembered 'defaultChapter' and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'
    When I restart app
    Then I check that current chapter equal to remembered 'newChapter'
