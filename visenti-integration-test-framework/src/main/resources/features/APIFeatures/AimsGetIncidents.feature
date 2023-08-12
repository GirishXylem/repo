@api @module-aims
Feature: Verifies the loading of incidents for AIMS Module

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @aims-get-incidents @report
  Scenario Outline: Verify loading of AIMS incident details for a given customer of type "<incidentType>"
  This test verifies the successful loading of Aims incidents of all type

    Given I setup the request for the "aims" api with Incident type "<incidentType>" and anomaly "<anomaly>" also the zones "<zones>"
    When  I perform "post" request on the endpoint "/v2/get-incidents"
    Then  I verify the status code to be "200"
    And   I verify the full response for the Aims getIncident Api
    And   I verify specific details in getIncident Api response like incident type "<incidentType>" and others

    @cloud @pub
    Examples:
      | incidentType  | anomaly | zones |
      | all           | all     | all   |
      | Burst         | all     | all   |
      | Leak          | all     | all   |
      | Water Quality | wqy     | all   |

    @asgor
    Examples:
      | incidentType | anomaly  | zones |
      | all          | all      | all   |
      | Burst        | all      | all   |
      | Water Hammer | pressure | all   |

  @aims-get-incidents-details
  Scenario Outline: Verify loading of AIMS incident details for a given customer of type "<incidentType>"
  This test verifies the successful loading of Aims incidents details page of all type

    Given I setup the request for the "aims" api with Incident type "<incidentType>" and anomaly "<anomaly>" also the zones "<zones>"
    When  I perform "post" request on the endpoint "/v2/get-incidents"
    Then  I verify the status code to be "200"
    And   I retrieve the incident ID
    And   I retrieve few details from get incidents API
    Given I setup the request for the "aims" api for incident details
    When  I perform "post" request on the endpoint "/v2/get-incident-details"
    Then  I verify the status code to be "200"
    And   I verify the full response for the Aims get incident details Api
    And   I verify details in the incident list is same as in the incident details

    @pub @cloud
    Examples:
      | incidentType  | anomaly | zones |
      | all           | all     | all   |
      | Burst         | all     | all   |
      | Water Quality | wqy     | all   |

    @asgor
    Examples:
      | incidentType | anomaly  | zones |
      | all          | all      | all   |
      | Burst        | all      | all   |
      | Water Hammer | pressure | all   |
	
					