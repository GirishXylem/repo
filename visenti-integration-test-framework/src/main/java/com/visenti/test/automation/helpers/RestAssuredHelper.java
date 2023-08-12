package com.visenti.test.automation.helpers;

import static com.visenti.test.automation.constants.APIConstants.SERVICE_TICKET_OR_TOKEN;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cucumber.listener.Reporter;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class RestAssuredHelper {

	private RequestSpecification httpRequest;
	private FilterableRequestSpecification filterableRequest;
	private Response response;
	private List<AssertionError> assertionErrorsList;
	private JsonPath jsonPath;

	public RequestSpecification getRequestSpecification() {
		return httpRequest;
	}

	public Response getResponse() {
		return response;
	}

	public FilterableRequestSpecification getFilterableRequestSpecification() {
		return filterableRequest;
	}

	public List<AssertionError> getAssertionErrors() {
		return assertionErrorsList;
	}

	public JsonPath getJsonPath() {
		return jsonPath != null ? jsonPath : response.jsonPath();
	}

	/**
	 * @param baseUri
	 * @param basePath
	 * @return
	 * 
	 * 		Adding baseUri and basePath to the Request. If the basePath passed is
	 *         empty only add the baseUrl
	 */
	public RequestSpecification addBaseURIAndBasePathToTheRequest(String baseUri, String basePath) {
		RestAssured.baseURI = baseUri;
		RestAssured.basePath = basePath;
		httpRequest = RestAssured.given();
		filterableRequest = (FilterableRequestSpecification) httpRequest;
		Log.info("Base URI " + filterableRequest.getBaseUri());
		Reporter.addStepLog("Base URI " + filterableRequest.getBaseUri());
		if (!basePath.isEmpty()) {
			Log.info("Base Path " + filterableRequest.getBasePath());
			Reporter.addStepLog("Base Path " + filterableRequest.getBasePath());
		}
		return httpRequest;

	}

	/**
	 * @param isEnabled
	 * @return
	 * 
	 * 		This method enables/disables the url encoding. If queryParams are
	 *         already encoded before they are provided to RestAssured need to
	 *         disable the encoding
	 */
	public RequestSpecification urlEncodingEnabled(Boolean isEnabled) {
		httpRequest = httpRequest.urlEncodingEnabled(isEnabled);
		if (isEnabled == false) {
			Log.info("Url Encoding is Disabled");
		}
		return httpRequest;

	}

	/**
	 * @param headersMap
	 * @return
	 * 
	 * 		Add headers Map to the RequestSpecification
	 */
	public RequestSpecification addHeadersToTheRequest(Map<String, String> headersMap) {
		Log.info("Following Headers added to the Request Specification:");
		Reporter.addStepLog("Following Headers added to the Request Specification:");
		for (Map.Entry<String, String> entry : headersMap.entrySet()) {

			httpRequest.header(entry.getKey(), entry.getValue());
			Log.info(" HeaderName '" + entry.getKey() + "' and HeaderValue '" + entry.getValue() + "'");
			Reporter.addStepLog(" HeaderName '" + entry.getKey() + "' and HeaderValue '" + entry.getValue() + "'");
		}
		return httpRequest;
	}

	/**
	 * @param headerName
	 * @param headerValue
	 * @return
	 * 
	 * 		Add single header to the RequestSpecification
	 */
	public RequestSpecification addHeaderToTheRequest(String headerName, Object headerValue) {
		httpRequest.header(headerName, headerValue);
		Log.info(" HeaderName '" + headerName + "' and HeaderValue '" + headerValue + "'");

		Reporter.addStepLog(" HeaderName '" + headerName + "' and HeaderValue '" + headerValue + "'");
		return httpRequest;
	}

	/**
	 * @param requestPayload
	 * @return Add Request Payload String to the RequestSpecification object
	 */
	public RequestSpecification addPayloadStringToTheRequest(String requestPayload) {
		httpRequest.body(requestPayload);
		Log.info("Added Payload " + requestPayload + " to the request");
		return httpRequest;
	}

	/**
	 * @param paramName
	 * @param paramValue
	 * @return Add Single QueryParam to the RequestSpecification Object
	 */
	public RequestSpecification addQueryParamsToTheRequest(String paramName, String paramValue) {
		if(paramValue.equals("cloud")){
			paramValue = "pub";
		}
		httpRequest.queryParam(paramName, paramValue);
		Log.info("QueryParam Name:'" + paramName + "' and Query Param Value '" + paramValue
				+ "' added to the Request Specification");
		Reporter.addStepLog("QueryParam Name:'" + paramName + "' and Query Param Value '" + paramValue
				+ "' added to the Request Specification");

		return httpRequest;
	}

	/**
	 * @param paramName
	 * @param paramValues
	 * @return
	 * 
	 * 		Add multiValued QueryParam to the RequestSpecification Object
	 */
	public RequestSpecification addQueryParamsToTheRequest(String paramName, Collection<?> paramValues) {
		httpRequest.queryParam(paramName, paramValues);
		Log.info("QueryParam Name:'" + paramName + "' and Query Param Value '" + paramValues
				+ "' added to the Request Specification");
		Reporter.addStepLog("QueryParam Name:'" + paramName + "' and Query Param Value '" + paramValues
				+ "' added to the Request Specification");

		return httpRequest;

	}

	/**
	 * @param queryParamsMap
	 * @return
	 * 
	 * 		Add QueryParams Map to the RequestSpecification
	 */
	public RequestSpecification addQueryParamsToTheRequest(Map<String, Object> queryParamsMap) {
		Log.info("Following Query Params added to the Request Specification:");
		Reporter.addStepLog("Following Query Params added to the Request Specification:");
		for (Map.Entry<String, Object> entry : queryParamsMap.entrySet()) {
			httpRequest.queryParam(entry.getKey(), entry.getValue());
			Log.info(" \t Param Name '" + entry.getKey() + "' and Param Value '" + entry.getValue() + "'");
			Reporter.addStepLog(" \t Param Name '" + entry.getKey() + "' and Param Value '" + entry.getValue() + "'");
		}
		return httpRequest;

	}

	/**
	 * @param pathParamsMap
	 * @return
	 * 
	 * 		Add PathParams Map to the RequestSpecification
	 */
	public RequestSpecification addPathParamsToTheRequest(Map<String, Object> pathParamsMap) {
		Log.info("Following Path Params added to the Request Specification:");
		Reporter.addStepLog("Following Path Params added to the Request Specification:");
		for (Map.Entry<String, Object> entry : pathParamsMap.entrySet()) {
			httpRequest.pathParam(entry.getKey(), entry.getValue());
			Log.info("Param Name '" + entry.getKey() + "' and Param Value '" + entry.getValue() + "'");
			Reporter.addStepLog("Param Name '" + entry.getKey() + "' and Param Value '" + entry.getValue() + "'");
		}
		return httpRequest;
	}

	/**
	 * @param formParamMap
	 * @return Add FormParams Map to the RequestSpecification
	 */
	public RequestSpecification addFormParamToTheRequest(Map<String, Object> formParamMap) {

		Log.info("Following Form Params added to the Request Specification:\n");
		Reporter.addStepLog("Following Form Params added to the Request Specification:\n");
		for (Map.Entry<String, Object> entry : formParamMap.entrySet()) {
			httpRequest.formParam(entry.getKey(), entry.getValue());
			Log.info("FormParam Name '" + entry.getKey() + "' and FormParam Value '" + entry.getValue() + "'");
			Reporter.addStepLog(
					"FormParam Name '" + entry.getKey() + "' and FormParam Value '" + entry.getValue() + "'");
		}
		return httpRequest;

	}

	/**
	 * @param requestType
	 * @param endPoint
	 * @param requestSpecification
	 * @param filterableRequestSpecification
	 * @return
	 * @throws Exception
	 * 
	 *                   In this method we are performing an HTTP Request ,based on
	 *                   the requestType on an end point
	 */
	public Response performRequestOnAnEndPoint(String requestType, String endPoint) throws Exception {

		switch (requestType.toLowerCase()) {
		case "get":
			System.out.println("Inside Get method");
			response = httpRequest.when().get(endPoint);
			break;

		case "post":
			System.out.println("Inside Post Method");
			response = httpRequest.when().post(endPoint);
			break;
		case "put":
			System.out.println("Inside Put Method");
			response = httpRequest.when().put(endPoint);
			break;
		case "delete":
			System.out.println("Inside Delete Method");
			response = httpRequest.when().delete(endPoint);
			break;

		default:
			Log.error("Wrong Request Type provided");
			throw new Exception("Wrong Request type provided");
		}
		Log.info("Performed " + filterableRequest.getMethod() + " Request on the end Point\n '"
				+ filterableRequest.getURI() + "'");
		Reporter.addStepLog("Performed " + filterableRequest.getMethod() + " Request on the end Point\n '"
				+ filterableRequest.getURI() + "'");
		// Change the LogLevel to Debug in log4j.properties file ,if you want to see
		// Response in Console or log file
		 Log.debug("Response Body :\n" + response.asString());
		//Log.info("Response Body :\n" + response.asString());
		return response;

	}

	/**
	 * @param expectedStatusCode In this method ,we are verifying the actual status
	 *                           code matches the expected status code
	 */
	public void verifyStatusCodeAsExpected(String expectedStatusCode) {
		Log.info("Expected Status Code " + expectedStatusCode);
		Log.info("Actual Status Code " + response.getStatusCode());

		response.then().assertThat().statusCode(Integer.parseInt(expectedStatusCode));

		Log.info("Successfully Asserted the Response Code to be " + expectedStatusCode);
		Reporter.addStepLog("Successfully Asserted the Response Code to be " + expectedStatusCode);

	}

	/**
	 * @param jsonPathExpression
	 * @return This method returns the Object value at given JsonPathExpression
	 */
	public Object getValueAtAGivenJsonPathExpression(String jsonPathExpression) {
		JsonPath jsonPath = getJsonPath();
		// JsonPath jsonPath=response.jsonPath();
		Log.info("JsonPath Expression: " + jsonPathExpression);
		Object value = jsonPath.get(jsonPathExpression);
		Log.debug("Value at the JsonPath Expression: " + jsonPathExpression + "=" + value);

		return value;
	}

	/**
	 * @param jsonPathExpression
	 * @param expectedDataTypeValueType
	 * @param response
	 * @throws Exception
	 * 
	 *                   This method verifies the data type of the value at a given
	 *                   jsonPathExpression is as expected
	 */
	public void verifyDataTypeOfValueAtAGivenJsonPathExpression(String jsonPathExpression, String expectedDataTypeValue)
			throws Exception {

		Object value = getValueAtAGivenJsonPathExpression(jsonPathExpression);
		Log.info("Value is of type:" + value.getClass().getName());

		switch (expectedDataTypeValue.toLowerCase()) {

		case "list":
			assertTrue(value instanceof List,
					"Data type of the value at the jsonPath expression  '" + jsonPathExpression + "' is not a List");

			break;
		case "map":
			assertTrue(value instanceof Map,
					"Data type of the value at the jsonPath expression  '" + jsonPathExpression + "' is not a Map");

			break;
		case "string":
			assertTrue(value instanceof String,
					"Data type of the value at the jsonPath expression '" + jsonPathExpression + "' is not a String");

			break;
		case "integer":
		case "int":
			assertTrue(value instanceof Integer, "Data type of the value at the jsonPath expression  '"
					+ jsonPathExpression + "' is not an Integer");

			break;
		case "long":
			assertTrue(value instanceof Long,
					"Data type of the value at the jsonPath expression  '" + jsonPathExpression + "' is not a Long");
			break;
		case "double":
			assertTrue(value instanceof Double,
					"Data type of the value at the jsonPath expression  '" + jsonPathExpression + "' is not a Double");
			break;
		case "float":
			assertTrue(value instanceof Float,
					"Data type of the value at the jsonPath expression  '" + jsonPathExpression + "' is not a Float");
			break;
		case "boolean":
			assertTrue(value instanceof Boolean,
					"Data type of the value at the jsonPath expression  '" + jsonPathExpression + "' is not a Boolean");
		case "null":
			assertTrue(value == null,
					"Data type of the value at the jsonPath expression " + jsonPathExpression + "' is not null ");
			break;
		default:
			Log.error("Wrong value type provided");
			throw new Exception("Wrong value type provided");

		}
		Log.info("Asserted the Json Value at the JSON Path Expression '" + jsonPathExpression + "' is of type '"
				+ expectedDataTypeValue + "'");
		Reporter.addStepLog("Asserted the Json Value at the JSON Path Expression '" + jsonPathExpression
				+ "' is of type '" + expectedDataTypeValue + "'");

	}

	/**
	 * @param jsonPathExpression
	 * @param expectedKeys
	 * 
	 *                           This method verifies ,the Map at a given jsonPath
	 *                           expression ,contains the List of expectedKeys
	 * 
	 */
	public void verifyMapContainsKeysAtAGivenJsonPathExpression(String jsonPathExpression, List<String> expectedKeys) {
		JsonPath jPath = getJsonPath();
		// JsonPath jPath=response.jsonPath();

		// Catching all the AssertionError while Iterating the Map
		// Creating a List of AssertionError
		assertionErrorsList = new ArrayList<AssertionError>();
		// At the given JSONPath Expression we have a Map ,so fetching the map
		Map<String, Object> map = jPath.getMap(jsonPathExpression);
		// Now Asserting the Map contains the expected Keys
		for (String key : expectedKeys) {
			try {
				assertTrue(map.containsKey(key),
						"The Map  at the JSONPath Expression '" + jsonPathExpression + "' contains the following Keys:"
								+ map.keySet() + " but do not contain the expected Key '" + key + "'");
				Log.info("The Map at the JSONPath Expression '" + jsonPathExpression + "' contains the Key '" + key
						+ "'");
			} catch (AssertionError e) {

				Log.error(e.getMessage());
				assertionErrorsList.add(e);

			}

		}
		if (!assertionErrorsList.isEmpty()) {
			// Reporting all the Assertion Failures while Iterating the Map
			reportAssertionErrorsDuringIteration(jsonPathExpression, "Map");
		} else {
			Log.info("Asserted all the expected keys '" + expectedKeys.toString()
					+ "' in Map at the JSONPathExpression '" + jsonPathExpression + "'");
			Reporter.addStepLog("Asserted all the expected keys '" + expectedKeys.toString()
					+ "' in Map at the JSONPathExpression '" + jsonPathExpression + "'");

		}

	}

	/**
	 * @param jsonPathExpression
	 * @param key                This method verifies ,the Map at a given jsonPath
	 *                           expression ,contains the expectedKey
	 */
	public void verifyMapContainsAKeyAtAGivenJsonPathExpression(String jsonPathExpression, String key) {
		JsonPath jPath = getJsonPath();
		// At the given JSONPath Expression we have a Map ,so fetching the map
		Map<String, Object> map = jPath.getMap(jsonPathExpression);

		assertTrue(map.containsKey(key),
				"The Map at the JSONPath Expression '" + jsonPathExpression + "' do not contain the Key '" + key + "'");
		Log.info("The Map at the JSONPath Expression '" + jsonPathExpression + "' contains the Key '" + key + "'");
		Reporter.addStepLog("The Map at the JSONPath Expression '" + jsonPathExpression + "' contains the Key '" + key + "'");
	}

	/**
	 * @param jsonPathExpression
	 * @param expectedKeysEachMap
	 * 
	 *                            This method verifies each Map in a List of Maps at
	 *                            given jsonPath expression: -is not empty -contains
	 *                            the expected Keys
	 */
	public void verifyKeysInEachMapOfAListAtAGivenJsonPathExpression(String jsonPathExpression,
			List<String> expectedKeysEachMap) {

		// Creating a List of AssertionError to catch All the AssertionError while
		// Iterating the
		// List of Maps
		assertionErrorsList = new ArrayList<AssertionError>();
		JsonPath jPath = getJsonPath();
		// The dataType of the key represented by jsonPath is a List,hence fetching the
		// List
		List<Map<String, Object>> actualList = jPath.getList(jsonPathExpression);

		int i = 0;
		for (Map<String, Object> actualMap : actualList) {
			// Asserting Each Map in the List is not empty
			boolean isMapEmpty;
			try {
				assertTrue(!actualMap.isEmpty(), "Map at JsonPath '" + jsonPathExpression + "[" + i + "]' is empty");
				Log.info("Map at JsonPath '" + jsonPathExpression + "[" + i + "]' is not empty");
				isMapEmpty = false;

			} catch (AssertionError e) {
				Log.error(e.getMessage());
				assertionErrorsList.add(e);
				isMapEmpty = true;

			}
			if (!isMapEmpty) {
				for (String key : expectedKeysEachMap) {
					try {
						assertTrue(actualMap.containsKey(key),
								"The  map,at JsonPath '" + jsonPathExpression + "[" + i
										+ "]' contains the following keys: " + actualMap.keySet()
										+ " \n but do not contain the key '" + key + "'");
						Log.info("The  map,at JsonPath '" + jsonPathExpression + "[" + i + "]'  contains the key '"
								+ key + "'");
					} catch (AssertionError e) {
						Log.error(e.getMessage());
						assertionErrorsList.add(e);

					}

				}
			}
			i++;
		}

		if (!assertionErrorsList.isEmpty()) {
			// Reporting all the Assertion Failures while Iterating the List of Maps
			reportAssertionErrorsDuringIteration(jsonPathExpression, "List");
		} else {
			Log.info("Asserted each Map in the List at the JsonPath Expression '" + jsonPathExpression
					+ "' is not Empty");
			Log.info("Asserted all the expected Keys '" + expectedKeysEachMap.toString()
					+ "' in each Map of the List at the JsonPath Expression '" + jsonPathExpression + "'");
			Reporter.addStepLog("Asserted each Map in the List at the JsonPath Expression '" + jsonPathExpression
					+ "' is not Empty");
			Reporter.addStepLog("Asserted all the expected Keys '" + expectedKeysEachMap.toString()
					+ "' \n in each Map of the List at the JsonPath Expression '" + jsonPathExpression + "'");
		}
	}

	/**
	 * @param headerName
	 * @param expectedHeaderValue
	 * 
	 *                            This method verifies the actual response header
	 *                            value is as expected
	 */
	public void verifyResponseHeaderValue(String headerName, String expectedHeaderValue) {

		response.then().assertThat().header(headerName, expectedHeaderValue);

		Log.info("The value of the Response Header '" + headerName + "' is as expected : " + expectedHeaderValue);
		Reporter.addStepLog(
				"The value of the Response Header '" + headerName + "' is as expected : " + expectedHeaderValue);

	}

	/**
	 * @param jsonPathExpression
	 * @param expectedValue
	 * @param validationType
	 * 
	 *                           This method verifies the Value at a given JsonPath
	 *                           Expression is equal to the expected value for a
	 *                           given validation type
	 */
	public void verifyValueAtAJsonPathExpressionForAValidationType(String jsonPathExpression, Object expectedValue,
			String validationType) {

		JsonPath jPath = getJsonPath();
		// JsonPath jPath=response.jsonPath();

		switch (validationType.toLowerCase()) {
		case "integer":
		case "int":
			assertEquals(jPath.getInt(jsonPathExpression), ((Integer) expectedValue).intValue(),
					"The actual value '" + jPath.getInt(jsonPathExpression) + "' at the jsonPath Expression '"
							+ jsonPathExpression + "'" + "for validationType '" + validationType
							+ "' is not equal to expected value ='" + expectedValue + "'");
			break;
		case "string":
			assertEquals(jPath.getString(jsonPathExpression), (String) expectedValue,
					"The actual value '" + jPath.getString(jsonPathExpression) + "' at the jsonPath Expression '"
							+ jsonPathExpression + "'" + "for validationType '" + validationType
							+ "' is not equal to expected value ='" + expectedValue + "'");
			break;
		case "char":
			assertEquals(jPath.getChar(jsonPathExpression), ((Character) expectedValue).charValue(),
					"The actual value '" + jPath.getChar(jsonPathExpression) + "' at the jsonPath Expression '"
							+ jsonPathExpression + "'" + "for validationType '" + validationType
							+ "' is not equal to expected value ='" + expectedValue + "'");
			break;
		case "float":
			assertEquals(jPath.getFloat(jsonPathExpression), ((Float) expectedValue).floatValue(),
					"The actual value '" + jPath.getFloat(jsonPathExpression) + "' at the jsonPath Expression '"
							+ jsonPathExpression + "'" + "for validationType '" + validationType
							+ "' is not equal to expected value ='" + expectedValue + "'");
			break;
		case "double":
			assertEquals(jPath.getDouble(jsonPathExpression), ((Double) expectedValue).doubleValue(),
					"The actual value '" + jPath.getDouble(jsonPathExpression) + "' at the jsonPath Expression '"
							+ jsonPathExpression + "'" + "for validationType '" + validationType
							+ "' is not equal to expected value ='" + expectedValue + "'");
			break;
		case "long":
			assertEquals(jPath.getLong(jsonPathExpression), ((Long) expectedValue).longValue(),
					"The actual value '" + jPath.getLong(jsonPathExpression) + "' at the jsonPath Expression '"
							+ jsonPathExpression + "'" + "for validationType '" + validationType
							+ "' is not equal to expected value ='" + expectedValue + "'");
			break;
		default:
			Log.error("Wrong Validation Type " + validationType + " passed");
			throw new RuntimeException("Wrong Validation Type " + validationType + " passed");

		}
		Log.info("The actual value at the jsonPath Expression '" + jsonPathExpression + "'" + "for validationType '"
				+ validationType + "' is as expected =" + expectedValue);
		Reporter.addStepLog("The actual value at the jsonPath Expression '" + jsonPathExpression + "'"
				+ "for validationType '" + validationType + "' is as expected =" + expectedValue);
	}

	/**
	 * @param expectedResponseTimeMilliSeconds This method verifies the Response
	 *                                         time is less than the
	 *                                         expectedResponseTime in milliseconds
	 */
	public void verifyResponseTimeLessThanExpectedMilliseconds(String expectedResponseTimeMilliSeconds) {

		Log.info("Actual Response time in milliseconds " + response.time());
		Reporter.addStepLog("Actual Response time in milliseconds " + response.time());
		response.then().assertThat().time(lessThan(Long.valueOf(expectedResponseTimeMilliSeconds)));
		Log.info("Asserted the actual Response time to be less than Expected '" + expectedResponseTimeMilliSeconds);
		Reporter.addStepLog(
				"Asserted the actual Response time to be less than Expected '" + expectedResponseTimeMilliSeconds);

	}

	/**
	 * @param jsonPathExpression This method verifies that the value at a given
	 *                           JsonPath expression is not Empty This is applicable
	 *                           for Type 'String','List' and 'Map' only
	 */
	@SuppressWarnings("unchecked")
	public void verifyValueNotEmptyAtAJsonPathExpression(String jsonPathExpression) {
		JsonPath jPath = getJsonPath();
		// JsonPath jPath=response.jsonPath();
		Object value = jPath.get(jsonPathExpression);

		if (value instanceof List) {

			List<Object> listValue = (List<Object>) value;
			assertTrue(!listValue.isEmpty(),
					"The List at the given JSONPath expression '" + jsonPathExpression + "' is empty");
			Log.info("Asserted the List at the given JSONPath expression '" + jsonPathExpression + "' is not empty");
			Reporter.addStepLog(
					"Asserted the List at the given JSONPath expression '" + jsonPathExpression + "' is not empty");

		} else if (value instanceof Map) {
			Map<String, Object> mapValue = (Map<String, Object>) value;
			assertTrue(!mapValue.isEmpty(),
					"The Map at the given JSONPath expression '" + jsonPathExpression + "' is empty");
			Log.info("Asserted the Map at the given JSONPath expression '" + jsonPathExpression + "' is not empty");
			Reporter.addStepLog(
					"Asserted the Map at the given JSONPath expression '" + jsonPathExpression + "' is not empty");

		} else if (value instanceof String) {

			String stringValue = (String) value;
			assertTrue(!stringValue.isEmpty(),
					"The String at the given JSONPath expression '" + jsonPathExpression + "' is empty");
			Log.info("Asserted the String at the given JSONPath expression '" + jsonPathExpression + "' is not empty");
			Reporter.addStepLog(
					"Asserted the String at the given JSONPath expression '" + jsonPathExpression + "' is not empty");
		}

	}

	/**
	 * @param jsonPathExpression This method verifies the Value not null at a Given
	 *                           jsonPath Expression
	 */
	public void verifyValueNotNullAtAGivenJsonPath(String jsonPathExpression) {
		JsonPath jPath = getJsonPath();
		Object value = jPath.get(jsonPathExpression);

		assertNotNull(value, "The value at the given JsonPath '" + jsonPathExpression + "' is null");
		Log.info("Asserted the value at the JsonPath '" + jsonPathExpression + "' is not null");
		Reporter.addStepLog("Asserted the value at the JsonPath '" + jsonPathExpression + "' is not null");
	}

	/**
	 * @param jsonPathExpression
	 * @param item
	 * 
	 *                           This method verfies the List at a JsonPath
	 *                           Expression ,contains an Item
	 */
	public void verifyListAtAJsonPathExpressionContainsAnItem(String jsonPathExpression, Object item) {
		List<Object> values = getJsonPath().getList(jsonPathExpression);
		Log.info("Values: " + values);

		assertTrue(values.contains(item), "The List at the JsonPath Expression '" + jsonPathExpression
				+ " do not contain the item '" + item + "'");
		Log.info("The List :" + values + " at the given Json Path Expression '" + jsonPathExpression
				+ "' contains the item '" + item + "'");
		Reporter.addStepLog("The List at the given Json Path Expression '" + jsonPathExpression
				+ "' contains the item '" + item + "'");

	}

	/**
	 * @param jsonPathExpression
	 * @param expectedItems
	 * 
	 *                           This method verifies that a List at the given
	 *                           JsonPath Expression contains a Collection of expected
	 *                           Items
	 */
	public void verifyListAtAJsonPathExpressionContainsExpectedItems(String jsonPathExpression,
			Collection<Object> expectedItems) {

		// Creating an ArrayList of AssertionError to catch all AssertionErrors while
		// iterating
		assertionErrorsList = new ArrayList<AssertionError>();
		List<Object> actualValues = getJsonPath().getList(jsonPathExpression);

		for (Object item : expectedItems) {
			try {
				assertTrue(actualValues.contains(item.toString()),
						"The Actual List :" + actualValues + " at the JsonPath Expression '" + jsonPathExpression
								+ " do not contain the item '" + item + "'");
				Log.info("The Actual List :" + actualValues + " at the given Json Path Expression '"
						+ jsonPathExpression + "' contains the item '" + item + "'");
			} catch (AssertionError e) {
				Log.error(e.getMessage());
				assertionErrorsList.add(e);
			}
		}

		if (!assertionErrorsList.isEmpty()) {
			// Reporting all the Assertion Failures during Iteration
			reportAssertionErrorsDuringIteration(jsonPathExpression, "List");

		} else {
			Log.info("The Actual List :" + actualValues + " at the given Json Path Expression '" + jsonPathExpression
					+ "' contains the  expected item '" + expectedItems + "'");
			Reporter.addStepLog("The Actual List :" + actualValues + " at the given Json Path Expression '"
					+ jsonPathExpression + "' contains the  expected items '" + expectedItems + "'");
		}

	}

	/**
	 * @param jsonPathExpression
	 * @param expectedKey
	 * @param expectedDataTypeValue
	 * @throws Exception
	 * 
	 *                   This method verifies a Map at a given JsonPathExpression
	 *                   contains expectedKey, The value of the Key is not null ,the
	 *                   data type of the value of the key is same as
	 *                   expectedDataTypeValue and the Value of the Key is not empty
	 *                   for type String,List,Map
	 */

	public void verifyKeyAndItsValueDataTypeInAMapAtJsonPath(String jsonPathExpression, String expectedKey,
			String expectedDataTypeValue) throws Exception {

		assertionErrorsList = new ArrayList<AssertionError>();
		JsonPath jPath = getJsonPath();

		// Fetching the Map at the jsonPath
		Map<String, Object> actualMap = jPath.getMap(jsonPathExpression);
		// Asserting Map contains expectedKeys
		assertTrue(actualMap.containsKey(expectedKey),
				"The  map,at JsonPath '" + jsonPathExpression + "' contains the following keys: '" + actualMap.keySet()
						+ "but do not contain the key '" + expectedKey + "'");
		Log.info("The  map,at JsonPath '" + jsonPathExpression + "'  contains the key '" + expectedKey + "'");
		String jsonPathKey = jsonPathExpression + "." + expectedKey;
		Object value = actualMap.get(expectedKey);
		Log.info("Value of the Key :" + expectedKey + " in the Map: " + value);

		// Asserting the value of the Key in the Map is not null
		assertNotNull(value, "The value of the Key at the JsonPath '" + jsonPathKey + "' is null");
		Log.info("The value of the Key at the JsonPath '" + jsonPathKey + "' is not null");

		// Asserting the data type of the value of the key is same as expected
		boolean isMatch = verifyDataTypeAsExpected(value, expectedDataTypeValue, jsonPathKey);
		if (!isMatch) {
			fail("Value of the key at the JsonPath '" + jsonPathKey + "'  is not a " + expectedDataTypeValue);
		}

		// Asserting the value of the Key is not empty ,if it is a String or a List or a
		// Map

		/*
		 * if (value instanceof String) { assertTrue(!((String) value).isEmpty(),
		 * "The value of the Key of type " + expectedDataTypeValue +
		 * " at the JsonPath '" + jsonPathKey + " is empty"); //
		 * Log.info("The value of Data Type 'String' for the key at the JsonPath '" + //
		 * jsonPathKey + " is not empty"); } else if (value instanceof Map) {
		 * assertTrue(!((Map<String, Object>) value).isEmpty(),
		 * "The value of the Key of type " + expectedDataTypeValue +
		 * " at the JsonPath '" + jsonPathKey + " is empty"); //
		 * Log.info("The value of Data Type 'Map' for the key at the JsonPath '" + //
		 * jsonPathKey + " is not empty"); } else if (value instanceof List) {
		 * assertTrue(!((List<Object>) value).isEmpty(), "The value of the Key of type "
		 * + expectedDataTypeValue + " at the JsonPath '" + jsonPathKey + " is empty");
		 * // Log.info("The value of Data Type 'List' for the key at the JsonPath '" +
		 * // jsonPathKey + " is not empty"); }
		 */
		verifyValueNotEmpty(value, jsonPathKey);
		/*
		 * Log.info("The value of the key of type '" + expectedDataTypeValue +
		 * "' at the JsonPath '" + jsonPathKey + " is not empty");
		 */
		Reporter.addStepLog("Asserted the Map at the JsonPath '" + jsonPathExpression + "' "
				+ " contains the expected Key '" + expectedKey + "'");
		Reporter.addStepLog("Asserted the dataType of the value of the Key: '" + expectedKey
				+ "' in the Map is as expected '" + expectedDataTypeValue
				+ " ,and \n value is not null and not empty for Type 'String', 'List' or Map");

	}

	/**
	 * @param jsonPathExpression
	 * @param expectedMapOfKeysAndDataType
	 * @throws Exception
	 * 
	 *                   This method verifies ,the Map at a JsonPath : -contains the
	 *                   expectedKeys ,dataType of the value of the keys is as
	 *                   expected ,Value of a key is not null ,Value of a key is not
	 *                   empty for type 'String', 'List' ,'Map'
	 * 
	 *                   Here we will pass a Map of ExpectedKeys and DataType as an
	 *                   argument to this method
	 * 
	 */

	public void verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(String jsonPathExpression,
			Map<String, String> expectedMapOfKeysAndDataType) throws Exception {
		JsonPath jPath = getJsonPath();

		// Fetching the Map at the jsonPath
		Map<String, Object> actualMap = jPath.getMap(jsonPathExpression);
		// Creating a List Object which will hold all the AssertionError's during
		// iteration
		assertionErrorsList = new ArrayList<AssertionError>();

		for (Map.Entry<String, String> entry : expectedMapOfKeysAndDataType.entrySet()) {
			String expectedKey = entry.getKey();
			String expectedDataTypeValue = entry.getValue();

			boolean mapContainsKey;
			// Asserting Actual Response Map contains expected Key
			try {
				assertTrue(actualMap.containsKey(expectedKey),
						"The  map,at JsonPath '" + jsonPathExpression + "' contains the following keys: '"
								+ actualMap.keySet() + "but do not contain the key '" + expectedKey + "'");
				Log.info("The  map,at JsonPath '" + jsonPathExpression + "'  contains the key '" + expectedKey + "'");
				mapContainsKey = true;
			} catch (AssertionError e) {
				Log.error(e.getMessage());
				assertionErrorsList.add(e);
				mapContainsKey = false;

			}
			Log.info("Map Contains Key " + expectedKey + " is " + mapContainsKey);

			// Getting the value if the Map contains expected Key
			if (mapContainsKey) {
				// Getting the value of a Key in the Actual Response Map
				String jsonPathKey = jsonPathExpression + "." + expectedKey;
				Object value = actualMap.get(expectedKey);
				Log.info("Value of the Key " + expectedKey + "=" + value);

				// Assert Not Null .
				// The value of the Key in the Actual Response Map is not null
				boolean isValueNull;
				try {
					assertNotNull(value, "The value of the Key at the JsonPath '" + jsonPathKey + "' is null");
					Log.info("The value of the Key at the JsonPath '" + jsonPathKey + "' is not null");
					isValueNull = false;
				} catch (AssertionError e) {
					Log.error(e.getMessage());
					assertionErrorsList.add(e);
					isValueNull = true;

				}

				if (!isValueNull) {
					// Asserting the value of Key ,matches the expectedDataType when value of key is
					// not null
					boolean isMatch = verifyDataTypeAsExpected(value, expectedDataTypeValue, jsonPathKey);

					// Asserting the Value of the Key for type String/List/Map is not Empty

					if (isMatch) {

						try {
							/*
							 * if (value instanceof String) { assertTrue(!((String) value).isEmpty(),
							 * "The value of the Key at the JsonPath '" + jsonPathKey + " is empty");
							 * 
							 * Log.info("The value of Data Type 'String' for the key at the JsonPath '" +
							 * jsonPathKey + " is not empty");
							 * 
							 * } else if (value instanceof Map) { assertTrue(!((Map<String, Object>)
							 * value).isEmpty(), "The value of the Key at the JsonPath '" + jsonPathKey +
							 * " is empty");
							 * 
							 * Log.info("The value of Data Type 'Map' for the key at the JsonPath '" +
							 * jsonPathKey + " is not empty");
							 * 
							 * } else if (value instanceof List) { assertTrue(!((List<Object>)
							 * value).isEmpty(), "The value of the Key at the JsonPath '" + jsonPathKey +
							 * " is empty");
							 * 
							 * Log.info("The value of Data Type 'List' for the key at the JsonPath '" +
							 * jsonPathKey + " is not empty");
							 * 
							 * }
							 */
							verifyValueNotEmpty(value, jsonPathKey);

							/*
							 * Log.info("The value of the key of type '" + expectedDataTypeValue +
							 * "' at the JsonPath '" + jsonPathKey + " is not empty");
							 */
						} catch (AssertionError e) {
							Log.error(e.getMessage());
							assertionErrorsList.add(e);

						}
					}

				}
			}
		}
		if (!assertionErrorsList.isEmpty()) {
			// Reporting all the AssertionError caught during iteration
			reportAssertionErrorsDuringIteration(jsonPathExpression, "Map");
		} else {
			Reporter.addStepLog("Asserted the Map at the JsonPath '" + jsonPathExpression + "' "
					+ " contains the expected Keys '" + expectedMapOfKeysAndDataType.keySet() + "'");
			Reporter.addStepLog("Asserted the dataType of the value of the Keys '"
					+ expectedMapOfKeysAndDataType.keySet()
					+ "' in the Map is as expected ,and \n value is not null for each key and not empty for Type 'String', 'List' or Map");
		}
	}

	/**
	 * @param jsonPathExpression
	 * @param expectedMapOfKeysAndDataType
	 * @throws Exception
	 * 
	 *                   This method verifies each Map in a List of Maps at a given
	 *                   jsonPath expression is not empty ,each Map contains the
	 *                   expected Keys ,dataType of the value of each key is as
	 *                   expected ,value of key is not null ,value of a key is not
	 *                   empty for type 'String' ,'List' or 'Map'
	 * 
	 *                   Here we will pass a Map of ExpectedKeys and DataType as an
	 *                   argument to this method
	 */

	public void verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(String jsonPathExpression,
			Map<String, String> expectedMapOfKeysAndDataType) throws Exception {

		JsonPath jPath = getJsonPath();
		// ArrayList object will hold all the AssertionError caught during iteration

		assertionErrorsList = new ArrayList<AssertionError>();
		// Getting the List of Maps at the jsonPath
		List<Map<String, Object>> actualList = jPath.getList(jsonPathExpression);

		int i = 0;
		for (Map<String, Object> actualMap : actualList) {
			// Verify each Map not empty
			boolean isMapEmpty;
			try {
				assertTrue(!actualMap.isEmpty(), "Map at JsonPath" + jsonPathExpression + "[" + i + "] is empty");
				Log.info("Map at JsonPath '" + jsonPathExpression + "[" + i + "]' is not empty");
				isMapEmpty = false;
			} catch (AssertionError e) {
				Log.error(e.getMessage());
				assertionErrorsList.add(e);
				isMapEmpty = true;
			}
			if (!isMapEmpty) {
				for (Map.Entry<String, String> entry : expectedMapOfKeysAndDataType.entrySet()) {

					// Getting the expectedKey and the dataType from the
					// expectedMapOfKeysAndDataType
					String expectedKey = entry.getKey();
					String expectedDataTypeValue = entry.getValue();

					// Asserting each Map contains the expected Key
					boolean mapContainsKey;
					try {
						assertTrue(actualMap.containsKey(expectedKey),
								"The  map,at JsonPath '" + jsonPathExpression + "[" + i
										+ "]' contains the following keys: " + actualMap.keySet()
										+ "    do not contain the key '" + expectedKey + "'");
						Log.info("The  map,at JsonPath '" + jsonPathExpression + "[" + i + "]'  contains the key '"
								+ expectedKey + "'");
						mapContainsKey = true;
					} catch (AssertionError e) {
						Log.error(e.getMessage());
						assertionErrorsList.add(e);
						mapContainsKey = false;
					}
					if (mapContainsKey) {
						String jsonPathKey = jsonPathExpression + "[" + i + "]." + expectedKey;

						Object value = actualMap.get(expectedKey);
						Log.info("Value of the Key " + expectedKey + "=" + value);

						// Assert Not Null
						// The value of the Key in the Actual Response Map is not null
						boolean isValueNull;
						try {
							assertNotNull(value, "The value of the Key at the JsonPath '" + jsonPathKey + "' is null");
							Log.info("The value of the Key at the JsonPath '" + jsonPathKey + "' is not null");
							isValueNull = false;
						} catch (AssertionError e) {
							Log.error(e.getMessage());
							assertionErrorsList.add(e);
							isValueNull = true;
						}

						if (!isValueNull) {
							// Asserting the value of Key ,matches the expectedDataType
							boolean isMatch = verifyDataTypeAsExpected(value, expectedDataTypeValue, jsonPathKey);

							if (isMatch) {
								// Asserting the Value of the Key for type String/List/Map is not Empty
								try {
									verifyValueNotEmpty(value, jsonPathKey);
									/*
									 * if (value instanceof String) { assertTrue(!((String) value).isEmpty(),
									 * "The value of the Key at the JsonPath '" + jsonPathKey + " is empty");
									 * 
									 * Log.info("The value of the key of type 'String' at the JsonPath '" +
									 * jsonPathKey + " is not empty");
									 * 
									 * } else if (value instanceof Map) { assertTrue(!((Map<String, Object>)
									 * value).isEmpty(), "The value of the Key at the JsonPath '" + jsonPathKey +
									 * " is empty");
									 * 
									 * Log.info("The value of the key of type 'Map' at the JsonPath '" + jsonPathKey
									 * + " is not empty");
									 * 
									 * } else if (value instanceof List) { assertTrue(!((List<Object>)
									 * value).isEmpty(), "The value of the Key at the JsonPath '" + jsonPathKey +
									 * " is empty");
									 * 
									 * Log.info("The value of the key of type 'List' at the JsonPath '" +
									 * jsonPathKey + " is not empty");
									 * 
									 * } Log.info("The value of the key of type " + expectedDataTypeValue +
									 * " at the JsonPath '" + jsonPathKey + " is not empty");
									 */
								} catch (AssertionError e) {
									Log.error(e.getMessage());
									assertionErrorsList.add(e);
								}
							}
						}
					}

				}
				i = i + 1;
			}
		}

		if (!assertionErrorsList.isEmpty()) {
			reportAssertionErrorsDuringIteration(jsonPathExpression, "List");
		} else {
			Log.info("Verified each Map in the List is not Empty");
			Log.info("Verified each Map in the List contains the expected Keys "
					+ expectedMapOfKeysAndDataType.keySet());
			Log.info("Verified data type of the value all the keys in each Map is as expected");
			Log.info("Verified value of the keys in each Map is not null ");
			Log.info("Verified the value of the keys in each Map is not empty for type 'String' or 'List' or 'Map' ");
			Reporter.addStepLog("Asserted each Map in the List at the JsonPath '" + jsonPathExpression
					+ "' is not Empty ,map contains the Keys \n '" + expectedMapOfKeysAndDataType.keySet() + "' ");
			Reporter.addStepLog("Asserted dataType of the value of the keys :'" + expectedMapOfKeysAndDataType.keySet()
					+ "' in eachMap is as expected,value not null for all keys \n and not empty for Type 'String' or 'List' or 'Map'");
		}

	}

	/**
	 * @param jsonPathExpression
	 * @param key
	 * @param expectedValue
	 * 
	 *    This method verifies that the value of a key in
	 *    Each Map of a List is equal to the expectedValue
	 * 
	 *  If the value of a key in all Maps of a List is not constant 
	 *  we can pass a List or Set of Expected Values for the key
	 *  and this method verifies that the actual value of a key
	 *  in all Maps matches with one of the expectedValues passed as a List or Set
	 *                          
	 *                          
	 */
	@SuppressWarnings("unchecked")
	public void verifyValueOfAKeyInEachMapOfAListAsExpected(String jsonPathExpression, String key,
			Object expectedValue) {
		JsonPath jPath = getJsonPath();
		// Create an ArrayList object to catch AssertionError during Iteration
		assertionErrorsList = new ArrayList<AssertionError>();
		// Getting the List of Maps at the jsonPath
		List<Map<String, Object>> listOfMaps = jPath.getList(jsonPathExpression);

		int i = 0;
		for (Map<String, Object> map : listOfMaps) {

			Object actualValue = map.get(key);

			if (expectedValue instanceof List || expectedValue instanceof Set) {
				try {
					assertTrue(((Set<Object>) expectedValue).contains(actualValue),
							"The actual Value =" + actualValue + " for the key at the jsonPath '" + jsonPathExpression
									+ "[" + i + "]." + key + "' do not match with one of the expected values :"
									+ expectedValue.toString());
					Log.info("The actual Value =" + actualValue + " for the key at the jsonPath '" + jsonPathExpression
							+ "[" + i + "]." + key + "' matches with one of the expected values :"
							+ expectedValue.toString());
				} catch (AssertionError e) {
					Log.error(e.getMessage());
					assertionErrorsList.add(e);
				}
			} else {
				try {
					assertEquals(actualValue, expectedValue,
							"The actual Value =" + actualValue + " for the key at the jsonPath '" + jsonPathExpression
									+ "[" + i + "]." + key + "' do not match with the expected value ="
									+ expectedValue);
					Log.info("The actual Value =" + actualValue + " for the key at the jsonPath '" + jsonPathExpression
							+ "[" + i + "]." + key + "' matches with the expected value =" + expectedValue);
				} catch (AssertionError e) {
					Log.error(e.getMessage());
					assertionErrorsList.add(e);
				}
			}
			i++;
		}
		if (!assertionErrorsList.isEmpty()) {
			reportAssertionErrorsDuringIteration(jsonPathExpression, "List");
		}

		else {
			Log.info("Verified the value of key '" + key + "' in each Map of the List at the JsonPath '"+jsonPathExpression+"' is as expected=" + expectedValue);

			Reporter.addStepLog(
					"Verified the value of key '" + key + "' in each Map of the List at the JsonPath '"+jsonPathExpression+"' is as expected=" + expectedValue);
		}
	}

	/**
	 * @param jsonPathExpression
	 * @param expectedKeyValues  
	 * 
	 * * This method verfies that the value of keys in
	 *  each Map of a List is equal to Expected Value
	 *  
	 *  If the value of a key/keys in all Maps of a List is not constant 
	 *  we can pass a List or Set of Expected Values for the keys
	 *  and this method verifies that the actual value of a key
	 *  in all Maps matches with one of the expectedValues passed as a List or Set
	 * 
	 *   
	 */
	@SuppressWarnings("unchecked")
	public void verifyValueOfKeysInEachMapOfAListAsExpected(String jsonPathExpression,
			Map<String, Object> expectedKeyValues) {
		JsonPath jPath = getJsonPath();

		// Create an object of ArrayList to catch AssertionError during Iteration
		assertionErrorsList = new ArrayList<AssertionError>();
		// Getting the List of Maps at the jsonPath
		List<Map<String, Object>> listOfMaps = jPath.getList(jsonPathExpression);

		int i = 0;
		for (Map<String, Object> map : listOfMaps) {
			String key;
			Object expectedValue;
			Object actualValue;
			for (Map.Entry<String, Object> entry : expectedKeyValues.entrySet()) {
				key = entry.getKey();
				expectedValue = entry.getValue();
				actualValue = map.get(key);

				//If expectedValue is an instance of List or Set ,this means that
				//value of key in each Map is not same and we have passed the List or Set of Expected Values
				
				//We will get the actual value of the key and verify that 
				//the expectedValues Set or List contains the actual Value
				if (expectedValue instanceof List || expectedValue instanceof Set) {

					try {
						assertTrue(((Set<Object>) expectedValue).contains(actualValue), "The actual Value ="
								+ actualValue + " for the key at the jsonPath '" + jsonPathExpression + "[" + i + "]."
								+ key + "' do not match with one of the expected values :" + expectedValue.toString());
						Log.info("The actual Value =" + actualValue + " for the key at the jsonPath '"
								+ jsonPathExpression + "[" + i + "]." + key
								+ "' matches with one of the expected values :" + expectedValue.toString());
					} catch (AssertionError e) {
						Log.error(e.getMessage());
						assertionErrorsList.add(e);
					}

				} else {
					//When the expected Value is not an instance of List or Set ,it means
					// the value of the key should be same in all the Maps and we are asserting that
					try {
						assertEquals(actualValue, expectedValue,
								"The actual Value =" + actualValue + " for the key at the jsonPath '"
										+ jsonPathExpression + "[" + i + "]." + key
										+ "' do not match with the expected value =" + expectedValue);
						Log.info("The actual Value =" + actualValue + " for the key at the jsonPath '"
								+ jsonPathExpression + "[" + i + "]." + key + "' matches with the expected value ="
								+ expectedValue);
					} catch (AssertionError e) {
						Log.error(e.getMessage());
						assertionErrorsList.add(e);
					}
				}
			}

			i++;
		}
		if (!assertionErrorsList.isEmpty()) {
			reportAssertionErrorsDuringIteration(jsonPathExpression, "List");
		}

		else {
			Log.info("Verified the value of the keys  " + expectedKeyValues.keySet()
					+ " in each Map of the List at the jsonPath '"+jsonPathExpression+"' is as expected ");

			Reporter.addStepLog("Verified the value of the keys  " + expectedKeyValues.keySet()
			+ " in each Map of the List at the jsonPath '"+jsonPathExpression+"' is as expected ");
		}
	}

	/**
	 * @param value
	 * @param expectedDataType
	 * @return
	 * @throws Exception
	 * 
	 *                   This method verifies the value is matching the
	 *                   expectedDataType
	 * 
	 *                   It returns true if the value matches the expected DataType
	 *                   else it returns false This is a reusable method used in
	 *                   other public methods of this class
	 */
	private boolean verifyDataTypeAsExpected(Object value, String expectedDataType, String jsonPathKey)
			throws Exception {

		try {
			switch (expectedDataType.toLowerCase()) {
			case "list":
				assertTrue(value instanceof List,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a List");
				break;
			case "map":
				assertTrue(value instanceof Map,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a Map");
				break;
			case "string":
				assertTrue(value instanceof String,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a String");
				break;
			case "integer":
			case "int":
				assertTrue(value instanceof Integer,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not an Integer");

				break;
			case "long":
				assertTrue(value instanceof Long,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a Long");
				break;
			case "double":
				assertTrue(value instanceof Double,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a Double");
				break;
			case "float":
				assertTrue(value instanceof Float,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a Float");
				break;
			case "boolean":
				assertTrue(value instanceof Boolean,
						"Value of the key at the JsonPath '" + jsonPathKey + "'  is not a Boolean");
				break;
			default:
				Log.error("Wrong value type provided");
				throw new Exception("Wrong value type provided");

			}
			Log.info("Value of the key at the JsonPath '" + jsonPathKey + "'  is of the expected type '"
					+ expectedDataType + "'");
			return true;
		} catch (AssertionError | Exception e) {
			Log.error(e.getMessage());
			if (e instanceof AssertionError) {
				assertionErrorsList.add((AssertionError) e);
				return false;
			} else {
				throw e;
			}
		}
	}

	/**
	 * @param value
	 * @param jsonPathKey
	 * 
	 *                    This is a private method which verifies the value at a key
	 *                    of type String ,List or Map is not empty .
	 * 
	 *                    This method is a reusable method and is used internally by
	 *                    other public methods in this class
	 */
	@SuppressWarnings({ "unchecked" })
	private void verifyValueNotEmpty(Object value, String jsonPathKey) {
		if (value instanceof String) {
			assertTrue(!((String) value).isEmpty(),
					"The value of the key of type 'String' at the JsonPath '" + jsonPathKey + " is empty");

			Log.info("The value of the key of type 'String' at the JsonPath '" + jsonPathKey + " is not empty");

		} else if (value instanceof Map) {
			assertTrue(!((Map<String, Object>) value).isEmpty(),
					"The value of the key of type 'Map' at the JsonPath '" + jsonPathKey + " is empty");

			Log.info("The value of the key of type 'Map' at the JsonPath '" + jsonPathKey + " is not empty");

		} else if (value instanceof List) {
			assertTrue(!((List<Object>) value).isEmpty(),
					"The value of the Key of type 'List' at the JsonPath '" + jsonPathKey + " is empty");

			Log.info("The value of the key of type 'List' at the JsonPath '" + jsonPathKey + " is not empty");

		}
	}

	/**
	 * @param jsonPath
	 * @param type
	 * 
	 *                 This method reports all the AssertionError caught during
	 *                 iteration of a list or a Map
	 */

	private void reportAssertionErrorsDuringIteration(String jsonPath, String type) {

		StringBuilder sb = new StringBuilder("The following assertions failed while iterating the '" + type
				+ "' at jsonPath '" + jsonPath + "': \n\n");
		for (AssertionError assertionError : assertionErrorsList) {

			sb.append(assertionError.getMessage()).append("\n\n");

		}
		throw new AssertionError(sb);

	}


    /**
     * Verifies the data types of values in a JSON list at the given JSON path expression.
     *
     * @param jsonPathExpression
     * @param datatype
     */
    public void verifyTheDataTypesOfValuesInAList(String jsonPathExpression, String datatype)
    {
        List value = response.jsonPath().get(jsonPathExpression);

        if(datatype.equalsIgnoreCase("integer"))
        {
            for(Object val:value)
            {
                Assert.assertTrue(val instanceof Integer,"Value is not an Integer");
            }
            Log.info("DataTypes at the given " + jsonPathExpression + " is verified");
        }else if(datatype.equalsIgnoreCase("string"))
        {
            for(Object val:value)
            {
                Assert.assertTrue(val instanceof String,"Value is not an String");
            }
            Log.info("DataTypes at the given " + jsonPathExpression + " is verified");
        }else if(datatype.equalsIgnoreCase("long"))
        {
            for(Object val:value)
            {
                Assert.assertTrue(val instanceof Long,"Value is not an Long");
            }
            Log.info("DataTypes at the given " + jsonPathExpression + " is verified");
        }else if(datatype.equalsIgnoreCase("float"))
        {
            for(Object val:value)
            {
                Assert.assertTrue(val instanceof Float,"Value is not an float");
            }
            Log.info("DataTypes at the given " + jsonPathExpression + " is verified");
        }else if(datatype.equalsIgnoreCase("double"))
        {
            for(Object val:value)
            {
                Assert.assertTrue(val instanceof Double,"Value is not an double");
            }
            Log.info("DataTypes at the given " + jsonPathExpression + " is verified");
        }else if(datatype.equalsIgnoreCase("boolean"))
        {
            for(Object val:value)
            {
                Assert.assertTrue(val instanceof Boolean,"Value is not an boolean");
            }
            Log.info("DataTypes at the given " + jsonPathExpression + " is verified");
			Reporter.addStepLog("DataTypes at the given " + jsonPathExpression + " is verified");
        }
    }

	/**
	 * Verifies that the sizes of two JSON arrays are equal.
	 * @param dataKey1
	 * @param dataKey2
	 */
    public void verifyTheTwoJsonArraySizesAreEqual(String dataKey1, String dataKey2)
    {
		// Get the sizes of the JSON arrays
        int count1 =response.jsonPath().getList(dataKey1).size();
        int count2 = response.jsonPath().getList(dataKey2).size();
        // Log the sizes of the JSON arrays
        Log.info("Number of elements in " + dataKey1 + " list: " + count1);
        Log.info("Number of elements in " + dataKey2 + " list: " + count2);

        // Assert the sizes and include custom assertion message
        String assertionMessage = "The sizes of '" + dataKey1 + "' and '" + dataKey2 + "' JSON arrays do not match.";
		try {
			Assert.assertEquals(count1, count2, assertionMessage);
			Log.info("JSON array sizes match: " + count1 + " elements in " + dataKey1 + " list and " +
					count2 + " elements in " + dataKey2 + " list.");
			Reporter.addStepLog("JSON array sizes match: " + count1 + " elements in " + dataKey1 + " list and " +
					count2 + " elements in " + dataKey2 + " list.");
		} catch (AssertionError e) {
			Log.info(assertionMessage);
			Reporter.addStepLog(assertionMessage);
			throw e;
		}
    }
}
