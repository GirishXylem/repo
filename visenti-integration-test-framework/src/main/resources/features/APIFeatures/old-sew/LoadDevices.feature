@Ignore
@sew @loadDevicesAPI @api
Feature: Verify Loading all Devices

@acoustic @loadAcousticDeviceAPI @api @smoke
Scenario: Verify loading Acoustic devices in SEW portal 
    And I verify the Loading Devices API for "Acoustic" 
	
@btry @loadBtryDeviceAPI @api @smoke
Scenario: Verify loading Btry devices in SEW portal 
    And I verify the Loading Devices API for "Btry" 
	
	
@customermeter @loadCustomerMeterDeviceAPI @api @smoke
Scenario: Verify loading CustomerMeter devices in SEW portal 
    And I verify the Loading Devices API for "CustomerMeter" 
	
@flow @loadFlowDeviceAPI @api @smoke
Scenario: Verify loading Flow devices in SEW portal 
   	And I verify the Loading Devices API for "Flow" 
	
@transient @loadTransientDeviceAPI @api @smoke
Scenario: Verify loading Transient devices in SEW portal 
	And I verify the Loading Devices API for "Transient" 
	
@wqy @loadWQYDeviceAPI @api @smoke
Scenario: Verify loading WQY devices in SEW portal 
	And  I verify the Loading Devices API for "WQY" 
	
	
	
	
	
	
	
	