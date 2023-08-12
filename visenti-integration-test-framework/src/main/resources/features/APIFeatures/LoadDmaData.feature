@api @module-plotter
Feature: Verify Loading all the DMA sensors

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @regression @dma-data @report
  Scenario Outline: Verify data availability of DMA sensor "<dmaSensors>" with "<dataType>" for interval of "<interval>" in last 24 hours
  This test verifies the /search/data api for a given dma sensor and data type
  for all the DMA.

    Given I setup the request for the "gis" getCustomerInfo Api
    When  I perform "GET" request on the endpoint "/get/customerInfo"
    And  I verify the status code to be "200"
    Then I get the list of DMA ID and name
    And  I setup the Request of "dma" data api "/search/data" with "<dmaSensors>", "<units>", "<dataType>" and "<interval>" also verify the response

    Examples:
      | dmaSensors             | units | dataType      | interval |
      | Total Inflow           | L/s   | Original Data | 60       |
      | Current Demand         | L/s   | Original Data | 60       |
      | Average Pressure       | mH    | Original Data | 60       |