@api @envelope
Feature: Verify Get pressureEnvData API

  @api @yantai
  Scenario Outline: Verify Successful Get pressureEnvData Request
    Given I setup the request for "data" /pressureEnvData Api
    And I setup the query param's for /pressureEnvData Api with startTime "<startTime>"
    When I perform "get" request on the endpoint "/pressureEnvData"
    Then I verify the status code to be "200"
    And I verify the full Response for Get pressureEnvData Api

    Examples:
      | startTime     |
      | 1690950658000 |