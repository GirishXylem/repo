@transient @web
Feature: Verify Transient theme functionality in the portal 
	As a user of the View 
	I want to load Transient theme and verify different functionality
	
Background: User should be on the Portal home Page
	Given I have successfully logged in to the "sew" portal	 
	
	@smoke @load_transient @transient @web @smoke
Scenario: Verify Transient Theme Selection
	When  I click on the Themes Panel button on the side menu 
	And   I select "Transient Patterns" under "Transient" category  
	When I perform Logout action
	Then I should see a message "Logout successful" 