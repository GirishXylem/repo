@api @module-gis
Feature: Verifies the loading of different GIS on enable

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @smoke @gis-get-gis
  Scenario Outline: Verify loading of GIS "<gisType>" api for a given customer
  This test verifies the successful loading of gis "<gisType>" for a given customer

    Given I setup the request for gis "<gisType>" meta search api
    When I perform "post" request on the endpoint "/get/gis"
    Then I verify the status code to be "200"
    And  I verify the default response for the GIS meta search Api
    And  I verify meta details of GIS "<gisType>" api

    @cloud
    Examples:
      | gisType   |
      | bulkmeter |
      | junction  |
      | valve     |
      | pipe      |

    @asgor
    Examples:
      | gisType  |
      | junction |
      | valve    |
      | pipe     |

  @smoke @gis-id-search
  Scenario Outline: Verify loading of GIS meta details from the search api for a given customer
  This test verifies the successful loading of gis meta details for a given GIS id

    Given I setup the request for gis "<gisType>" meta search api with "<gisId>"
    When I perform "post" request on the endpoint "/search/meta"
    Then I verify the status code to be "200"
    And  I verify the default response for the GIS meta search Api
    And  I verify meta details of GIS "<gisType>" api

    @cloud
    Examples:
      | gisType | gisId    |
      | pipe    | P_32524  |
      | valve   | V_283895 |

    @asgor
    Examples:
      | gisType  | gisId   |
      | pipe     | P_30H   |
      | valve    | V_111SH |
      | junction | J_E68H  |