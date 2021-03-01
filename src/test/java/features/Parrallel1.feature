Feature: Parallel1

  Background:
    Given Application is opened
    When I open Catalog

  @wait1
  Scenario: Wait1
    When I wait for 1 seconds

  @tier1 @wait1
  Scenario: Change, View Font and Contrast Settings
    And I open search modal
    And I search for 'Flower Fables'
    And DOWNLOAD book and save it as 'bookInfo'
    And I open book 'bookInfo' details by clicking on cover
    And Press on the book details screen at the action button READ
    Then Book 'bookInfo' is present on screen
    When I save font size as 'fontSize'
    And I INCREASE_FONT of text
    Then Font size 'fontSize' is increased
    When I save font size as 'fontSize'
    And I DECREASE_FONT of text
    Then Font size 'fontSize' is decreased
    When I change font style to SERIF
    Then Book text displays in SERIF font
    When I change font style to SANS_SERIF_ARIAL
    Then Book text displays in SANS_SERIF_ARIAL font
    When I change font style to ALTERNATIVE_SANS
    Then Book text displays in ALTERNATIVE_SANS font
    When I change contrast to WHITE_TEXT_ON_BLACK
    Then Book text displays WHITE on BLACK
    When I change contrast to BLACK_TEXT_ON_WHITE
    Then Book text displays BLACK on WHITE
    When I change contrast to BLACK_TEXT_ON_SEPIA
    Then Book text displays BLACK on SEPIA