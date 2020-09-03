Feature: Catalog Navigation

  Background:
    Given Application is opened

  Scenario: Return to last library catalog
    When I add 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is present on Accounts screen
    When I open Catalog
    Then Books feed is loaded
    When I switch to 'Hartford Public Library' from side menu
    Then Books feed is loaded
    When I restart app
    Then Books feed is loaded
      And Current library is 'Hartford Public Library' in Catalog

  Scenario: Navigate Lists
    When I open Catalog
    Then Books feed is loaded
    When I get names of books on screen and save them as 'listOfBooksOnMainPage'
      And I open 'Nonfiction' category
    Then Current category name is 'Nonfiction'
      And Following subcategories are present:
        | History                 |
        | Philosophy              |
        | Science & Technology    |
        | Religion & Spirituality |
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When I return to previous screen
      And I open 'Fiction' category
    Then Current category name is 'Fiction'
      And Following subcategories are present:
        | Literary Fiction |
        | Science Fiction  |
        | Mysteries        |
        | Romance          |
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When I open 'Drama' subcategory
    Then Subcategory screen is present
      And Subcategory name is 'Drama'

  Scenario: Browse Lanes/Categories
    When I open Catalog
    Then Books feed is loaded
      And Count of books in first lane is up to 12
    When I open 'Fiction' category
    Then Current category name is 'Fiction'
      And Following subcategories are present:
        | Literary Fiction   |
        | Science Fiction    |
        | Mysteries          |
        | Romance            |
        | Historical Fiction |
        | Horror             |
        | Poetry             |
        | Drama              |
        | All Fiction        |
    When I open 'Drama' subcategory
    Then Subcategory screen is present
      And Subcategory name is 'Drama'
    When I open first book in subcategory list and save it as 'bookInfo'
    Then Book 'bookInfo' is opened

  Scenario: View Book Details
    When I open Catalog
    Then Books feed is loaded
    When I open 'Fiction' category
    Then Current category name is 'Fiction'
    When I open 'Literary Fiction' subcategory
    Then Subcategory screen is present
      And Subcategory name is 'Literary Fiction'
    When I open first book in subcategory list and save it as 'bookInfo'
    Then Book 'bookInfo' is opened
      And The following values in the information block are present:
        | key         | value                               |
        | PUBLISHED   | 2018-04-25                          |
        | PUBLISHER   | Ember: A Journal of Luminous Things |
        | DISTRIBUTOR | Plympton                            |
        | CATEGORIES  | Short Stories, Fantasy, Horror      |
        | UPDATED     | 2019-11-11 00:26:52                 |
      And Description has text
    """Mohammad Hussain Mohammadi's "Dasht-e Leili" is a horrifying fictional recreation of the Dasht-e Leili massacre.
    In December 2001, between several hundred and several thousand Taliban prisoners were shot, or suffocated to death
    in metal shipping containers, then buried in a mass grave. Narrated by one of the victims as he struggles to
    survive, Mohammadi's story is a gut-wrenching, vivid depiction of an oft-overlooked atrocity."""

