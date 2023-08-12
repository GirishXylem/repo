@api @module-gis
Feature: Verifies the loading of API's for GIS config

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @gis-row-config @cloud @pub @asgor
  Scenario: Verify loading of GIS row config for a given customer
  This test verifies the successful loading of gis config for
  a given customer

    Given I setup the request for the "gis" api row config
    When I perform "get" request on the endpoint "/get/gis/config-rows"
    Then I verify the status code to be "200"
    And  I verify the default response for the GIS row Config Api
    And  I verify all different applicable GIS are available for selection

  @gis-column-config @cloud @pub @asgor
  Scenario: Verify loading of GIS column config for a given customer
  This test verifies the successful loading of gis config for
  a given customer

    Given I setup the request for the "gis" api column config
    When I perform "get" request on the endpoint "/get/gis/config-columns"
    Then I verify the status code to be "200"
    And  I verify the default response for the GIS column Config Api
    And  I verify the required columns are available

  @gis-filter-config @cloud @pub @asgor
  Scenario Outline: Verify loading of GIS filter config for a given customer
  This test verifies the successful loading of gis filter for
  a given customer

    Given I setup the request for the "gis" api row config with parameter "<filter_for>"
    When I perform "get" request on the endpoint "/get/gis/filter?special=true"
    Then I verify the status code to be "200"
    And  I verify the default response for the GIS filter Config Api
    And  I verify all the filters for the specific GIS "<filter_for>" config API

    Examples:
      | filter_for |
      | pipe       |
      | valve      |