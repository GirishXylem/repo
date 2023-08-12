@web @module-simulation
Feature: Verify Simulation functionality of the portal
  As an user of the View
  I want to check different functionalities of View functionality specifically on simulation

  Background: User should be on the Portal home Page
    Given I have successfully logged in to the "View" portal

  @simulation-panel-validation
  Scenario: Verify Simulation panel on click of simulation button
    When I click on Simulation button in the dma home page
    Then I verify the title of the right panel to be "Simulations"
    And  I verify to see the simulation search bar is available
    And  I verify all expected radio buttons are available for selection
    And  I also verify create button is available for simulation creation

  @simulation-creation
  Scenario Outline: Create Simulation for different scenarios
    When I click on Simulation button in the dma home page
    Then I also verify create button is available for simulation creation
    And  I click on create button
    And  I verify the simulation pop up box details
    Then I enter the simulation "<scenario>" name and will click on the create button
    And  I select the "<scenario>" to create the simulation
    Then I search the "<gisValue>" for the simulation and click on it
    And  I verify the fields after selecting the "<scenario>" like "<gisValue>" selected
    And  I fill in all the fields required for simulation "<scenario>" with "<gisValue>" and perform the "<value>"
    Then I click on run simulation button

    @cloud
    Examples:
      | scenario                   | gisValue          | value |
      | Fire Flow                  | DPH_10685         | 2     |
      | Fire Flow                  | J_115218          | 2     |
      | Flushing                   | J_115218          | 3     |
      | Valve Operation            | V_499839,V_699350 | Close |
      | Valve Operation            | V_699350          | Close |
      | New Water Demand or Source | J_115218          | NWD   |
      | New Water Demand or Source | J_115218          | NWS   |
      | Pipe Isolation             | P_432530          |       |

    @simulation-verify-result
    Scenario Outline: Create Simulation for different scenarios
      When I click on Simulation button in the dma home page
      And  I click on create button
      Then I enter the simulation "<scenario>" name and will click on the create button
      And  I select the "<scenario>" to create the simulation
      Then I search the "<gisValue>" for the simulation and click on it
      And  I fill in all the fields required for simulation "<scenario>" with "<gisValue>" and perform the "<value>"
      Then I click on run simulation button
      And  I search for the simulation to be displayed on the search page
      Then I click on the simulation created
      And  I verify the components of the simulation result page for the "<scenario>"

      Examples:
        | scenario                   | gisValue          | value |
        | Valve Operation            | V_719218          | Close |
