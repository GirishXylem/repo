package com.visenti.test.automation.api.modules.cas;

import static com.visenti.test.automation.constants.APIConstants.CAS_ACCESS_TOKEN_QUERY_PARAM_GRANT_TYPE_NAME;
import static com.visenti.test.automation.constants.APIConstants.CAS_ACCESS_TOKEN_QUERY_PARAM_GRANT_TYPE_VALUE;
import static com.visenti.test.automation.constants.APIConstants.CAS_ACCESS_TOKEN_RESOURCE_PATH;
import static com.visenti.test.automation.constants.APIConstants.CAS_BASE_PATH;
import static com.visenti.test.automation.constants.APIConstants.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;

import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class CasGetAccessToken extends RestBaseModule {

	public CasGetAccessToken(RestAssuredHelper helper) {
		super(helper);
	}

	private void setUpRequestForCasOAuth2AccessToken() {
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

		// Disabling the urlEncoding
		httpRequest.urlEncodingEnabled(false);
		Log.debug("Disabled url encoding");
		Log.info("Base Uri : " + filterableRequestSpecification.getBaseUri());
		Log.info("Base Path : " + filterableRequestSpecification.getBasePath());

		String queryParamClientSecretValue = ConfigFileReader.getConfigProperty(CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_SECRET_NAME);
		String queryParamClientIdValue = ConfigFileReader.getConfigProperty(CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_ID_NAME);

		Map<String, Object> queryParamsMap = new HashMap<String, Object>();

		queryParamsMap.put(CAS_ACCESS_TOKEN_QUERY_PARAM_GRANT_TYPE_NAME, CAS_ACCESS_TOKEN_QUERY_PARAM_GRANT_TYPE_VALUE);
		queryParamsMap.put(CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_SECRET_NAME, queryParamClientSecretValue);
		queryParamsMap.put(CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_ID_NAME, queryParamClientIdValue);

		// Again not using helper methods for this Request as they contain the step Logs
		// helper.addQueryParamsToTheRequest(queryParamsMap);
		
		httpRequest.queryParams(queryParamsMap);
		Log.info("QueryParams for CAS GetAccessToken API \n" + filterableRequestSpecification.getQueryParams());
		}

	public void performPOSTRequestOnEndPoint() throws Exception {
		String endPoint = CAS_ACCESS_TOKEN_RESOURCE_PATH;
		response = httpRequest.post(endPoint);
		Log.info("Performed '" + filterableRequestSpecification.getMethod() + "' Request on the end Point\n '"
				+ filterableRequestSpecification.getURI() + "'");
		System.out.println(response);
		Log.debug(response.asString());

	}

	public void verifyResponseForAccessTokenApi() {
		// Asserting the status code to be 200
		response.then().assertThat().statusCode(STATUS_CODE_200);
		Log.debug("Asserted the Status Code to be " + STATUS_CODE_200);

		response.then().assertThat().contentType(CONTENT_TYPE_TEXT_PLAIN);
		Log.debug("Asserted the Response Header 'Content-Type'=" + CONTENT_TYPE_TEXT_PLAIN);

		// Asserting the Response body startsWith the String 'access_token='
		response.then().assertThat().body(startsWith("access_token="));
		Log.debug("Asserted the Response body starts with 'access_token='");

		// Asserting the Response body contains the String '&expires_in='
		response.then().assertThat().body(containsString("&expires_in="));
		Log.debug("Asserted the Response body contains String  '&expires_in='");
		
		Log.info("Asserted successfully the Response for the API - "+filterableRequestSpecification.getURI() );
	}

	public String getAccessToken() throws Exception {
		setUpRequestForCasOAuth2AccessToken();
		performPOSTRequestOnEndPoint();
		verifyResponseForAccessTokenApi();

		// Getting only the value of the access token from the Response String
		String accessToken = response.asString().replace("access_token=", "");

		// Splitting the accessToken by '&' and getting the first part ,ignoring the
		// 'expired_in=
		accessToken = accessToken.split("&")[0];
		Log.info("AccessToken fetched from Response=" + accessToken);

		return accessToken;

	}

}
