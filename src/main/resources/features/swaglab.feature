Feature: Swag Lag Shopping

  @swaglab
  Scenario: Shop for multiple items at Swag lab application
    Given I launch swag lab application
    And login as standard user
    When I shop for multiple items
    Then I should able to place order successfully