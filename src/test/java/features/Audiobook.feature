Feature: Audiobook

  Background:
    Given Application is opened
    When I add custom 'New York Public Library - QA Server' opds feed
      And I open first AUDIOBOOK book and save book info as 'bookInfo'
      And Get book on the book details screen
    Then I check that opened book contains LISTEN button at book details screen

  @logout @cancelGet @tier2 @exclude_ios
  Scenario: Navigate by Table of Contents Menu
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select the chapter not equal to remembered 'defaultChapter' and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'

  @logout @cancelGet @tier2 @exclude_ios
  Scenario: Return to Chapter (Bookmarking/Syncing)
    When Press on the book details screen at the action button LISTEN
      And Remember current book chapter in 'defaultChapter'
      And Open the menu-based position in the audiobook
    Then I check that chapters are visible
      And Wait and check that all loaders are disappeared
    When I select the chapter not equal to remembered 'defaultChapter' and remember selected chapter as 'newChapter'
    Then I check that current chapter equal to remembered 'newChapter'
    When I restart app
      And I open Catalog
      And I switch to 'New York Public Library - QA Server' from side menu
      And I open Books
      And I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button LISTEN
    Then I check that current chapter equal to remembered 'newChapter'

  @logout @cancelGet @tier2 @exclude_ios
  Scenario: Play Audiobook
    When Press on the book details screen at the action button LISTEN
      And I click play button on player screen
    Then Pause button is present
      And Book is playing
    When I click pause button on player screen
    Then Play button is present
      And Book is not playing

  @logout @cancelGet @tier2 @exclude_ios
  Scenario: Navigate Audiobook
    When Press on the book details screen at the action button LISTEN
      And I click play button on player screen
    Then Pause button is present
      And Book is playing
    When I save book play time as 'timeAhead'
      And I skip ahead 15 seconds
    Then Playback 'timeAhead' moves forward by 15 seconds increment
    When I save book play time as 'timeBehind'
      And I skip behind 15 seconds
    Then Playback 'timeBehind' moves behind by 15 seconds increment
    When I move to middle part of chapter
    Then Play time is close to middle part of chapter
