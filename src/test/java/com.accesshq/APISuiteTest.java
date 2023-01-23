package com.accesshq;

import org.junit.jupiter.api.*;
import org.hamcrest.Matchers;

import java.util.List;

import static io.restassured.RestAssured.* ;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class APISuiteTest {

    /*
    Good tests are arranged with a pre-condition, event, post-condition
    ---------------------------------------------------------------------
    Given, When, Then
    Arrange, Act, Assert
     */
    @Test
    public void AusPostStatusTest() {
        given().
            header("auth-key", "62349813-01f7-4ab9-95b8-1418eeeb929f").and().
            param("q", "Melbourne").and().
            param("state", "VIC").and().
        when().
            get("https://digitalapi.auspost.com.au/postcode/search.json").
        then().
            assertThat().statusCode(200);
    }

    @Test
    public void AusPostPostCodeTest() {
        int postCodeNumber = 2250;

        given().
            header("auth-key", "62349813-01f7-4ab9-95b8-1418eeeb929f").and().
            param("q", "Tascott").and().
            param("state", "NSW").and().
        when().
            get("https://digitalapi.auspost.com.au/postcode/search.json").
        then().
            assertThat().body("localities.locality.postcode", Matchers.equalTo(postCodeNumber));
    }

    @Test
    public void AusPostManyPostCodeTest() {
        int melbournePostCode = 3001;
        Boolean containsPostCode = false;

        List<Object> postCodes = given().
            header("auth-key", "62349813-01f7-4ab9-95b8-1418eeeb929f").and().
            param("q", "Melbourne").and().
            param("state", "VIC").and().
        when().
            get("https://digitalapi.auspost.com.au/postcode/search.json").
        then().
            extract().body().jsonPath().getList("localities.locality.postcode");

        for(Object postCode: postCodes) {
            if (postCode instanceof Integer) {
                if (postCode.equals(melbournePostCode)) {
                    containsPostCode = true;
                    break;
                }
            }
        }

        assertTrue(containsPostCode);
    }

}