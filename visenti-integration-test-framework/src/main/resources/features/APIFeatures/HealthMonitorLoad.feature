@module-health-monitor @api
Feature: Verify loading of Health monitor API's

  @hm-load-user-preferences @cloud @pub @asgor
  Scenario: Verify loading of HM UserPreferences for a given customer and user
  This test verifies the successful loading of HM UserPreferences
  for a given userId and customerId /getAIMSUserPreferences?userId={userId}&customerId={customerId}

    Given I setup the request for "data" api for user preferences
    When I perform "get" request on the endpoint "/hm/userPreferences"
    Then I verify the status code to be "200"
    And  I verify the full response for HM user preferences Api

  @hm-update-user-preferences @cloud @pub @asgor
  Scenario Outline: Verify updating of HM UserPreferences for a given customer and user
  This test verifies the successful updating of HM User Preferences

    Given I setup the request for "data" api for updating user preferences for "<columns>"
    When I perform "post" request on the endpoint "/hm/userPreferences"
    Then I verify the status code to be "200"
    And  I verify the full response for HM user preferences post Api

    Examples:
      | columns |
      | all     |

  @hm-get-detections @cloud @pub @asgor
  Scenario: Verify loading of HM detections
  This test verifies the successful loading of HM detections

    Given I setup the request for "data" api for getting detections
    When I perform "get" request on the endpoint "/hm/detections/get"
    Then I verify the status code to be "200"
    And  I verify the full response for getting HM detections