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

  @logout
Scenario: Borrow Book
  When I add 'Hartford Public Library' account
  Then Account 'Hartford Public Library' is present on Accounts screen
  When I enter credentials for 'Hartford Public Library' account
  Then Text on Login button is changed to Log out on Account screen
  When I reserve book in 'Fiction'-'Drama' category from given distributors and save it as 'bookInfo':
    | Overdrive          |
    | RBdigital          |
    | Baker and Taylor   |
    | DPLA Exchange      |
    | SimplyE collection |
    | Open Bookshelf     |
    | Biblioboard        |
    Then Read button is present on Book page
    When I Return book

  #read	tap "Read"	opens book reader to cover-page (or title-page)	pass	11/27/2018
  #start return	tap "Return"	an alert pops up with cancel|return choices	pass	11/27/2018	dialog alert says "Are you sure you want to return Aesop's Fables"?  with Cancel|Return links
  #cancel return	tap "Cancel" after "Return"	action is cancelled and book remains checked out	pass	11/27/2018
  #return	tap "Return" after "Return"	book returned, and "Get" (or "Reserve") button appears	pass	11/27/2018
  #re-borrow same book right after it's returned	tap "Get" then "Return", then "Get" again either from book-view or list-view	book can be re-read after returning	fail		** Books don't always allow a borrow immediately after they have been returned, unless the user goes to another page or filter which will refresh the book's state.  So a book might say "get" immediately after you return it, but tapping "Get" will result in a "Borrowing failed" error message.  If the user goes to another menu or filter, then this will refresh the underlying data, and they can then borrow this title without interruption.
