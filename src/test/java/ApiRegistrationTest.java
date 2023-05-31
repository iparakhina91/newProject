import io.qameta.allure.Owner;
import models.RegisterUserBody;
import models.RegisterUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Spec.*;

public class ApiRegistrationTest {

    @Test
    @Tag("api")
    @Owner("korovinaiyu")
    @DisplayName("Регистрация пользователя POST api/register без password")
    void registerMissingPasswordTestWithLombok() {
        RegisterUserBody registerData = new RegisterUserBody();
        registerData.setEmail("irina@mail.ru");

        RegisterUserResponse response = step("Регистрация пользователя без password", () ->
                given(requestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseFail)
                        .extract().as(RegisterUserResponse.class));

        step("Проверка, что отображается ошибка с корректным текстом", () ->
                assertThat(response.getError().equals("Missing password")));
    }

    @Test
    @Tag("api")
    @Owner("korovinaiyu")
    @DisplayName("Регистрация пользователя POST api/register без password")
    void registerUserTest() {
        RegisterUserBody registerData = new RegisterUserBody();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterUserResponse response = step("Регистрация пользователя без password", () ->
                given(requestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSuccess)
                        .extract().as(RegisterUserResponse.class));

        assertEquals("4", response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }
}
