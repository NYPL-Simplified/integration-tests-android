Feature: Refresh Feed

  Scenario: Update Bookshelf List
    When I open application
      And I open Catalog
      And I switch to 'The SimplyE Collection' from side menu
      And I get first book from shelf and save it as 'bookInfo'
      And I open Books
    Then Book 'bookInfo' is present in Books List
      And Count of books is equal to 1
    When I refresh list of books
    Then Book 'bookInfo' is present in Books List
      And Count of books is equal to 1