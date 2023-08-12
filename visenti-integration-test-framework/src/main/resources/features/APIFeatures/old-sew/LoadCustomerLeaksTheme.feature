@Ignore
@PortalHealth @CustomerLeaksTheme @customer_leaks @api
Feature: Verify CustomerLeaks Theme main functionality flows

@customer_leaks @customer_leaks_statistics @api @smoke @regression
Scenario: Loading Customer Leaks Theme Statistics
   Then I should see "customer_leaks" theme "statistics" data for "7" days

@customer_leaks @customer_leaks_trends @api @smoke @regression
Scenario: Verify Customer Leaks Theme Trends API for different incidentType and WaterLoss
Then I verify the Trends API for given incidentType and waterLoss for "7" days

@customer_leaks @customer_leaks_alerts @api @smoke @regression
Scenario: Verify Alerts and AIMS Config API for Customer Leaks
Then I should see "customer_leaks" theme "alerts" data for "7" days
And  I verify AIMS config API response



