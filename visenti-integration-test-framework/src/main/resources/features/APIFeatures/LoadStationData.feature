@api @module-plotter
Feature: Verify Loading of the Station and sensor of that station

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @regression @station-data
  Scenario Outline: Verify loading of Station's "<station>" sensor "<stationSensors>" with "<dataType>" for interval of "<interval>" mins in last 24 hours
  This test verifies the /search/data api for a given Station sensor and data type.

    Given I setup the request for the "data" api with "<stationSensors>", "<units>", "<dataType>" and "<interval>" for "<station>"
    When  I perform "post" request on the endpoint "/search/data"
    And   I verify the status code to be "200"
    And   I verify the mandatory data in the response for Station data API
    And   I verify the station "<station>" meta in the response with "<units>" and "<stationSensors>"

    Examples:
      | station        | stationSensors | units | dataType      | interval |
      | technolog_1015 | pressure       | mH    | Original Data | 60       |
      | halmapi_1060   | flow2          | L/s   | Original Data | 60       |
