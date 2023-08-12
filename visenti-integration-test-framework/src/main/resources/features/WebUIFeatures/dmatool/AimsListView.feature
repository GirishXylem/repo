@web @module-aims
Feature: Verify AIMS functionality of the portal
  As an user of the View
  I want to check different functionalities of View functionality

  Background: User should be on the Portal home Page
    Given I have successfully logged in to the "View" portal

  @smoke @web
  Scenario: Verify AIMS with all the mandate details by passing default date
    When I click on "AIMS" button in the dma home page
    And  I Verify all the sensors are selected by default
    #If specific date is not required keep it default
    Then I select "default" date from the global date picker
    And  I verify that live mode is "on"
    And  I also verify aims right panel list view details
    And  I verify there is no data available for selected date range

  @smoke @web
  Scenario: Verify AIMS with all the mandate details by specifying user specified date
    When I click on "AIMS" button in the dma home page
    And  I Verify all the sensors are selected by default
    #Provide the date in dd/MM/yyyy format only
    And  I select "07/08/2020" date from the global date picker
    And  I toggle the live mode
    Then I verify that live mode is "off"
    And  I also verify aims right panel list view details

  @web
  Scenario: Verify AIMS Unattended tab with incidents
    When I select "07/08/2020" date from the global date picker
    Then I click on "AIMS" button in the dma home page
    And  I Verify all the sensors are selected by default
    And  I toggle the live mode
    And  I verify "Unattended" tab is selected and have incidents

  @web
  Scenario Outline: Search AIMS Unattended tab with different incidents
    When I select "07/08/2020" date from the global date picker
    Then I click on "AIMS" button in the dma home page
    And  I toggle the live mode
    And  I verify "Unattended" tab is selected and have incidents
    Then I search the incident with "<incident type>"
    And  I verify the "<incident type>" is present in first and last page of the list

    Examples:
      | incident type          |
      | water quality          |
      | burst                  |
      | low head               |
      | water quality advanced |
      | water hammer           |
      | leak                   |

  @web
  Scenario Outline: Flag an Unattended incident and verify
    When I select "07/08/2020" date from the global date picker
    Then I click on "AIMS" button in the dma home page
    And  I toggle the live mode
    Then I search the incident with "<incident type>"
    And  I flag the "unattended" incident and verify the incident is flagged in flagged tab

    Examples:
      | incident type |
      | Leak          |

  @web
  Scenario Outline: Verify the incident details
    When I select "07/08/2020" date from the global date picker
    Then I click on "AIMS" button in the dma home page
    And  I toggle the live mode
    And  I click on "overview" tab
    Then I search the incident with "<keyword>"
    And  I click on the incident and verify the incident details

    Examples:
      | keyword |
      | Burst   |
      | Leak    |

  @web
  Scenario Outline: Verify the source location functionality
    When I select "07/08/2020" date from the global date picker
    Then I click on "AIMS" button in the dma home page
    And  I toggle the live mode
    And  I click on "overview" tab
    Then I search the incident with "<keyword>"
    And  I click on the location button
    And  I verify the "<keyword>" incident details by clicking the marker on the map

    Examples:
      | keyword |
      | Burst   |
      | Leak    |