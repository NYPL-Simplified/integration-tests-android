Feature: Manage Libraries

  Scenario: Add Library
    When I accept EULA agreement
      And I select Add a Library Later button on Main screen
      And I open Settings from Menu pane
      And I open Accounts on Settings screen
      And I click Add button on Accounts screen
      And I select 'Alameda County Library' on Add Account screen
    Then Account 'Alameda County Library' is present on Accounts screen
