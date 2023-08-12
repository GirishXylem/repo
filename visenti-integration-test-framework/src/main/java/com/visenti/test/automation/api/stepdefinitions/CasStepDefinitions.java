package com.visenti.test.automation.api.stepdefinitions;

import static com.visenti.test.automation.constants.APIConstants.ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.cas.CasValidateAccessToken;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;

import cucumber.api.java.en.Given;

public class CasStepDefinitions {

	RestAssuredHelper helper;
	private CasValidateAccessToken objCasValidateAccessToken;

	public CasStepDefinitions(RestAssuredHelper helper) {
		this.helper = helper;
		objCasValidateAccessToken = new CasValidateAccessToken(this.helper);
	}

	@Given("^The service User has a valid Access Token$")
	public void verifyTheServiceUserHasAValidAccessToken() throws Exception {

		String executionEnvironment = RuntimeConfigSingleton.getInstance().getExecutionEnvironment();
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		String serviceUser = ConfigFileReader.getConfigProperty("api.header.x-service-user");
		
		Log.info("Execution Environment--"+executionEnvironment);
		Log.info("Customer Name--"+customerName);
		Log.info("X-ServiceUser-- "+serviceUser);

		if (customerName.equalsIgnoreCase("pub")){
			objCasValidateAccessToken.validateOauthProfileApiAndStoreValidAccessTokenInEnvPropertiesFile();
			Reporter.addStepLog("Valid Access Token :-"+ConfigFileReader.getConfigProperty(ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE)+" for the user '"+serviceUser+"'");
		} else {
			Log.info("The 'client_secret' not available for the user '" + serviceUser + "' for Customer '"
					+ customerName + "' in Environment '" + executionEnvironment + "'");
			Log.info("Hence, static 'X-Service-Ticket' used for now");
			Reporter.addStepLog("The 'client_secret' not available for the user '" + serviceUser + "' for Customer '"
					+ customerName + "' in Environment '" + executionEnvironment + "'");
			
			Reporter.addStepLog("Hence, static 'X-Service-Ticket' used for now");
		}

	}
}
