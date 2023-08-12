package com.visenti.test.automation.api.modules.base;

import com.visenti.test.automation.helpers.*;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class RestBaseModule {

	protected RequestSpecification httpRequest;
	protected FilterableRequestSpecification filterableRequestSpecification;
	protected Response response;
	protected RestAssuredHelper helper;
	public Map<String, String> headersMap;
	public RestBaseModule(RestAssuredHelper helper)
	{
		this.helper=helper;
	}

	String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
	public void addCommonHeadersToTheRequest()
	{
		ConfigFileReader configFileReader = new ConfigFileReader();
		headersMap = new HashMap<>();
		headersMap.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
		headersMap.put("x-service-customer", configFileReader.getAPIHeaderValues("customer"));
		headersMap.put("x-service-url",configFileReader.getAPIHeaderValues("url"));
		if(RuntimeConfigSingleton.getInstance().getCustomerName().equalsIgnoreCase("pub")&&RuntimeConfigSingleton.getInstance().getExecutionEnvironment().equalsIgnoreCase("dev"))
		{
		headersMap.put("x-service-user", ConfigFileReader.getConfigProperty("client_id"));
		//headersMap.put("X-Service-AccessToken", ConfigFileReader.getConfigProperty(ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE));
		}
		else
		{
		headersMap.put("x-service-user", configFileReader.getAPIHeaderValues("user"));
		headersMap.put("x-service-ticket", configFileReader.getAPIHeaderValues("ticket"));
		}

		helper.addHeadersToTheRequest(headersMap);
	}

	public void setUpRequest(String api) {
		String apiUrlSubString, baseUri;
		assert customerName!=null;
		apiUrlSubString = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs(api);
		String environment = RuntimeConfigSingleton.getInstance().getExecutionEnvironment();
		if(customerName.equalsIgnoreCase("cloud") ||	customerName.equalsIgnoreCase("pub")){
			if(!environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + apiUrlSubString +"-" +PortalConfigManagement.getPortalPrefix() + "-view"
						+ PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + apiUrlSubString
					+ PortalConfigManagement.getPortalDomain();
			}
		} else if (customerName.equalsIgnoreCase("yantai")) {
			baseUri = "https://" + apiUrlSubString +"-" +PortalConfigManagement.getPortalPrefix()
					+ PortalConfigManagement.getPortalDomain();
			System.out.println(baseUri);
		} else  {
			if(environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalPrefix() + "-"
						+ PortalConfigManagement.getPortalDomain();
			}
		}

		helper.addBaseURIAndBasePathToTheRequest(baseUri, "");
		addCommonHeadersToTheRequest();
		helper.urlEncodingEnabled(false);
	}

	public void setUpRequestWithBasePath(String api,String basePath) {
		String apiUrlSubString , baseUri ;
		apiUrlSubString = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs(api);
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		String environment = RuntimeConfigSingleton.getInstance().getExecutionEnvironment();
		assert customerName!=null;
		if(customerName.equalsIgnoreCase("cloud") ||	customerName.equalsIgnoreCase("pub")){
			if(!environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + apiUrlSubString +"-" +PortalConfigManagement.getPortalPrefix() + "-view"
						+ PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + apiUrlSubString
						+ PortalConfigManagement.getPortalDomain();
			}
		} else  {
			if(environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalPrefix() + "-"
						+ PortalConfigManagement.getPortalDomain();
			}
		}
		helper.addBaseURIAndBasePathToTheRequest(baseUri, basePath);
		addCommonHeadersToTheRequest();
		helper.addHeaderToTheRequest("content-type","application/json");
		helper.urlEncodingEnabled(false);
	}

	public void setUpRequestWithParam(String ...api){
		setUpRequest(api[0]);
		helper.addQueryParamsToTheRequest(api[1],api[2]);
	}
}
