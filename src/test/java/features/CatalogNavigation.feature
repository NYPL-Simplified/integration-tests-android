Feature: Catalog Navigation

  Background:
    Given Application is opened

  @tier1
  Scenario: Return to last library catalog
    When I add 'Alameda County Library' account
      And I open Catalog
      And I switch to 'Alameda County Library' from side menu
    Then Books feed is loaded
    When I restart app
    Then Books feed is loaded
      And Current library is 'Alameda County Library' in Catalog

  @tier1
  Scenario: Navigate Lists
    When I add 'LYRASIS' account
      And I open Catalog
      And I switch to 'LYRASIS' from side menu
    Then Books feed is loaded
    When I get names of books on screen and save them as 'listOfBooksOnMainPage'
      And I open 'Nonfiction' category
    Then Current category name is 'Nonfiction'
      And Books feed is loaded
      And Following subcategories are present:
        | Gardening                   |
        | Art & Design                |
        | Biography & Memoir          |
        | Education                   |
        | Personal Finance & Business |
        | Parenting & Family          |
        | Food & Health               |
        | History                     |
        | Hobbies & Home              |
        | Humor                       |
        | Entertainment               |
        | Life Strategies             |
        | Literary Criticism          |
        | Philosophy                  |
        | Politics & Current Events   |
        | Reference & Study Aids      |
        | Religion & Spirituality     |
        | Science & Technology        |
        | Self-Help                   |
        | Sports                      |
        | Travel                      |
        | True Crime                  |
        | All Nonfiction              |
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When I return to previous screen
      And I open 'Fiction' category
    Then Current category name is 'Fiction'
      And Books feed is loaded
      And Following subcategories are present:
        | gardening fun      |
        | Classics           |
        | Drama              |
        | Adventure          |
        | Jane Austen        |
        | Fantasy            |
        | Folklore           |
        | Historical Fiction |
        | Horror             |
        | Humor              |
        | Literary Fiction   |
        | LGBTQ Fiction      |
        | Mystery            |
        | Poetry             |
        | Romance            |
        | Science Fiction    |
        | Short Stories      |
        | Thriller           |
        | Urban Fiction      |
        | Westerns           |
        | Women's Fiction    |
        | All Fiction        |
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When I open 'Westerns' subcategory
    Then Subcategory screen is present
      And Subcategory name is 'Westerns'

  @tier1
  Scenario: Browse Lanes/Categories
    When I add 'LYRASIS' account
      And I open Catalog
      And I switch to 'LYRASIS' from side menu
    Then Books feed is loaded
      And Count of books in first lane is up to 12
    When I open 'Fiction' category
    Then Current category name is 'Fiction'
      And Following subcategories are present:
        | gardening fun      |
        | Classics           |
        | Drama              |
        | Adventure          |
        | Jane Austen        |
        | Fantasy            |
        | Folklore           |
        | Historical Fiction |
        | Horror             |
        | Humor              |
        | Literary Fiction   |
        | LGBTQ Fiction      |
        | Mystery            |
        | Poetry             |
        | Romance            |
        | Science Fiction    |
        | Short Stories      |
        | Thriller           |
        | Urban Fiction      |
        | Westerns           |
        | Women's Fiction    |
        | All Fiction        |
    When I open 'Westerns' subcategory
    Then Subcategory screen is present
      And Subcategory name is 'Westerns'
    When I open first book in subcategory list and save it as 'bookInfo'
    Then Book 'bookInfo' is opened

  @tier1
  Scenario: Sort Lists
    When I add 'Alameda County Library' account
      And I open Catalog
      And I switch to 'Alameda County Library' from side menu
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

  @tier1 @exclude_ios
  Scenario: View Book Details
    When I add 'The New York Public Library' account
      And I open Catalog
      And I switch to 'The New York Public Library' from side menu
    When I open search modal
    Then Search modal is opened
    When I search for 'Tom Swift and His War Tank: Or, Doing His Bit for Uncle Sam'
      And I switch to 'eBooks' catalog tab
    Then Subcategory screen is present
    When I open book with name 'Tom Swift and His War Tank: Or, Doing His Bit for Uncle Sam' and save it as 'bookInfo'
    Then Book 'bookInfo' is opened
      And The following values in the information block are present:
        | key         | value                     |
        | PUBLISHED   | 2020-09-28                |
        | PUBLISHER   | Duke Classics             |
        | DISTRIBUTOR | Overdrive                 |
        | CATEGORIES  | Classics, Science Fiction |
      And Description has text
    """
    	Tom Swift and His War Tank is the 21st book in the original Tom Swift series.

"Every boy possesses some form of inventive genius. Tom Swift is a bright, ingenious boy and his inventions and adventures make the most interesting kind of reading."

"These spirited tales convey in a realistic way, the wonderful advances in land and sea locomotion and other successful inventions. Stories like these are impressed upon the memory and their reading is productive only of good."

This series of adventure novels starring the genius boy inventor Tom Swift falls into the genre of "invention fiction" or "Edisonade".
    """
    When I open related books
    Then Current category name is 'Related books…'
      And Count of books in subcategory 'Victor Appleton' lane is more then 1
    When I open 'Victor Appleton' subcategory
    Then Current category name is 'Victor Appleton'
      And Count of books in search result is more then 1
    When I return to previous screen
    Then Current category name is 'Related books…'
      And Count of books in subcategory 'Tom Swift' lane is more then 1
    When I open 'Tom Swift' subcategory
    Then Current category name is 'Tom Swift'
      And Count of books in search result is more then 1

  @tier1 @exclude_android
  Scenario: View Book Details (iOS)
    When I add 'The New York Public Library' account
      And I open Catalog
      And I switch to 'The New York Public Library' from side menu
    When I open search modal
    Then Search modal is opened
    When I search for 'Harry Potter and the Order of the Phoenix'
      And I switch to 'eBooks' catalog tab
    Then Subcategory screen is present
    When I open book with name 'Harry Potter and the Order of the Phoenix' and save it as 'bookInfo'
    Then Book 'bookInfo' is opened
      And The following values in the information block are present:
        | key         | value                 |
        | PUBLISHED   | 2015-12-08            |
        | PUBLISHER   | Pottermore Publishing |
        | DISTRIBUTOR | Overdrive             |
        | CATEGORIES  | Fantasy               |
      And Description has text
    """
    'You are sharing the Dark Lord's thoughts and emotions. The Headmaster thinks it inadvisable for this to continue. He wishes me to teach you how to close your mind to the Dark Lord.'
Dark times have come to Hogwarts. After the Dementors' attack on his cousin Dudley, Harry Potter knows that Voldemort will stop at nothing to find him. There are many who deny the Dark Lord's return, but Harry is not alone: a secret order gathers at Grimmauld Place to fight against the Dark forces. Harry must allow Professor Snape to teach him how to protect himself from Voldemort's savage assaults on his mind. But they are growing stronger by the day and Harry is running out of time...
    """
    When I open related books
    Then Count of books in subcategory 'J. K. Rowling' lane is up to 12
    When I open 'J. K. Rowling' subcategory
    Then Current category name is 'J. K. Rowling'
      And Count of books in search result is up to 12
    When I return to previous screen
    Then Count of books in subcategory 'Harry Potter' lane is up to 12
    When I open 'Harry Potter' subcategory
    Then Current category name is 'Harry Potter'
      And Count of books in search result is up to 12
