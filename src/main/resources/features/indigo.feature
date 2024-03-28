Feature: Indigo search flight options

  @indigo
  Scenario: Search for one way ticket from Mumbai to Delhi for single passenger for current date
    Given I launch Indigo application
    When I search for "one" way ticket from "Mumbai" to "Delhi" for 1 passenger
    Then I should get all available flight options for "Mumbai" to "Delhi"