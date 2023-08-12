@PortalHealth @api
Feature: Verify health of portal 

@zonetheme_load @api @smoke
Scenario: Verifying the Zone theme with initial load 
	Then I verify the initial loading in "zone" theme in "sew" portal with default "7" days 
	
@transienttheme_load @api @smoke
Scenario: Verifying the Transient theme with initial load 
	Then I verify the initial loading in "transient" theme in "sew" portal with default "7" days 
	
@acoustictheme_load @api @smoke
Scenario: Verifying the Acoustic theme with initial load 
	Then I verify the initial loading in "acoustic" theme in "sew" portal with default "7" days 
	
@aimstheme_load @api @smoke
Scenario: Verifying the AIMS theme with initial load 
	Then I verify the initial loading in "aims" theme in "sew" portal with default "7" days 
	
@customerleakstheme_load @api @smoke
Scenario: Verifying the Customer Leaks theme with initial load 
	Then I verify the initial loading in "customer_leaks" theme in "sew" portal with default "7" days 
 