package org.example.endpoints;

import io.qameta.allure.restassured.AllureRestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.example.objects.User;
import org.example.references.Constants;

public class UserHttp {
    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
    protected RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
    protected Response loginUser(String email, String password) {
        return given(this.baseRequest("application/json"))
                .body(new User(email, password))
                .when()
                .post(Constants.API_LOGIN);
    }
    protected Response deleteUser(String token) {
        return given(this.baseRequest())
                .auth().oauth2(token)
                .delete(Constants.API_DELETE_USER);
    }
}

