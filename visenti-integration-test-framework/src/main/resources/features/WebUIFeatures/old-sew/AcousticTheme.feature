@acoustic @web
Feature: Verify Acoustic theme functionality in the portal 
	As a user of the View 
	I want to load Acoustic theme and verify different functionality
	
Background: User should be on the Portal home Page
	Given I have successfully logged in to the "sew" portal	 
	
	@smoke @load_acoustic  @acoustic @web @smoke
Scenario: Verify Acoustic Theme selection 
	When  I click on the Themes Panel button on the side menu 
	And   I select "Last Night Min Energy" under "Acoustic" category  
	When I perform Logout action
	Then I should see a message "Logout successful" 