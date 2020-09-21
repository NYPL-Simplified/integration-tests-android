Feature: Catalog Navigation

  Background:
    Given Application is opened

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
    When I set text to the search textBox 'Harry Potter et les Reliques'
      And I click apply search button
    Then Search modal is closed
      And Search page is opened
    When I switch to 'eBooks' catalog tab
    Then Subcategory screen is present
    When I open first found book from the search result
    Then Book 'Harry Potter et les Reliques de la Mort, ebook, by J. K. Rowling' is opened
      And The following values in the information block are present:
        | key         | value                 |
        | PUBLISHED   | 2014-08-07            |
        | PUBLISHER   | Pottermore Publishing |
        | DISTRIBUTOR | Overdrive             |
        | CATEGORIES  | Fantasy               |
        | UPDATED     | 2020-09-17 08:45:57   |
      And Description has text
    """
    Cette année, Harry a dix-sept ans et ne retourne pas à Poudlard. Avec Ron et Hermione, il se consacre à la dernière mission confiée par Dumbledore. Mais le Seigneur des Ténèbres règne en maître. Traqués, les trois fidèles amis sont contraints à la clandestinité. D'épreuves en révélations, le courage, les choix et les sacrifices de Harry seront déterminants dans la lutte contre les forces du Mal. Avec le dénouement de l'héroïque histoire de Harry Potter, J.K. Rowling signe un chef-d'oeuvre d'une grande humanité et d'une maîtrise incomparable.
    """
    When I open related books
    Then Current category name is 'Related Books'
      And Count of books in subcategory 'J. K. Rowling' lane is up to 12
    When I open 'J. K. Rowling' subcategory
    Then Current category name is 'J. K. Rowling'
      And Count of books in search result is up to 12
    When I go back to the previous catalog screen
    Then Current category name is 'Related Books'
      And Count of books in subcategory 'Similar titles recommended by NoveList' lane is up to 12
    When I open 'Similar titles recommended by NoveList' subcategory
    Then Current category name is 'Titles recommended by NoveList'
      And Count of books in search result is up to 12
    When I go back to the previous catalog screen
    Then Current category name is 'Related Books'
      And Count of books in subcategory 'Harry Potter' lane is up to 12
    When I open 'Harry Potter' subcategory
    Then Current category name is 'Harry Potter'
      And Count of books in search result is up to 12

  @logout @cancelHold
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
      And Save current library for CANCEL_HOLD books after test
    Then Book saved as 'bookInfo' should contain CANCEL button at catalog books screen

  @logout @cancelGet
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
      And Save current library for CANCEL_GET books after test
      And I open the book details for the subsequent GET and save it as 'bookInfo'
      And I check that opened book contains READ button at book details screen
      And Press on the book details screen at the action button RETURN
      And I check that the action button text equal to the GET
      And I return to previous screen
      And I open book 'bookInfo' details by clicking on cover
      And Press on the book details screen at the action button GET
    Then I check that the action button text equal to the READ
