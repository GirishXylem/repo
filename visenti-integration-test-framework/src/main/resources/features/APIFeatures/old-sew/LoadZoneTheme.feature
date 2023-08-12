@PortalHealth @zone @zonetheme @api
Feature: Verify Zone theme main functionality flows 

@zone @zone_themes_load @zonetheme @api @smoke
Scenario: Verifying loading the Zone Theme 
	Then I verify APIs up and & running within initial load 
	
@zone @zone_statistics_all @zonetheme @api @regression
Scenario: Verify the statistics/all response for all 3 API calls for different zone id's' along with other loading API calls 
	Then I verify the statistics/all api for the following zone ids for default "7" days 
		|"5c9882d1cff47e00018753b0"|"5c988351cff47e00018753b2"|"5c988421cff47e00018753b4"|"5c988443cff47e00018753b6"|"5c9884abcff47e00018753b8"|
	And  I verify the statistics/all api for the following zone ids for default "7" days 
		|"5c9884d9cff47e00018753ba"|"5c98850fcff47e00018753bc"|"5c98852ecff47e00018753be"|"5c988556cff47e00018753c0"|"5c988579cff47e00018753c2"|
	And  I verify the statistics/all api for the following zone ids for default "7" days 
		|"5c988598cff47e00018753c4"|"5c9885c8cff47e00018753c6"|"5c9b278acff47e00018753c8"|"5c9b2ef1cff47e00018753ce"|
		
		
@zone @zone_trends @zonetheme @api @regression 
Scenario: Verify the Trends api response for a given zoneId and sensor type 
    Then I verify the trends api for zoneId "5c9882d1cff47e00018753b0" and sensorType "current_demand" for default "7" days 
	And I verify the trends api for zoneId "5c9882d1cff47e00018753b0" and sensorType "minimum_night_inflow" for default "7" days 
	And I verify the trends api for zoneId "5c9882d1cff47e00018753b0" and sensorType "aggregate_consumption_smart" for default "7" days 
	And I verify the trends api for zoneId "5c9882d1cff47e00018753b0" and sensorType "total_inflow" for default "7" days 
	And I verify the trends api for zoneId "5c9882d1cff47e00018753b0" and sensorType "total_outflow" for default "7" days 
	And I verify the trends api for zoneId "5c9882d1cff47e00018753b0" and sensorType "average_pressure" for default "7" days 
	
	
@zone @zone_waterbalance @zonetheme @api @regression
Scenario: Verify the Zone theme waterbalance data api for different water balance models 
	Then I fetch the zone Ids from the getCustomerInfoAPI 
	And I verify the water balance api for type "data" and water balance model "top_to_bottom" for default "7" days 
	And I verify the water balance api for type "data" and water balance model "bottom_up" for default "7" days 
	And I verify water balance api for type "metadata" and water balance model "top_to_bottom" 
	And I verify water balance api for type "metadata" and water balance model "bottom_up" 
	And I verify water balance api for type "default" and water balance model "bottom_up" 
	And I verify water balance api for type "default" and water balance model "top_to_bottom" 
	
	
	
	
	
	#@zone @zone_get_kml  @zonetheme
	#Scenario: Verify the getKML api for all the zone id's
	#Then I fetch the zone Ids from the getCustomerInfoAPI for the customer "sewuk"
	#And  I verify the getKML api for all the zone Ids for customer "sewuk"
	
	
	#@zone @zone_themes_label  @zonetheme
	#Scenario: Verifying the Zone theme getThemesLabelAll API
	#Then I verify getThemesLabelAllAPI
	
	#@zone @zone_customer_info	@zonetheme
	#Scenario: Verifying the Zone theme getCustomer info API  for customer sewuk
	#Then I verify the getCustomerInfoAPI for the customer "sewuk"
	
	
	#@zone @zone_aims_user_preference @zonetheme
	#Scenario: Verify the AIMS User preference API
	#Then I verify the getAIMSUserPreferencesAPI