@Ignore 
@loadingGisLayers @GIS @api 
Feature: Verify Loading of all GIS Layers 

@loadingHydrant @hydrant @api @smoke
Scenario: Verify loading GIS layer :HYDRANT 
	And I verify the Loading GIS API of type "hydrant" 
	
@loadingValve @valve @api @smoke
Scenario: Verify loading GIS layer :VALVE 
	And I verify the Loading GIS API of type "valve" 
	
@loadingJunction @junction @api @smoke
Scenario: Verify loading GIS layer :JUNCTION 
	And I verify the Loading GIS API of type "junction" 
	
@loadingPipe @pipe @api @smoke
Scenario: Verify loading GIS layer :PIPE 
	And I verify the Loading GIS API of type "pipe" 
	
	
    