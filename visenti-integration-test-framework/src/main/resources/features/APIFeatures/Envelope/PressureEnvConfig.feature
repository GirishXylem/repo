@api @envelope
Feature: Verify Get pressureEnvConfig API

  @api @yantai
  Scenario: Verify Successful Get pressureEnvConfig Request
    Given I setup the request for "data" /pressureEnvConfig Api
    When I perform "get" request on the endpoint "/pressureEnvConfig"
    Then I verify the status code to be "200"
    And I verify the full Response for Get pressureEnvConfig Api

