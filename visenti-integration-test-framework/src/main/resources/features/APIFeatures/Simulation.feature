@module-simulation @api
Feature: Verify API's for simulation module

  @yw @create-simulation
  Scenario Outline: Verify creating simulation for a specific simulation scenario
  This scenario verifies that for a specific scenario simulation is created

    Given I setup the request for the simulation api with the base path "/api-sim"
    And   I add payload to the request with "<details>" for "<scenario>"
    When  I perform "post" request on the endpoint "/combinedSimulations"
    Then  I verify the status code to be "200"
    And   I verify the response for create simulations API

    Examples:
      | details       | scenario |
      | P_10071133136 | pipe     |
      | V_05125007502 | valve    |
      | J_13626       | nwd      |

  @yw @simulation-result-list
  Scenario: Verify the list of simulations created for a specific date range
  This scenario verifies that for a specific date range to list all the simulation created on that date range

    Given I setup the request for the simulation api with the base path "/api-sim"
    And   I setup the query param's for the simulation result list
    When  I perform "get" request on the endpoint "/simulationResults/search"
    Then  I verify the status code to be "200"
    And   I verify the response for Simulations results API

  @yw @simulation-settings
  Scenario: Verify the pre defined settings for the simulation scenarios
  This scenario verifies the default settings that are set for the different simulation scenarios

    Given I setup the request for the simulation api with the base path "/api-sim"
    When  I perform "get" request on the endpoint "settings"
    Then  I verify the status code to be "200"
    And   I verify the response for simulations settings API