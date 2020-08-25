Feature: Manage Libraries

  Background:
    When I accept EULA agreement
      And I select Add a Library Later button on Main screen
      And I open Settings from Menu pane
      And I open Accounts on Settings screen
      And I click Add button on Accounts screen
      And I select 'Hartford Public Library' on Add Account screen
    Then Account 'Hartford Public Library' is present on Accounts screen

  @logout
  Scenario: Add Library
    When I click 'Hartford Public Library' account on Accounts screen
      And I enter credentials for ebook user on Account screen
    Then Text on Login button is changed to Log out on Account screen

  Scenario: Switch Library Catalogs
    When I open Catalog from Menu pane
      And I approve that User is 13 or older
    Then Books feed is loaded
    #id = feedWithGroups
    When I get names of books in first lane and save them as 'nameOfBooks'
    #(//android.widget.LinearLayout[@content-desc="Roots and Branches, ebook, by Andrew Kaye"])[1]
  #//androidx.recyclerview.widget.RecyclerView[1]//android.widget.LinearLayout[@content-desc]
      And I open 'Hartford Public Library' from side menu
    Then List of books is not equal to list of books saved as 'nameOfBooks'
