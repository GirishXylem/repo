@api @module-initial-portalLoad
Feature: Verifies all the API's during Initial Portal Load

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @smoke @api @ums-load-account @pub @cloud @asgor
  Scenario: Verify User account data is successfully loaded from UMS
  This scenario verifies the handshake with UMS portal is successful or not
  and loads data for the given User account

    Given I setup the request for the UMS account Api
    When  I perform "get" request on the endpoint "/account"
    Then  I verify the status code to be "200"
    And   I verify the full response for UMS account Api


  @smoke @api @ums-load-resources @pub @cloud @asgor
  Scenario: Verify loading and grouping of Resources for an Application and a Customer from UMS
  This test verifies loading and grouping the resources data for an
  application and customer from UMS - /serviceValidates/{application}/{customer}?groupby=resource

    Given I setup the request for the UMS /serviceValidates Api for an Application and a Customer
    When  I perform "get" request on the endpoint "/serviceValidates/{application}/{customer}"
    Then  I verify the status code to be "200"
    And   I verify the full response for the UMS serviceValidates Api


  @smoke @api @dma-load-commattributes @pub @cloud @asgor
  Scenario: Verify all the Common Attributes successfully getting loaded
  This test verifies the successful loading of all the common attributes
  /themes/commattributes/all

    Given I setup the request for the DMA commonattributes Api
    When  I perform "GET" request on the endpoint "/themes/commattributes/all"
    Then  I verify the status code to be "200"
    And   I verify the full response for DMA commattributes Api

  @smoke @api @data-load-displayunits @pub @cloud @asgor
  Scenario: Verify all the Display Units successfully getting loaded
  This test verifies the successful loading of all the display units
  /displayunit/all for the data api

    Given I setup the request for the Data displayunit Api
    When  I perform "GET" request on the endpoint "/displayunit/all"
    Then  I verify the status code to be "200"
    And   I verify the full response for the Data displayunit Api

  @smoke @api @gis-load-customerInfo @pub @cloud @asgor
  Scenario: Verify loading of Customer Info data for a given customer
  This test verifies the successful loading of the customer info data
  for a given customer /get/customerInfo?customer={customerName}

    Given    I setup the request for the "gis" getCustomerInfo Api
    When     I perform "GET" request on the endpoint "/get/customerInfo"
    Then     I verify the status code to be "200"
    And      I verify the full response for the Gis customerInfo Api

  @smoke @api @data-load-livestatus @pub @cloud @asgor
  Scenario: Verify loading of HealthMonitor livestatus api for type Station
  This test verifies the successful loading of the livestatus data
  for the stations /healthmonitor/livestatus?type=["station"]

    Given  I setup the request for the Data /livestatus Api
    When   I perform "GET" request on the endpoint "/healthmonitor/livestatus"
    Then   I verify the status code to be "200"
    And    I verify the full response for the Data livestatus Api

  @gis-pipe-zoom @pub @cloud @asgor
  Scenario: Verify loading of GIS pipes for a given customer
  This test verifies the successful loading of GIS pipes for a given customer

    Given I setup the request for the "gis" Pipes Zoom Api with the base path "/pipes"
    And   I setup the query param for gis zoom Api
    When  I perform "get" request on the endpoint "/zoom"
    Then  I verify the status code to be "200"
    And   I verify the full response for the GIS Pipes Zoom Api
    And   I verify the specific response for the GIS Pipes Zoom Api

  @dma-themes-all @pub @cloud @asgor
  Scenario: Verify all the themes successfully loaded
  This test verifies the successful loading of all the themes
  /themes/all

    Given I setup the request for the "dma" Api with the base path "/themes"
    When  I perform "get" request on the endpoint "/all"
    Then  I verify the status code to be "200"
    And   I verify the full response for DMA all themes Api
    And   I verify the specific response for the DMA all themes Api
    And   I verify the key and name having same value for the DMA all themes Api