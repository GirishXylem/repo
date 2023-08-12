@PortalHealth @TransientTheme @api
Feature: Verify Transient Theme main functionality flows 
	As a user of the View
	I want to load Transient theme to check different APIs

@transient_statistics @transient @api @smoke @regression
Scenario: Loading Transient Theme Statistics 
	Then I verify the "transient" theme metaSearch API 
	And I verify the rangeDeltaStatsAndCoverage API for "7" days 
	And I verify nearByLeaksByStationIds API for "7" days 
	
	
@transient_alerts @transient @api @regression
Scenario: Loading Transient Theme Alerts 
	Then I should see "transient" theme "alerts" data for "7" days 
	And  I verify the getAIMSUserPreferencesAPI 
	And  I verify AIMS config API response