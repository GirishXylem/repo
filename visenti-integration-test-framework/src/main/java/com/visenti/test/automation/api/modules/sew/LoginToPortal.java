package com.visenti.test.automation.api.modules.sew;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class LoginToPortal {

    public static void verifyLoginPortal() {
        /**
         * disable url encoding to make sure API call will do as intended
         */
        RestAssured.urlEncodingEnabled = false;
        RestAssured.useRelaxedHTTPSValidation();

        //Loading theme APIs data
        loginToPortal();

    }

    /**
     * Theme API Call for getting all attributes
     */
    public static void loginToPortal() {

        RestAssured.baseURI = "https://sewuk-ums-api.eu.cloud.visenti.com/";
        RestAssured.basePath = "/account";

        // HTTP request
        RequestSpecification httpRequest = given()
                .queryParam("aggregated", "true")
                .header("X-Service-Customer", "sewuk")
                .header("X-Service-User", "visentiqa@gmail.com")
                .header("X-Service-Url", "https://sewuk.eu.cloud.visenti.com/dma-tool/")
                .header("X-Service-Ticket", "ST-15-DHEgWWEjM30UynCWMDJeVSvScbGCwj6gEJTGbHnHO0wFBZevLkOQtHh7EV-visenti-authentication-server")
                .request().log().all();
        Response response = httpRequest.request(Method.GET);
        //Getting response
        Assert.assertEquals(response.getStatusCode(), 200);
        Integer jsonResponseStatus = response.jsonPath().get("status");
        Assert.assertEquals(String.valueOf(jsonResponseStatus), "202");
        ArrayList<String> jsonResponse = response.jsonPath().get("data");
        assertTrue(!jsonResponse.isEmpty());
    }
//TODO: validate login response with exact values

}