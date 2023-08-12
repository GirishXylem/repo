@search @web
Feature: Verify ums search functionality of the portal
  As an user of the UMS
  I want to search user and verify user is present in the list

  Background: User is on the Login Page
    Given I am on the "ums" portal Login Page

  @sanity @web @ums @uu @pub
  Scenario: Verify if user is available from the search
    When I enter valid credentials
    And  I click on Login button
    Then I should be on the Home Page of "ums" portal
    And  I click on the "Users" from the drawer list
    And  I search the logged in user to verify if user is present
    When I perform Logout action for ums portal
    Then I should see a message "Logout successful"