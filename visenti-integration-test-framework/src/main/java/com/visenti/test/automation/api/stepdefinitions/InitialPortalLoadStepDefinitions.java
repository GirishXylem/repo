package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.initialportalload.*;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.UnsupportedEncodingException;

public class InitialPortalLoadStepDefinitions {

	private UmsLoadAccountData objUmsAccount;
	private UmsLoadResources objUmsServiceValidates;
	private DmaLoadCommonAttributes objCommonAttributes;
	private DataLoadDisplayUnits objDisplayUnits;
	private GisLoadCustomerInfo objCustomerInfo;
	private DataLoadLiveStatus objLiveStatus;
	private GisLoadDeviceConfig objConfig;
	private RestAssuredHelper helper;
	private DMALoadAllThemes  objThemesAll;


	public InitialPortalLoadStepDefinitions(RestAssuredHelper helper) {
		this.helper = helper;
		objUmsAccount = new UmsLoadAccountData(this.helper);
		objUmsServiceValidates = new UmsLoadResources(this.helper);
		objCommonAttributes = new DmaLoadCommonAttributes(this.helper);
		objDisplayUnits = new DataLoadDisplayUnits(this.helper);
		objCustomerInfo = new GisLoadCustomerInfo(this.helper);
		objLiveStatus = new DataLoadLiveStatus(this.helper);
		objConfig = new GisLoadDeviceConfig(helper);
		objThemesAll = new DMALoadAllThemes(helper);

	}

	/*
	 * @Given("^I setup the request for the UMS account api with below Query Parameters$"
	 * ) public void setUpRequestForUMSAccountWithGivenQueryParams(DataTable
	 * queryParams) {
	 *
	 * objUMSAccount.setUpRequestWithGivenQueryParams(queryParams);
	 *
	 * }
	 */

	@Given("^I setup the request for the UMS account Api$")
	public void setUpRequestForUMSAccountApi() {
		objUmsAccount.setUpRequestWithGivenQueryParams();
	}

	@Given("^I verify the full response for UMS account Api$")
	public void verifyFullResponseForUMSAccountApi() throws Exception {

		objUmsAccount.verifyFullResponseForUMSAccountApi();
	}

	@Given("^I setup the request for the UMS /serviceValidates Api for an Application and a Customer$")
	public void setUpRequestForUMSServiceValidatesApi() {

		objUmsServiceValidates.setUpRequestWithQueryAndPathParams();

	}

	@Given("^I verify the full response for the UMS serviceValidates Api$")
	public void verifyFullResponseForUMSServiceValidatesApi() throws Exception {
		objUmsServiceValidates.verifyFullResponseForUMSServiceValidatesApi();

	}

	@Given("^I setup the request for the DMA commonattributes Api$")
	public void setUpRequestForDMACommonAttributesApi() {
		objCommonAttributes.setUpRequestForCommonAttributesApi();
	}

	@Then("^I verify the full response for DMA commattributes Api$")
	public void verifyFullResponseDMACommonAttributesApi() throws Exception {
		objCommonAttributes.verifyFullResponseForCommonAttributesApi();
	}

	@Given("^I setup the request for the Data displayunit Api$")
	public void setUpRequestForDataDisplayUnitAllApi() {
		objDisplayUnits.setUpRequestForDataApiDisplayUnit();

	}

	@Then("^I verify the full response for the Data displayunit Api$")
	public void verifyFullResponseForDataDisplayUnitAllApi() throws Exception {
		objDisplayUnits.verifyFullResponseForDisplayUnitApi();
	}

	@Given("^I setup the request for the \"([^\"]*)\" getCustomerInfo Api$")
	public void setUpRequestForGISGetCustomerInfoApi(String api) {
		objCustomerInfo.setUpRequestWithQueryParam(api);
	}

	@Then("^I verify the full response for the Gis customerInfo Api$")
	public void verifyFullResponseForGisCustomerInfoApi() throws Throwable {
		objCustomerInfo.verifyFullResponseForGisCustomerInfoApi();
	}

