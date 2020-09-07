Feature: Catalog Navigation

  #Background:
    #Given Application is opened

  Scenario: Return to last library catalog
    Given Application is opened
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

  Scenario: Sort Lists
    When I add 'Alameda County Library' account
    Then Account 'Alameda County Library' is present on Accounts screen
    When I open Catalog
    Then Books feed is loaded
    When I switch to 'Alameda County Library' from side menu
    Then Books feed is loaded
    When I switch to 'Audiobooks' catalog tab
    Then Books feed is loaded
      And All present books are audiobooks
    When I switch to 'eBooks' catalog tab
      And I open 'Fiction' category
      And I open 'Drama' subcategory
    Then Subcategory screen is present
    When I sort books by 'Author'
    Then Subcategory screen is present
      And Books are sorted by Author ascending
    When I sort books by 'Title'
    Then Subcategory screen is present
      And Books are sorted by Title ascending
    When I save list of books as 'listOfBooks'
      And I sort books by 'Recently Added'
    Then Subcategory screen is present
      And List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'
    When I save list of books as 'recentlyAddedListOfBooks'
      And I sort books by 'Random'
    Then List of books on subcategory screen is not equal to list of books saved as 'recentlyAddedListOfBooks'
    When I save list of books as 'randomListOfBooks'
      And I select book by Availability - 'Available now'
    Then All books can be loaned or downloaded
    When I select book by Availability - 'All'
    Then Subcategory screen is present
    When I select book by Availability - 'Yours to keep'
    Then All books can be downloaded
