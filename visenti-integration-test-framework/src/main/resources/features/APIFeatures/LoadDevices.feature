@api @module-devices
Feature: Verify Loading all devices

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @api @regression @load-devices
  Scenario Outline: Verify loading of "<deviceType>" devices in "<networkType>" network
  This test verifies the get/devices api for a given device and network type
  for all the Zones applicable

    Given I setup the Request for device "<deviceType>" and network "<networkType>"
    When I perform "post" request on the endpoint "/get/devices"
    Then I verify the status code to be "200"
    And  I verify the full Response for device Load Devices Api
    And  I verify specific details of devices Api response for the network "<networkType>"

    @cloud @pub
    Examples:
      | deviceType    | networkType   |
      | Acoustic      | Potable Water |
      | Btry          | Potable Water |
      | Pressure      | Potable Water |
      | Transient     | Potable Water |
      | Flow          | Potable Water |
      | WQY           | Potable Water |
      | Level         | Potable Water |
      | CustomerMeter | Potable Water |

    @asgor
    Examples:
      | deviceType | networkType   |
      | Acoustic   | Potable Water |
      | Btry       | Potable Water |
      | Pressure   | Potable Water |
      | Transient  | Potable Water |
      | Flow       | Potable Water |

  @api @regression @station-latest-data
  Scenario Outline: Verify loading of latest data devices in  network
  This test verifies the get the latest data api for a given device and network type

    Given I setup the request for "data" api to get the latest data for station "<station>"
    When I perform "get" request on the endpoint "/search/latestdata"
    Then I verify the status code to be "200"
    And  I verify the response for get latest data Api

    @cloud @pub
    Examples:
      | station  |
      | zone1_30 |

    @asgor
    Examples:
      | station      |
      | asgor27_1619 |