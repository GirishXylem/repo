@Ignore 
@PortalHealth @AcousticTheme @acoustic @api
Feature: Verify Acoustic Theme main functionality flows 
	As an user of the View
    I want to load acoustic theme to check different APIs
    
@acoustic @load_acoustic @api @smoke
Scenario: Loading Acoustic Theme 
	Then I load "acoustic" theme with "7" days 
	
@acoustic_statistics @acoustic @api @regression @smoke
Scenario: Loading Acoustic Theme Statistics 
	Then I should see "acoustic" theme "statistics" data for "7" days 

	# Update this method to test for few stations making scenario in to scenario outline
@acoustic_explorer @acoustic @api @regression @smoke
Scenario: Loading Acoustic Theme Explorer data
	Verifying the Acoustic theme Explorer API for default range for a given Station 
	And I verify the Explorer API data for the station "gutr_42" for default "7" days from current date 
	
@acoustic_alerts @acoustic @api @regression @smoke
Scenario: Loading Acoustic Theme Alerts 
	Then I should see "acoustic" theme "alerts" data for "7" days 
	And  I verify the getAIMSUserPreferencesAPI 
	And  I verify AIMS config API response 