	@Given("^I setup the request for the Data /livestatus Api$")
	public void setUpRequestforDataLiveStatusApi() throws UnsupportedEncodingException {
		objLiveStatus.setUpRequestWithQueryParam();
	}

	@Then("^I verify the full response for the Data livestatus Api$")
	public void verifyFullResponseForDataLiveStatusApi() throws Exception {
		objLiveStatus.verifyFullResponseForLiveStatusApi();
	}

	@Given("^I get the list of DMA ID and name$")
	public void getAllDmaIdAndName() throws UnsupportedEncodingException {
		objCustomerInfo.getDmaIDAndName();
	}

	// GIS device config api

	@Given("^I setup the request for \"([^\"]*)\" Device config Api$")
	public void setUpRequestForGisDeviceConfigApi(String api) {
		objConfig.setUpRequestForGisConfig(api);
	}

	@Then("^I verify the common response for the device config Api$")
	public void verifyCommonResponseForDeviceConfigApi() throws Exception {
		objConfig.verifyCommonResponseForDeviceConfigApi();
	}

	@Then("^I verify the specific response for the device config Api$")
	public void verifySpecificResponseForDeviceConfigApi() throws Exception {
		objConfig.verifySpecificResponseForDeviceConfigApi();
	}

	@Given("^I setup the request for the \"([^\"]*)\" Pipes Zoom Api with the base path \"([^\"]*)\"$")
	public void setupTheRequestForTheGisPipesZoomApi(String api, String path) {
		GisLoadPipesZoom gisLoadPipesZoom = new GisLoadPipesZoom(helper);
		gisLoadPipesZoom.setUpRequestForGISZoomAPI(api, path);
	}

	@Given("^I setup the query param for gis zoom Api$")
	public void setUpQueryParamForZoomApi() {
		GisLoadPipesZoom gisLoadPipesZoom = new GisLoadPipesZoom(helper);
		gisLoadPipesZoom.setUpQueryParamForZoomApi();
	}

	@Then("^I verify the full response for the GIS Pipes Zoom Api$")
	public void VerifyTheFullResponseForTheGISPipesZoomApi() throws Exception {
		GisLoadPipesZoom gisLoadPipesZoom = new GisLoadPipesZoom(helper);
		gisLoadPipesZoom.verifyCommonResponseForGisZoomApi();
	}
	@And("^I verify the specific response for the GIS Pipes Zoom Api$")
	public void VerifyTheSpecificResponseForTheGISPipesZoomApi() throws Exception {
		GisLoadPipesZoom gisLoadPipesZoom = new GisLoadPipesZoom(helper);
		gisLoadPipesZoom.verifySpecificResponseForGisZoomApi();
	}

	@Given("^I setup the request for the \"([^\"]*)\" Api with the base path \"([^\"]*)\"$")
	public void SetupTheRequestForTheDmaAllThemesApi(String api, String path) throws Exception {
		DMALoadAllThemes dmaLoadAllThemes = new DMALoadAllThemes(helper);
		dmaLoadAllThemes.setUpRequestForDMAAllThemes(api, path);
	}

	@Then("^I verify the full response for DMA all themes Api$")
	public void VerifyTheFullResponseForDMAAllThemesApi() throws Exception {
		DMALoadAllThemes dmaLoadAllThemesAPI= new DMALoadAllThemes(helper);
		dmaLoadAllThemesAPI.verifyCommonResponseForDmaAllThemes();
	}

	@Then("^I verify the specific response for the DMA all themes Api$")
	public void VerifyTheSpecificResponseForTheDmaAllThemesApi() throws Exception {
		objThemesAll.verifySpecificResponseForDmaAllThemesApi();

	}
	@Then("^I verify the key and name having same value for the DMA all themes Api$")
	public void VerifyTheKeyAndNameHavingSameValueForTheDMAAllThemesApi() throws Exception {
		objThemesAll.verifyTheKeyAndNameHavingSameValueForDmaAllThemesApi();
	}


}