@module-health-monitor @api
Feature: Verifies API's for health monitor

  @health-status
  Scenario: Verify health status of the station/sensors
  This scenario verifies the data or the status of the station or a sensor

    Given I setup the request for the "data" api with parameter
    When  I perform "get" request on the endpoint "/hm/data/get"
    Then  I verify the status code to be "200"
    And   I verify the full response for Station/Sensor health API
    Then  I verify the station is online when at least one sensor is online
    And   I also verify the station is offline when all the sensors are offline
    And   I also verify if station has received data within "48" hours

  @health-instrument-station @cloud
  Scenario Outline: Verify health status of the station
  This scenario verifies the data or the status of the station

    Given I setup the request for the "data" api
    And   I setup the query param's for HM instrument tab stations with the "<Vendor>"
    When  I perform "get" request on the endpoint "/hm/data/get"
    Then  I verify the status code to be "200"
    And   I verify the full response for Station/Sensor health API
    Then  I verify the station is online when at least one sensor is online
    And   I also verify the station is offline when all the sensors are offline

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @health-instrument-sensor @smoke
  Scenario Outline: Verify health status of the sensors
  This scenario verifies the data or the status of the sensor

    Given I setup the request for the "data" api
    And   I setup the query param's for HM instrument tab sensors with the "<Vendor>"
    When  I perform "get" request on the endpoint "/hm/data/get"
    Then  I verify the status code to be "200"
    And   I verify the full response for Station/Sensor health API
    And   I verify specific details of sensor in instrument tab

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @data-status @report @hm-report
  Scenario: Verify data availability of different vendors
  This scenario verifies the online station count status of different vendors

    Given I setup the request for the "data" api
    When  I perform "get" request on the endpoint "/data/status"
    Then  I verify the status code to be "200"
    And   I verify the full response for Data status health API
    And   I also verify the data availability of all the Vendors

  @widget-statics @pub @cloud
  Scenario Outline: Verify widget statistics for different vendors like "<Vendor>"
  This scenario verifies the statistics of issue type for different vendors as a widget

    Given I setup the request for the "data" api
    And   I setup the query param's for HM widget API with the "<Vendor>"
    When  I perform "get" request on the endpoint "/healthmonitor/widgetstatistics"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I also verify the widget statistics from widget API

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @issue-distribution-table
  Scenario Outline: Verify issue type distribution table for the vendor's "<Vendor>"
  This scenario verifies the issue type distribution table for different vendors

    Given I setup the request for the "data" api
    And   I setup the query param's for issue table API with the "<Vendor>"
    When  I perform "get" request on the endpoint "/hm/data/get"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I also verify the issue type distribution table API for specific "<Vendor>"

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @daily-issues-chart  @health-chart
  Scenario Outline: Verify daily issues chart for the vendor's "<Vendor>"
  This scenario verifies the daily issues chart for different vendors

    Given I setup the request for the "data" api with the base path "/hm/trends/historical"
    And   I setup the query param's for all the charts of HM with the "<Vendor>"
    When  I perform "get" request on the endpoint "/stacked/get/v2"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I verify the specific response for daily issues chart health API

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @issues-resolved-new-issues-backlog-chart  @health-chart
  Scenario Outline: Verify Issues resolved vs new issues and Backlog chart for the vendor's "<Vendor>"
  This scenario verifies the Issues resolved vs new issues and Backlog chart for different vendors

    Given I setup the request for the "data" api with the base path "/hm/trends/historical"
    And   I setup the query param's for all the charts of HM with the "<Vendor>"
    When  I perform "get" request on the endpoint "/get"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I verify the specific response for issues resolved,new issues and backlog chart health API

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @hour-on-hour-chart  @health-chart
  Scenario Outline: Verify hour on hour chart for the vendor's "<Vendor>"
  This scenario verifies the hour on hour chart for different vendors

    Given I setup the request for the "data" api with the base path "/hm/trends/historical"
    And   I setup the query param's for all the charts of HM with the "<Vendor>"
    When  I perform "get" request on the endpoint "/stacked/gethour"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I verify the specific response for hour on hour chart health API

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @pie-chart @health-chart
  Scenario Outline: Verify current and previous pie chart for the vendor's "<Vendor>"
  This scenario verifies the current and previous pie chart for different vendors

    Given I setup the request for the "data" api with the base path "/hm/trends/historical"
    And   I setup the query param's for all the charts of HM with the "<Vendor>"
    When  I perform "get" request on the endpoint "/pie/get/v2"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I verify the specific response for pie chart health API

    @asgor
    Examples:
      | Vendor |
      | AIS    |

  @issue-type-distribution-chart @yw @health-chart
  Scenario Outline: Verify issue type distribution chart for the vendor's "<Vendor>"
  This scenario verifies the issue type distribution chart for different vendors

    Given I setup the request for the "data" api with the base path "/hm/trends/historical"
    And   I setup the query param's for all the charts of HM with the "<Vendor>"
    When  I perform "get" request on the endpoint "/stacked/get/chart"
    Then  I verify the status code to be "200"
    And   I verify the common response for data module for all the health API's
    And   I verify the specific response for issue distribution chart health API

    @asgor
    Examples:
      | Vendor |
      | AIS    |