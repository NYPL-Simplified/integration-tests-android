Feature: Catalog Navigation

  Background:
    Given Application is opened

  @tier1
  Scenario: Return to last library catalog
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I open Catalog
    Then Books feed is loaded
    When I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I restart app
    Then Books feed is loaded
      And Current library is 'The New York Public Library' in Catalog

  @tier1
  Scenario: Navigate Lists
    When I open Catalog
    Then Books feed is loaded
    When I get names of books on screen and save them as 'listOfBooksOnMainPage'
      And I open 'Nonfiction' category
    Then Current category name is 'Nonfiction'
      And Books feed is loaded
      And Following subcategories are present:
        | History                 |
        | Philosophy              |
        | Science & Technology    |
        | Religion & Spirituality |
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When I return to previous screen
      And I open 'Fiction' category
    Then Current category name is 'Fiction'
      And Books feed is loaded
      And Following subcategories are present:
        | Literary Fiction |
        | Science Fiction  |
        | Mysteries        |
        | Romance          |
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When I open 'Drama' subcategory
    Then Subcategory screen is present
      And Subcategory name is 'Drama'

  @tier1
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

  @tier1
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
    Then Books feed is loaded
    When I open category by chain:
        | Fiction |
        | Drama   |
    Then Subcategory screen is present
    When I sort books by AUTHOR
    Then Subcategory screen is present
      And Books are sorted by Author ascending
    When I sort books by TITLE
    Then Subcategory screen is present
      And Books are sorted by Title ascending
    When I save list of books as 'listOfBooks'
      And I sort books by RECENTLY_ADDED
    Then Subcategory screen is present
      And List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'
    When I save list of books as 'recentlyAddedListOfBooks'
      And I sort books by RANDOM
    Then List of books on subcategory screen is not equal to list of books saved as 'recentlyAddedListOfBooks'
    When I save list of books as 'randomListOfBooks'
      And Change books visibility to show AVAILABLE_NOW
    Then All books can be loaned or downloaded
    When I change books visibility to show ALL
    Then Subcategory screen is present
    When I change books visibility to show YOURS_TO_KEEP
    Then All books can be downloaded

  @tier1
  Scenario: View Book Details
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I open Catalog
    Then Books feed is loaded
    When I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open Catalog
    Then Books feed is loaded
    When I open search modal
    Then Search modal is opened
    When I set text to the search textBox 'Harry Potter and the Order of the Phoenix'
      And I click apply search button
    Then Search modal is closed
      And Search page is opened
    When I switch to 'eBooks' catalog tab
    Then Subcategory screen is present
    When I open book with name 'Harry Potter and the Order of the Phoenix' and save it as 'bookInfo'
    Then Book 'bookInfo' is opened
      And The following values in the information block are present:
        | key         | value                 |
        | PUBLISHED   | 2015-12-08            |
        | PUBLISHER   | Pottermore Publishing |
        | DISTRIBUTOR | Overdrive             |
        | CATEGORIES  | Fantasy               |
        | UPDATED     | 2020-09-22 11:15:16   |
      And Description has text
    """
    "'You are sharing the Dark Lord's thoughts and emotions. The Headmaster thinks it inadvisable for this to continue. He wishes me to teach you how to close your mind to the Dark Lord.'"
Dark times have come to Hogwarts. After the Dementors' attack on his cousin Dudley, Harry Potter knows that Voldemort will stop at nothing to find him. There are many who deny the Dark Lord's return, but Harry is not alone: a secret order gathers at Grimmauld Place to fight against the Dark forces. Harry must allow Professor Snape to teach him how to protect himself from Voldemort's savage assaults on his mind. But they are growing stronger by the day and Harry is running out of time...
    """
    When I open related books
    Then Current category name is 'Related Books'
      And Count of books in subcategory 'J. K. Rowling' lane is up to 12
    When I open 'J. K. Rowling' subcategory
    Then Current category name is 'J. K. Rowling'
      And Count of books in search result is up to 12
    When I return to previous screen
    Then Current category name is 'Related Books'
      And Count of books in subcategory 'Similar titles recommended by NoveList' lane is up to 12
    When I open 'Similar titles recommended by NoveList' subcategory
    Then Current category name by localization is TITLE_RECOMMENDED_BY_NOVELIST
      And Count of books in search result is up to 12
    When I return to previous screen
    Then Current category name is 'Related Books'
      And Count of books in subcategory 'Harry Potter' lane is up to 12
    When I open 'Harry Potter' subcategory
    Then Current category name is 'Harry Potter'
      And Count of books in search result is up to 12

  @logout @cancelHold @tier2
  Scenario: Reserve Book (hold)
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | 2020's Hottest Books |
      And Change books visibility to show ALL
      And RESERVE book and save it as 'bookInfo'
      And Save current 'The New York Public Library' library for CANCEL_HOLD books after test
    Then Book saved as 'bookInfo' should contain CANCEL button at catalog books screen

  @logout @cancelGet @tier2
  Scenario: Borrow book
    When I add 'The New York Public Library' account
    Then Account 'The New York Public Library' is present on Accounts screen
    When I enter credentials for 'The New York Public Library' account
    Then Text on Login button is changed to Log out on Account screen
    When I open Catalog
      And I switch to 'The New York Public Library' from side menu
    Then Books feed is loaded
    When I open category by chain:
      | Essential Reads on Feminism |
      And Save current 'The New York Public Library' library for CANCEL_GET books after test
      And I open the book details for the subsequent GET and save it as 'bookInfo'
      And I check that opened book contains READ button at book details screen
      And Press on the book details screen at the action button RETURN
      And I check that the action button text equal to the GET
      And I return to previous screen
      And I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button GET
    Then I check that the action button text equal to the READ
