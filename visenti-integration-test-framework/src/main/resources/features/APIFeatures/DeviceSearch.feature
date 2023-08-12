@api @module-device
Feature: Verifies the loading of different device/station on search

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @station-search
  Scenario Outline: Verify loading of Station meta details from the search api for a given customer
  This test verifies the successful loading of station meta details for a given station details

    Given I setup the request for device meta search api with "<SearchType>" , "<SearchString>" and "<SearchField>"
    When I perform "post" request on the endpoint "/search/meta"
    Then I verify the status code to be "200"
    And  I verify the default response for the GIS meta search Api for devices
    And  I verify meta details of station search api with "<SearchString>" and "<SearchField>"

    @asgor
    Examples:
      | SearchType | SearchString             | SearchField  |
      | station    | kg ssp_624-20-8 stn 1619 | display_name |
      | station    | asgor27_1619             | _id          |