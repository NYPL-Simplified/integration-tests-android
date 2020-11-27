Feature: Audiobook

  Background:
    Given Application is opened
    When I add custom 'New York Public Library - QA Server' opds feed
    Then Books feed is loaded
    When I open first AUDIOBOOK book and save book info as 'bookInfo'
      And Get book on the book details screen
    Then I check that opened book contains LISTEN button at book details screen

  @logout @cancelGet @tier2
  Scenario: Navigate by Table of Contents Menu
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select the chapter not equal to remembered 'defaultChapter' and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'

  @logout @cancelGet @tier2
  Scenario: Return to Chapter (Bookmarking/Syncing)
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select the chapter not equal to remembered 'defaultChapter' and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'
    When I restart app
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
      And I open Books
      And I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button LISTEN
    Then I check that current chapter equal to remembered 'newChapter'
