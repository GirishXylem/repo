package com.visenti.test.automation.api.modules.cas;

import static com.visenti.test.automation.constants.APIConstants.CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_ID_NAME;
import static com.visenti.test.automation.constants.APIConstants.CAS_BASE_PATH;
import static com.visenti.test.automation.constants.APIConstants.CAS_PROFILE_INVALID_RESPONSE_KEY;
import static com.visenti.test.automation.constants.APIConstants.CAS_PROFILE_QUERY_PARAM_ACCESS_TOKEN_NAME;
import static com.visenti.test.automation.constants.APIConstants.CAS_PROFILE_RESOURCE_PATH;
import static com.visenti.test.automation.constants.APIConstants.CAS_PROFILE_VALID_RESPONSE_KEY;
import static com.visenti.test.automation.constants.APIConstants.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;

import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class CasValidateAccessToken extends RestBaseModule {

	String accessToken=ConfigFileReader.getConfigProperty(ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE);

	public CasValidateAccessToken(RestAssuredHelper helper) {
		super(helper);

	}

	public void validateOauthProfileApiAndStoreValidAccessTokenInEnvPropertiesFile() throws Exception {
		Log.info("access token---------"+accessToken);
		
		if (!isAccessTokenValid()) {
			accessToken = new CasGetAccessToken(helper).getAccessToken();
			//Using recursion here ,till the oauth2.0/profile api is successfully validated for the Access Token
			validateOauthProfileApiAndStoreValidAccessTokenInEnvPropertiesFile();
			storeAccessTokenInPropertiesFile();
			
		} else {
			Log.info("Valid Access Token " + accessToken);
			
		}
		
	}

	public void storeAccessTokenInPropertiesFile() throws Exception {
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		ConfigFileReader.storePropertyKeyAndValue(ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE, accessToken, true);
		Log.info("Auth Token stored in env Properties file as '" + customerName +"."+ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE+"'");

	}

	public void setUpRequestForCasValidateAccessToken() throws Exception {
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		// Retrieving the propertyKey 'customer'.cas.prefix for a customer and env
		String casPrefix = ConfigFileReader.getConfigProperty("cas.prefix");
		Log.debug("The value of the property key '" + customerName + ".cas.prefix' =" + casPrefix);

		String baseUri = "https://" + casPrefix + PortalConfigManagement.getPortalDomain();
		String basePath = CAS_BASE_PATH;

		// Adding baseUri and basePath
		// Not using the helper methods as it contains Reporter Step Logs ,which will
		//create too many Step Logs in the Background Step,which isnt required.
		RestAssured.useRelaxedHTTPSValidation();
		httpRequest = RestAssured.given().relaxedHTTPSValidation();
		httpRequest.baseUri(baseUri).basePath(basePath);

		filterableRequestSpecification = (FilterableRequestSpecification) httpRequest;

		httpRequest.urlEncodingEnabled(false);

		Log.info("Base Uri :" + filterableRequestSpecification.getBaseUri());
		Log.info("Base Path : " + filterableRequestSpecification.getBasePath());

		//httpRequest.queryParam(CAS_PROFILE_QUERY_PARAM_ACCESS_TOKEN_NAME, accessToken);
		  httpRequest.queryParam(CAS_PROFILE_QUERY_PARAM_ACCESS_TOKEN_NAME, accessToken);							
		Log.info("QueryParams for CAS ValidateAccessToken API \n" + filterableRequestSpecification.getQueryParams());

	}

	public void performGetRequestOnCasProfileEndPoint() {
		String endPoint = CAS_PROFILE_RESOURCE_PATH;
		response = httpRequest.get(endPoint);
		Log.info("Performed '" + filterableRequestSpecification.getMethod() + "' Request on the end Point\n '"
				+ filterableRequestSpecification.getURI() + "'");
		System.out.println(response.asString());
		Log.debug(response.asString());

	}

	public boolean isAccessTokenValid() throws Exception {

		Log.debug((this.accessToken));
		setUpRequestForCasValidateAccessToken();
		performGetRequestOnCasProfileEndPoint();

		try {
			response.then().assertThat().statusCode(STATUS_CODE_200);
			Log.debug("Asserted Status Code to be " + STATUS_CODE_200);
			response.then().assertThat().body("$", hasKey(CAS_PROFILE_VALID_RESPONSE_KEY)).body("$",
					not(hasKey(CAS_PROFILE_INVALID_RESPONSE_KEY)));
			Log.debug("Asserted Response body has key :'" + CAS_PROFILE_VALID_RESPONSE_KEY + "' and do not have key :'"
					+ CAS_PROFILE_INVALID_RESPONSE_KEY + "'");
			// Asserting the value of 'id' key same as 'client_id' query Param in
			// /oauth2.0/accessToken api
			response.then().assertThat().body("id",
					equalTo(ConfigFileReader.getConfigProperty(CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_ID_NAME)));
			Log.debug("Asserted the value of 'id' key in the Response to be equal to '"
					+ ConfigFileReader.getConfigProperty(CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_ID_NAME + "'"));
			Log.info("Asserted :Access Token is valid");
			return true;

		} catch (AssertionError e) {
			Log.warn("Access token Invalid ");
			return false;
		}
	}

}
