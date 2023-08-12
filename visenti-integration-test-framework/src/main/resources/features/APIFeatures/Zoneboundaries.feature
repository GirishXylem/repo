@api @module-zonesboundaries
Feature: Verify Loading all the Zones boundaries KML

  Background: User should have a Valid Access Token
    Here in the Background ,we are validating the existing Access Token,
    Generating a new one if it is invalid ,and storing the Valid Access Token in properties file

    Given The service User has a valid Access Token

  @regression @kml-zone-boundaries @asgor @cloud @pub
  Scenario: Verify loading of all DMA KML zone boundaries
    This test verifies the successful loading of the all kml zone boundaries
     /get/kml?zoneId={zoneId}

    Given I setup the request for the "gis" getCustomerInfo Api
    When I perform "GET" request on the endpoint "/get/customerInfo"
    And I verify the status code to be "200"
    Then I setup the request for "gis" and perform "GET" request on the endpoint "/get/kml" and validate the common and specific response
