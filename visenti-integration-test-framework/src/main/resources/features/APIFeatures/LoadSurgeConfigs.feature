@api @module-surges
Feature: Verify loading of different surge API's

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @smoke @api @surges-metrics-config @pub @cloud @asgor
  Scenario: Verify loading of Surges metrics config for a given customer
  This test verifies the successful loading of Surges config for a given customer

    Given I setup the request for the "aims" api for surges config
    When I perform "get" request on the endpoint "/surges/metrics/config"
    Then I verify the status code to be "200"
    And  I verify the full response for the surges config Api

  @smoke @api @network-performance-config-criteria @pub @cloud @asgor
  Scenario Outline: Verify loading of network performance config criteria API
  This test verifies the successful loading of network performance config api

    Given I setup the request for the "aims" api for network performance config with "<Criteria Id>"
    When I perform "get" request on the endpoint "/network-performance/config-criteria"
    Then I verify the status code to be "200"
    And  I verify the full response for the network config Api with "<Criteria Id>"

    Examples:
      | Criteria Id |
      | 11          |

  @smoke @api @get-risk-by-zones @pub @cloud @asgor
  Scenario: Verify getting of risk by zones
  This test verifies the successful loading of risks by zone

    Given I setup the request for the "aims" api to get risk of zones
    When I perform "post" request on the endpoint "/surge/getriskbyzones"
    Then I verify the status code to be "200"
    And  I verify the full response for the risk by zones Api

  @smoke @api @surges-metrics @pub @cloud @asgor
  Scenario: Verify loading of metrics for surges
  This test verifies the successful loading of metrics for the surges

    Given I setup the request for the "aims" api to get surges metrics
    When I perform "post" request on the endpoint "/surges/metrics"
    Then I verify the status code to be "200"
    And  I verify the full response for the surges metrics Api

  @smoke @api @damaging-transients @pub @cloud @asgor
  Scenario: Verify loading of metrics for surges
  This test verifies the successful loading of metrics for the surges

    Given I setup the request for the "aims" api to get damaging transients
    When I perform "post" request on the endpoint "/damaging-transient/stats"
    Then I verify the status code to be "200"
    And  I verify the response for the damaging transients Api


  @smoke @api @get-transients @pub @cloud @asgor
  Scenario: Verify loading of metrics for surges
  This test verifies the successful loading of metrics for the surges

    Given I setup the request for the "aims" api to get transients
    When I perform "post" request on the endpoint "/getTransients"
    Then I verify the status code to be "200"
    And  I verify the response for transients Api