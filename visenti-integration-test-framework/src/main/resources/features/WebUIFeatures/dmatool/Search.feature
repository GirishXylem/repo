@web
Feature: Verify Search functionality in the portal
  As a user of the View
  I want to search a station and verify different functionality

  Background: User should be on the Portal home Page
    Given I have successfully logged in to the "View" portal

  @smoke @search @web
  Scenario Outline: Verify search with different criteria
    When I search the pub search panel with "<keyword>"
    Then I click on the "<image>" from the search
    And Verify the details of the station or gis or zone with "<keywordToVerify>"

    Examples:
      | keyword       | image        | keywordToVerify |
      | station_Name  | Blue Station | station_Name    |
      | station_Id    | Blue Station | station_Name    |
      | gis_Pipe      | Pipe         | gis_Pipe        |
      | gis_Valve     | Valve        | gis_Valve       |
      | gis_Junction  | Junction     | gis_Junction    |
      | gis_BulkMeter | Bulk Meter   | gis_BulkMeter   |
      | gis_Hydrant   | Hydrant      | gis_Hydrant     |

 # @smoke @search @web
  #Scenario : Verify on clicking of zone opens with right panel
   # When I click on the "Western Zone" from the search
    #And Verify the details of the station or gis or zone with "WESTERN"