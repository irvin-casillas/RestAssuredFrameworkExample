package com.unosquare;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ApiCore {

    String baseURI = "https://reqres.in/api";

    public Response PostOperation(String filePath, String url) throws IOException, ParseException {

        JSONParser json = new JSONParser();
        FileReader reader = new FileReader(filePath);
        JSONObject requestParams = (JSONObject) json.parse(reader);

        //API preparing request
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.headers("Content-Type", "application/json");
        httpRequest.body(requestParams.toString());
        Response response = httpRequest.post(url);

        createReport(response.getBody().asString(),response.statusCode(), requestParams.toString(),RestAssured.baseURI+url);

        return response;
    }

    public Response getOperation(String url) throws IOException, ParseException {

        //API preparing request
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(url);

        createReport(response.getBody().asString(),response.statusCode(), "",RestAssured.baseURI+url);
        return response;
    }

    public Response PutOperation(String filePath, String url) throws IOException, ParseException {

        JSONParser json = new JSONParser();
        FileReader reader = new FileReader(filePath);
        JSONObject requestParams = (JSONObject) json.parse(reader);

        //API preparing request
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.headers("Content-Type", "application/json");
        httpRequest.body(requestParams.toString());
        Response response = httpRequest.put(url);

        createReport(response.getBody().asString(),response.statusCode(), requestParams.toString(),RestAssured.baseURI+url);

        return response;
    }

    public void createReport(String bodyResponse, int statusCode, String request, String url){
        Reporter.log("Response from API: " + bodyResponse);
        Reporter.log("Response status code: " + statusCode);
        Reporter.log("Request sent:" + request);
        Reporter.log("Url path:"+url);
    }

}
