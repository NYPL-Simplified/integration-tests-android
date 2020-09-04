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
    When I open search modal
    Then Search modal is opened
    When I set text to the search textBox 'Men Without Men'
      And I click apply search button
    Then Search modal is closed
      And Search page is opened
    When I open first found book from the search result
    Then Book 'Men Without Men' is opened
      And The following values in the information block are present:
        | key         | value                           |
        | PUBLISHED   | 2018-05-31                      |
        | PUBLISHER   | Plympton                        |
        | DISTRIBUTOR | Plympton                        |
        | CATEGORIES  | Literary Fiction, Short Stories |
        | UPDATED     | 2018-05-31 14:22:22             |
      And Description has text
    """
    Eric's return from London breathes life back into the “two-fers” group; grabbing two-for-one draft beers at the Phoenix, a lower Manhattan gay bar, just hasn’t been the same without the man whose magnetic personality holds the friends together. But then Bendiks decides it’s time to play matchmaker, setting Eric up on a series of blind dates, and the group dynamic warps beyond narrator Buttons’ control.
    """


