@Ignore
@plotDevicesAPI @api
Feature: Plotting different Devices and Sensors 

@plotAcousticAPI @acoustic @api @smoke
Scenario Outline: Verify the Plotting API for Acoustic - 3 Stations and all sensor type combinations 

	And I verify the Plotting API for below stations and sensorType for default "7" days for "acoustic" stations
		|stationId |<stationId>  |
		|device    |<device>     |
		|sensorType|<sensorType> |
		|unit      |<unit>       |
		
	Examples: 
		|stationId|device  |sensorType|unit |
		|gutr_38  |acoustic|leakscore |score| 	
		|gutr_38  |acoustic|acoustic  |dB   |
		|gutr_51  |acoustic|leakscore |score|
		|gutr_51  |acoustic|acoustic  |dB   |
		|gutr_16  |acoustic|leakscore |score|
		|gutr_16  |acoustic|acoustic  |dB   |
		
@plotBtryAPI @btry @api @smoke
Scenario Outline: Verify the Plotting API for Btry - 3 Stations and all sensor type combinations 

	And I verify the Plotting API for below stations and sensorType for default "7" days for "btry" stations
	|stationId |<stationId>  |
	|device    |<device>     |
	|sensorType|<sensorType> |
	|unit      |<unit>       |
				
	Examples: 
	|stationId|device  |sensorType|unit |
	|gutr_38  |btry    |btry      |volt | 	
	|gutr_51  |btry    |btry      |volt |
	|gutr_16  |btry    |btry      |btry |	
	
@plotCustomerMeterAPI @customermeter @api @smoke
Scenario Outline: Verify the Plotting API for CustomerMeters - 3 Stations and all sensor types 

	And I verify the Plotting API for below stations and sensorType for default "7" days for "customermeter" stations
	|stationId |<stationId>  |
	|device    |<device>     |
	|sensorType|<sensorType> |
	|unit      |<unit>       |
						
	Examples: 
	|stationId     |device          |sensorType   |unit    |
	|meter_101716  |customermeter   |reading      |reading | 	
	|meter_101716  |customermeter   |consumption  |litres  |
	|meter_100054  |customermeter   |reading      |reading |
	|meter_100054  |customermeter   |consumption  |litres  | 	
	|meter_101081  |customermeter   |reading      |reading |
	|meter_101081  |customermeter   |consumption  |litres  | 
						
@plotFlowAPI @flow @api @smoke
Scenario Outline: Verify the Plotting API for Flow - 3 Stations and all sensor types 

	And I verify the Plotting API for below stations and sensorType for default "7" days for "flow" stations
	|stationId |<stationId>  |
	|device    |<device>     |
	|sensorType|<sensorType> |
	|unit      |<unit>       |
							
	Examples: 
	|stationId		 |device    	|sensorType|unit |
	|sewscada_202680 |  flow    	|flow1     |L/s  | 	
	|sewscada_202391 |  flow    	|flow1     |L/s  |
	|sewscada_4000767|  flow    	|flow1     |L/s  |
			

@plotTransientAPI @transient @api @smoke
Scenario Outline: Verify the Plotting API for Transient - 3 Stations and all sensor types 

	And I verify the Plotting API for below stations and sensorType for default "7" days for "transient" stations
	|stationId |<stationId>  |
	|device    |<device>     |
	|sensorType|<sensorType> |
	|unit      |<unit>       |
										
	Examples: 
	|stationId |device      | sensorType |unit	  |
	| syri_22  |transient   | pressure   | mH 	  |
	| syri_22  |transient   | minpressure| mH 	  |
	| syri_22  |transient   | maxpressure| mH     |
	| syri_22  |transient   | maxseverity|severity|
	| syri_24  |transient   | pressure   | mH 	  |
	| syri_24  |transient   | minpressure| mH 	  |
	| syri_24  |transient   | maxpressure| mH 	  |
	| syri_24  |transient   | maxseverity|severity|
	| syri_235 |transient   | pressure   | mH 	  |
	| syri_235 |transient   | minpressure| mH 	  |
	| syri_235 |transient   | maxpressure| mH 	  |
	| syri_235 |transient   | maxseverity|severity|
										
@plotWQYAPI @wqy @api @smoke
Scenario Outline: Verify the Plotting API for WQY - 3 Stations and all sensor types 

	And I verify the Plotting API for below stations and sensorType for default "7" days for "wqy" stations
	|stationId |<stationId>  |
	|device    |<device>     |
	|sensorType|<sensorType> |
	|unit      |<unit>       |	            
	Examples: 
	|stationId |device  | sensorType |unit	  					|
    |syri_13   |wqy     |  temp      |degree-celcius			|
	|syri_13   |wqy     |   cty      |micro-Siemens/centimeter  |
	|syri_13   |wqy     | chlorine   |ppm                       |
	|syri_13   |wqy     | turbidity  |NTU                       |
	|syri_16   |wqy     | temp       |degree-celcius            |
	|syri_16   |wqy     | ph         |pH                        |
	|syri_16   |wqy     | oxygen     |ppm                       |
	|syri_11   |wqy     |  temp      |degree-celcius			|
	|syri_11   |wqy     |   cty      |micro-Siemens/centimeter  |
	|syri_11   |wqy     | chlorine   |ppm                       |
	|syri_11   |wqy     | turbidity  |NTU                       | 
												
	