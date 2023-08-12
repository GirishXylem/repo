@Ignore 
@aims @aimstheme 
@PortalHealth  @api
Feature: Verify Loading the AIMS Theme 
	As an user of the View
  I want to login to AIMS theme
  In order to check out AIMS theme functions

	
@aims_acoustic @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading AIMS :Acoustic theme 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "Acoustic" anomaly for "7" days 
	
@aims_customer_meter @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading AIMS :Customer Meter Leaks theme 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "Customer_Meter" anomaly for "7" days 
	
@aims_flow @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading AIMS :Flow theme 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "Flow" anomaly for "7" days 
	
@aims_pressure @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading AIMS :Pressure theme 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "Pressure" anomaly for "7" days 
	
@aims_transient @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading AIMS :Transient theme 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "Transient" anomaly for "7" days 
	
@aims_wqy @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading AIMS :WQY theme 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "WQY" anomaly for "7" days 
	
@aims_all @aims @api @smoke @regression
Scenario: Verifying AIMS User Preference API and Loading all Aims theme at once 
	Then I verify the getAIMSUserPreferencesAPI 
	And I verify the loading aims theme for "All" anomaly for "7" days 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    