@loadContextualDataAPI @contextualdata @api
Feature: Verify Contextual Data (Customer Calls & Scheduled Jobs API )

@customercalls @api @smoke @regression
Scenario: Verify the Contextual Data API response for Customer Calls 
   And I verify the Contextual Data API for "CustomerCalls" for default "7" days 
    
    
@scheduledjobs @api @smoke @regression
Scenario: Verify the Contextual Data API response for Scheduled Jobs 
And I verify the Contextual Data API for "ScheduledJobs" for default "7" days     
      
   
   
   
   
