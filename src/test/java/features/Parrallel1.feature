Feature: Parallel1

  Background:
    Given Application is opened
    When I open Catalog

  @wait1
  Scenario: Wait1
    When I wait for 10 seconds