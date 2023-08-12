@module-login @web @view-portal
Feature: Verify login functionality of the portal 
	As an user of the View
    I want to login to check View functionality
    
Background: User is on the Login Page 
	Given I am on the "View" portal Login Page 
@smoke @web @uu @pub
Scenario: Verify successful login for a valid user
	When I enter valid credentials 
	And  I click on Login button 
	Then I should be on the Home Page of "View" portal 
	
@smoke @web	@uu 
Scenario: Verify successful login and logout 
	When I enter valid credentials 
	And  I click on Login button 
	Then I should be on the Home Page of "View" portal 
	When I perform Logout action
	Then I should see a message "Logout successful" 
	
