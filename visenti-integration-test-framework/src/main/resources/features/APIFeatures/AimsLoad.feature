@api @module-aims
Feature: Verifies the loading of API's for AIMS Module

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @smoke @api @aims-load-aims-config @pub @cloud @asgor
  Scenario: Verify loading of AIMS config for a given customer
  This test verifies the successful loading of Aims config for
  a given customerId /getAimsConfig?customerId={customerId}

    Given I setup the request for "aims" /getAimsConfig Api
    When I perform "get" request on the endpoint "/getAimsConfig"
    Then I verify the status code to be "200"
    And  I verify the full response for the Aims getConfig Api


  @smoke @api @aims-load-user-preferences @cloud @pub @asgor
  Scenario: Verify loading AIMS UserPreferences for a given customer and user
  This test verifies the successful loading of Aims UserPreferences
  for a given userId and customerId
  /getAIMSUserPreferences?userId={userId}&customerId={customerId}

    Given I setup the request for "aims" /getAIMSUserPreferences Api
    When I perform "get" request on the endpoint "/getAIMSUserPreferences"
    Then I verify the status code to be "200"
    And  I verify the full response for Aims getUserPreferences Api

  @smoke @api @aims-save-user-preferences @cloud @pub @asgor
  Scenario: Verify saving of AIMS UserPreferences for a given customer and user
  This test verifies the successful saving of AIMS UserPreferences
  for a given userId and customerId

    Given I setup the request for "aims" /saveAIMSUserPreferences Api
    When I perform "post" request on the endpoint "/saveAIMSUserPreferences"
    Then I verify the status code to be "200"
    And  I verify the full response for Aims saveUserPreferences Api

  @smoke @api @aims-incidents-doc @cloud @pub @asgor
  Scenario Outline: Verify downloading of a incident document
  This test verifies the successful of downloading an incident document

    Given I setup the request for "aims" /getIncidentsDoc Api with Incident type "<incidentType>" and anomaly "<anomaly>" and zones "<zones>"
    When I perform "post" request on the endpoint "/getIncidentsDoc"
    Then I verify the status code to be "200"
    And  I verify the response content type of getIncidentsDoc Api

    Examples:
      | incidentType | anomaly | zones |
      | all          | all     | all   |

  @smoke @api @aims-incidents-dashboard @cloud @pub @asgor
  Scenario Outline: Verify downloading of a incidents dashboard document
  This test verifies the successful of downloading an incident dashboard document

    Given I setup the request for "aims" /getIncidentsDoc Api with Incident type "<incidentType>" and anomaly "<anomaly>" and zones "<zones>"
    When I perform "post" request on the endpoint "/getIncidentsDoc"
    Then I verify the status code to be "200"
    And  I verify the response content type of getIncidentsDoc Api

    Examples:
      | incidentType | anomaly | zones |
      | all          | all     | all   |
	
	
					