@web @module-gis
Feature: Verify GIS functionality of the portal
  As an user of the View
  I want to check different functionalities of View functionality

  Background: User should be on the Portal home Page
    Given I have successfully logged in to the "View" portal

  @smoke @web @yw @cloud @gis-header-details
  Scenario: Verify GIS tab header details
    When I click on "GIS" button in the dma home page
    Then I verify row header details available under GIS tab

  @smoke @web @yw @cloud @gis-tab-details
  Scenario: Verify GIS tab details and default selected tab
    When I click on "GIS" button in the dma home page
    Then I verify all tabs available under GIS button
    And  I verify GIS tab selected by default under GIS tab

  @smoke @web @yw @cloud @gis-layers-sorting
  Scenario: Verify GIS layers available and its sorting functionality
    When I click on "GIS" button in the dma home page
    Then I verify each customer have the applicable list of GIS layers
    And  I verify GiS layers name sorted in Ascending order by default
    And  I verify GIS layers name sorted in Descending order on click of down sort button

  @smoke @web @yw @cloud @gis-layer-toggleButton-available
  Scenario: Verify GIS layers toggle buttons available
    When I click on "GIS" button in the dma home page
    Then I verify all GIS layers toggle button present under GIS tab

  @smoke @web @yw @cloud @gis-expand-row-button-available-for-layers
  Scenario Outline: Verify expand row button available for pipe & valve layers
    When I click on "GIS" button in the dma home page
    Then I verify "<layerName>" having expand row button under GIS tab

    Examples:
      | layerName |
      | Pipe      |
      | Valve     |

  @smoke @web @cloud @gis-heatmap-button-available-for-layers
  Scenario Outline: Verify heatmap button available for junction & pipe layers
    When I click on "GIS" button in the dma home page
    And  I verify "<layerName>" having heat map button under GIS tab

    Examples:
      | layerName |
      | Junction  |
      | Pipe      |

    # /*Sub zones details for customer yw and cloud */

  @smoke @web @yw @cloud @gis-subZones-tab-layers-available
  Scenario: Verify Sub zones tab layers available
    When I click on "GIS" button in the dma home page
    And  I click on SubZones tab under GIS button
    Then I verify all sub zones tab layers available

  @smoke @web @yw @cloud @gis-subZones-layers-toggle-button-available
  Scenario: Verify Sub zones tab layers toggle button available
    When I click on "GIS" button in the dma home page
    And  I click on SubZones tab under GIS button
    Then I verify all sub zones layers toggle button available

  @smoke @web @cloud @gis-subZones-layers-expandRow-button-available
  Scenario: Verify Sub zones tab layers expand row button available
    When I click on "GIS" button in the dma home page
    And  I click on SubZones tab under GIS button
    Then I verify all sub zones layers expand row button available

  @smoke @web @cloud @gis-subZones-layer-tooltip-and-download-button-available
  Scenario: Verify Sub zones tab layer download functionality and tool tip availability
    When I click on "GIS" button in the dma home page
    And  I click on SubZones tab under GIS button
    Then I verify download button link available for sub zones tab layer -WQ 10MLD Subzones
    And  I verify file downloaded on click of WQ 10MLD Subzones download button link
    And  I verify tool tip available for subzones layer -WQ 10MLD Subzones download button link

  @smoke @web @cloud @gis-popUp-details
  Scenario: Verify pop up details
    When I click on "GIS" button in the dma home page
    Then I click on name checkbox under GIS tab
    And  I verify popup pop up displayed with it's details

  @smoke @web @yw @cloud @gis-appTray-details-on-clickOf-header-checkBox
  Scenario: Verify GIS app button tray having multiple layers names and verify its legends
    When I click on "GIS" button in the dma home page
    Then I verify all GIS tab layers toggle buttons enabled on click of name checkbox & ok button from pop up
    And  I verify GIS app tray details on enabled all GIS tab layers
    And  I verify GIS layers Legends available once enabled all GIS tab layers

  @smoke @web @yw @cloud @gis-appTray-details-and-legend-details-for-enabledLayer
  Scenario Outline: Verify GIS app tray having enabled layer and verify its legend
    Given I click on "GIS" button in the dma home page
    Then  I click on toggle button for the "<layerName>"
    And   I click on "OK" button if pop up is displayed
    And   I verify the toggle button enabled for the GIS "<layerName>"
    And   I verify GIS app tray details for enabled GIS "<layerName>"
    And   I verify GIS legend details for enabled GIS "<layerName>"

    Examples:
      | layerName |
      | BulkMeter |
      | Hydrant   |
      | Junction  |
      | Pipe      |
      | Valve     |

    @cloud
    Examples:
      | layerName              |
      | Direct/Indirect Supply |

    @yw
    Examples:
      | layerName          |
      | PRV                |
      | T and End junction |


  @smoke @web @yw @cloud @gis-filter-details-available-for-layer
  Scenario Outline: Verify GIS layer filters details available for Pipe and Valve
    When I click on "GIS" button in the dma home page
    Then I click on toggle button for the "<layerName>"
    And   I click on "OK" button if pop up is displayed
    And  I verify the toggle button enabled for the GIS "<layerName>"
    And  I click on "<layerName>" expand row button under GIS tab
    And  I verify "<layerName>" filter details available under GIS tab

    Examples:
      | layerName |
      | Pipe      |
      | Valve     |

  @smoke @web @cloud @gis-legend-flow-direction-functionality
  Scenario: Verify the legend flow direction functionality
    When I click on "GIS" button in the dma home page
    Then I click on toggle button for the "Pipe"
    And  I verify the toggle button enabled for the GIS "Pipe"
    And  I verify the legend flow direction fuctionality

  @smoke @web @yw @cloud @gis-new-filter-functionality-for-layer
  Scenario Outline: Verify Add/delete new filter functionality for Pipe and Valve
    When I click on "GIS" button in the dma home page
    Then I click on toggle button for the "<layerName>"
    And   I click on "OK" button if pop up is displayed
    And  I verify the toggle button enabled for the GIS "<layerName>"
    And  I click on "<layerName>" expand row button under GIS tab
    And  I verify "<layerName>" add new filter functionality
    And  I verify "<layerName>" delete filter functionality

    Examples:
      | layerName |
      | Pipe      |
      | Valve     |

  @smoke @web @yw @cloud @gis-valve-filter-functionality
  Scenario Outline: Verify the Valve filter functionality
    When I click on "GIS" button in the dma home page
    Then I click on toggle button for the "Valve"
    And   I click on "OK" button if pop up is displayed
    And  I click on "Valve" expand row button under GIS tab
    And  I click and select "<Filter Type>" "<Value Type>" from drop down for the layer "Valve" And click on apply button
    And  I click on "OK" button from the filter pop up for "<Filter Type>" and "<Value Type>"
    And  I verify GIS app tray details contains "<Filter Type>" and "<Value Type>" for layer "Valve"
    And  I verify GIS legend details contains "<Filter Type>" for layer "Valve" filters

    Examples:
      | Filter Type | Value Type  |
      | None        |             |
      | Criticality | All         |
      | Criticality | LOW         |
      | Criticality | MEDIUM,HIGH,VERY HIGH |

    @yw
    Examples:
      | Filter Type | Value Type  |
      | None        |             |
      | Status      |   OPEN      |
      | Status      |   CLOSED    |
      | Status      | OPEN,CLOSED |

























