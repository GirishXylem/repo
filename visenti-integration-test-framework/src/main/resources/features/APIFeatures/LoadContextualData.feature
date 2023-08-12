@api @module-otherSources @contextualData
Feature: Verify Loading of contextual data i,e Work orders, customer calls, leak risk

  Background: User should have a Valid Access Token
  Here in the Background ,we are validating the existing Access Token,
  Generating a new one if it is invalid ,and storing the Valid Access Token in properties file
    Given The service User has a valid Access Token

  @api @regression @yw
  Scenario Outline: Verify loading of "<contextualDataType>" contextual data
  This test verifies the contextual data api for a given type in the examples

    Given I setup the request for contextual data "dma" api with parameter "<contextualDataType>" and "<days>"
    When I perform "get" request on the endpoint "/getReferenceData"
    Then I verify the status code to be "200"
    And  I verify the common response for contextual data
    And  I also verify the specific response for contextual data of type "<contextualDataType>"

    Examples:
      | contextualDataType  | days |
      | customer_complaints | 7    |
      | leak_risk           | 15   |
      | work_orders         | 1    |


