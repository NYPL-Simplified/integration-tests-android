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

  Scenario: Sort Lists
    When I add 'Hartford Public Library' account
    Then Account 'Hartford Public Library' is present on Accounts screen
    When I open Catalog
    Then Books feed is loaded
    When I switch to 'Hartford Public Library' from side menu
    Then Books feed is loaded
    When I switch to 'Audiobooks' catalog tab
    Then All present books are audiobooks
    When I switch to 'eBooks' catalog tab
    Then All present books are ebooks
    When I open 'Fiction' category
      And I open 'Drama' subcategory
      And I sort books by 'Author'
    Then Books are sorted by Author ascending
    When I sort books by 'Title'
    Then Books are sorted by Title ascending
    When I save list of books as 'listOfBooks'
      And I sort books by 'Recently Added'
    Then List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'
    When I save list of books as 'recentlyAddedListOfBooks'
      And I sort books by 'Random'
    Then List of books on subcategory screen is not equal to list of books saved as 'recentlyAddedListOfBooks'
    When I save list of books as 'randomListOfBooks'
      And I select book by Availability - 'Available now'
    Then All books can be loaned or downloaded
    When I select book by Availability - 'All'
    Then List of books on subcategory screen is equal to list of books saved as 'randomListOfBooks'
    When I select book by Availability - 'Yours to keep'
    Then All books can be downloaded
