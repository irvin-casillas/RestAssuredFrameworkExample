package com.unosquare;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FirstAPITest {

    ApiCore apiCore;

    @Ignore
    public void f() {
        RestAssured.baseURI = "https://reqres.in/api/";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/users/2");

        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        Assert.assertEquals(statusCode, 200);
        Reporter.log("Success 200 validation");

        response.then().body("data.first_name", equalTo("Janet"));
        response.then().body("data.last_name", equalTo("Weaver"));
        Reporter.log(response.body().asString());
    }


    @Ignore
    public void f_Gherkin() {

        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON)
                .assertThat().body("data.'first_name'", equalTo("Janet"));

        Reporter.log("Success 200 validation");
    }



    @Test
    public void getListUsers() throws IOException,ParseException{
        Response test = apiCore.getOperation("/users?page=2");
        System.out.println(test.getBody().asString());
    }

    @Test
    public void getUser() throws IOException,ParseException{
        Response test = apiCore.getOperation("/users/2");
        System.out.println(test.getBody().asString());
    }

    @Test
    public void getListColors() throws IOException,ParseException{
        Response test = apiCore.getOperation("/unknown");
        System.out.println(test.getBody().asString());
    }

    @Test
    public void getColor() throws IOException,ParseException{
        Response test = apiCore.getOperation("/unknown/2");
        System.out.println(test.getBody().asString());
    }

    @Test
    public void PostLogin() throws IOException, ParseException {
        Response test = apiCore.PostOperation(".\\Json\\Login.json","/login");
        Assert.assertEquals(test.statusCode(),200);
        System.out.println(test.getBody().asString());
    }

    @Test
    public void PostUser() throws IOException, ParseException {
        Response test = apiCore.PostOperation(".\\Json\\User.json","/users");
        Assert.assertEquals(test.statusCode(),201);
        System.out.println(test.getBody().asString());
    }

    @Test
    public void PostRegister() throws IOException, ParseException {
        Response test = apiCore.PostOperation(".\\Json\\Register.json","/register");
        Assert.assertEquals(test.statusCode(),200);
        System.out.println(test.getBody().asString());
    }

    @Test
    public void PostRegisterMissingPassword() throws IOException, ParseException {
        Response test = apiCore.PostOperation(".\\Json\\Register_MissingPassword.json","/register");
        JsonPath jsonPathEvaluator = test.jsonPath();
        Assert.assertEquals(test.statusCode(),400);
        Assert.assertEquals(jsonPathEvaluator.get("error"),"Missing password");
        System.out.println(jsonPathEvaluator.getString("error"));
    }

    @Test
    public void updateUser() throws IOException,ParseException{
        Response test = apiCore.PutOperation(".\\Json\\User.json","/users/2");
        Assert.assertEquals(test.statusCode(),200);
        System.out.println(test.getBody().asString());
    }

    @BeforeMethod
    public void beforeMethod() {
        apiCore = new ApiCore();

    }
}
