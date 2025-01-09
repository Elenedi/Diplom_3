package org.example.endpoints;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.endpoints.UserHttp;
import org.example.references.Constants;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import org.example.objects.User;
import org.example.objects.AcceptUser;

public class UserOperators extends UserHttp {
    @Step("Создание тестового пользователя")
    public void createUser(String name, String email, String password) {
        Response response = given(this.baseRequest("application/json"))
                .body(new User(email, password, name))
                .when()
                .post(Constants.API_REGISTER_USER);

        response.getStatusCode();
    }

    @Step("Удаление тестового пользователя")
    public void deleteUser(String email, String password) {
        Response response = loginUser(email, password);

        if(response.getStatusCode() != SC_OK) return;

        String token = response.body().as(AcceptUser.class).getAccessToken().split(" ")[1];
        deleteUser(token);
    }
}
