package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.gis.config.GISLoadColumnConfig;
import com.visenti.test.automation.api.modules.gis.config.GISLoadFilterConfig;
import com.visenti.test.automation.api.modules.gis.config.GISLoadRowConfig;
import com.visenti.test.automation.api.modules.gis.search.GISLoadMetaSearch;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.Given;

import java.io.IOException;

public class GISStepDefinitions {

	private RestAssuredHelper helper;

	public GISStepDefinitions(RestAssuredHelper helper) {
		this.helper = helper;
	}

	@Given("^I setup the request for the \"([^\"]*)\" api row config$")
	public void setUpRequestForRowConfigApi(String api) {
		GISLoadRowConfig gisLoadRowConfig = new GISLoadRowConfig(helper);
		gisLoadRowConfig.setUpRequestForRowConfig(api);
	}

	@Given("^I verify the default response for the GIS row Config Api$")
	public void verifyDefaultContentOfRowConfigApi() throws Exception {
		GISLoadRowConfig gisLoadRowConfig = new GISLoadRowConfig(helper);
		gisLoadRowConfig.verifyDefaultResponseContentForGISRowConfigApi();
	}

	@Given("^I verify all different applicable GIS are available for selection$")
	public void verifyAllGisIsPresent() {
		GISLoadRowConfig gisLoadRowConfig = new GISLoadRowConfig(helper);
		gisLoadRowConfig.verifyAllGisDetailsIsAvailable();
	}


	@Given("^I setup the request for the \"([^\"]*)\" api column config$")
	public void setUpRequestForColumnConfigApi(String api) {
		GISLoadColumnConfig gisLoadColumnConfig = new GISLoadColumnConfig(helper);
		gisLoadColumnConfig.setUpRequestForColumnConfig(api);
	}

	@Given("^I verify the default response for the GIS column Config Api$")
	public void verifyDefaultContentOfColumnConfigApi() throws Exception {
		GISLoadColumnConfig gisLoadColumnConfig = new GISLoadColumnConfig(helper);
		gisLoadColumnConfig.verifyDefaultResponseContentForGISColumnConfigApi();
	}

	@Given("^I verify the required columns are available$")
	public void verifyColumnContentOfTheApi() {
		GISLoadColumnConfig gisLoadColumnConfig = new GISLoadColumnConfig(helper);
		gisLoadColumnConfig.verifyColumnDetailsInTheApi();
	}

	@Given("^I setup the request for gis \"([^\"]*)\" meta search api$")
	public void setUpRequestForGisMetaApi(String gis) throws IOException {
		GISLoadMetaSearch gisLoadMetaSearch = new GISLoadMetaSearch(helper);
		gisLoadMetaSearch.setUpRequestForGisMeta(gis);
	}

	@Given("^I setup the request for gis \"([^\"]*)\" meta search api with \"([^\"]*)\"$")
	public void setUpRequestForGisIdSearchApi(String gisType, String gisId) throws IOException {
		GISLoadMetaSearch gisLoadMetaSearch = new GISLoadMetaSearch(helper);
		gisLoadMetaSearch.setUpRequestForGisIdSearch(gisType, gisId);
	}
	@Given("^I verify the default response for the GIS meta search Api$")
	public void verifyDefaultContentOfGisMetaApi() throws Exception {
		GISLoadMetaSearch gisLoadMetaSearch = new GISLoadMetaSearch(helper);
		gisLoadMetaSearch.verifyDefaultResponseContentForGISMetaSearchApi();
	}

	@Given("^I verify meta details of GIS \"([^\"]*)\" api$")
	public void verifyGISMetaDetails(String gis) {
		GISLoadMetaSearch gisLoadMetaSearch = new GISLoadMetaSearch(helper);
		//gisLoadMetaSearch.verifySpecificContentsOfGISMetaSearch(gis);
		gisLoadMetaSearch.getSomeDetails();
	}

	@Given("^I setup the request for the \"([^\"]*)\" api row config with parameter \"([^\"]*)\"$")
	public void setUpRequestForFilterConfigApi(String api, String paramValue) {
		GISLoadFilterConfig gisLoadFilterConfig = new GISLoadFilterConfig(helper);
		gisLoadFilterConfig.setUpRequestForFilterConfig(api,paramValue);
	}

	@Given("^I verify the default response for the GIS filter Config Api$")
	public void verifyDefaultResponseForFilterConfigApi() throws Exception {
		GISLoadFilterConfig gisLoadFilterConfig = new GISLoadFilterConfig(helper);
		gisLoadFilterConfig.verifyDefaultResponseContentForFilterConfigApi();
	}

	@Given("^I verify all the filters for the specific GIS \"([^\"]*)\" config API$")
	public void verifySpecificResponseForFiltersConfigApi(String filterType) throws Exception {
		GISLoadFilterConfig gisLoadFilterConfig = new GISLoadFilterConfig(helper);
		gisLoadFilterConfig.verifySpecificResponseContentForFilterConfigApi(filterType);
	}
}
