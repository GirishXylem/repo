@login @web
Feature: Verify ums functionality of the portal
  As an user of the UMS
  I want to check UMS functionality

  Background: User is on the Login Page
    Given I am on the "ums" portal Login Page

  @sanity @web @ums @pub @uu
  Scenario: Verify ums page details successful by login and logout
    When I enter valid credentials
    And  I click on Login button
    Then I should be on the Home Page of "ums" portal
    And I verify the details in the page
    When I perform Logout action for ums portal
    Then I should see a message "Logout successful"