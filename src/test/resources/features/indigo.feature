Feature: Indigo search flight options

  @indigo
  Scenario: Search for one way ticket from Mumbai to Delhi for single passenger
    Given I launch Indigo application
    When I search for "one" way ticket from "Mumbai" to "Pune" for "single" passenger
    Then I should get all available flight options