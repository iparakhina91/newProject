import io.qameta.allure.Owner;
import models.UserBody;
import models.UserResponse;
import models.UserResponseClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.Spec.*;

public class ApiTest extends TestBase {

    private static final int USER = 6;

    @Test
    void createUser() {

        UserBody data = new UserBody();
        data.setName("irina");
        data.setJob("qa");

        UserResponse response = step("Create user", () ->
                given(requestSpec)
                        .body(data)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponse)
                        .extract().as(UserResponse.class));

        step("Проверка name созданного пользователя", () ->
                    assertThat(response.getName()).isEqualTo("irina"));
        step("Проверка job созданного пользователя", () ->
                    assertThat(response.getJob()).isEqualTo("qa"));
    }

    @Test
    @Tag("api")
    @Owner("korovinaiyu")
    @DisplayName("Изменение имени пользователя PUT api/users/:id с существующим id")
    void updateUserTestWithLombok() {
        UserBody updateData = new UserBody();
        updateData.setName("ira");
        updateData.setJob("qa");

        UserResponse response = step("Изменение имени пользователя c id " + USER, () ->
                given(requestSpec)
                        .body(updateData)
                        .when()
                        .put("/users/" + USER)
                        .then()
                        .spec(responseSuccess)
                        .extract().as(UserResponse.class));

        step("Проверка, что изменилось имя пользователя с id " + USER, () ->
                assertThat(response.getName()).isEqualTo("ira"));
    }

    @Test
    @Tag("api")
    @Owner("korovinaiyu")
    @DisplayName("Изменение имени пользователя PUT api/users/:id с существующим id")
    void updateUserTest() {
        UserBody updateData = new UserBody();
        updateData.setName("ira");
        updateData.setJob("qa");

        UserResponse response = step("Изменение имени пользователя c id " + USER, () ->
                given(requestSpec)
                        .body(updateData)
                        .when()
                        .patch("/users/" + USER)
                        .then()
                        .spec(responseSuccess)
                        .extract().as(UserResponse.class));

        step("Проверка, что изменилось имя пользователя с id " + USER, () ->
                assertThat(response.getName()).isEqualTo("ira"));
    }

    @Test
    @Tag("api")
    @Owner("korovinaiyu")
    @DisplayName("Удаление пользователя DELETE api/users/:id с существующим id")
    void deleteUserTestWithLombok() {
        step("Удаление пользователя c id " + USER, () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .delete("/users/"+ USER)
                        .then()
                        .statusCode(204));
    }

    @Test
    @DisplayName("Проверка структуры ответа на запрос GET api/users/:id с id существующего пользователя")
    void successfulCheckSingleUserTest() {
        given(requestSpec)
                .when()
                .get("/users/10")
                .then()
                .spec(responseSuccess)
                .body(matchesJsonSchemaInClasspath("schemes/status-scheme-responce.json"));
    }

    @Test
    @DisplayName("Проверка структуры ответа на запрос GET api/users/:id с id существующего пользователя")
    void getInfoAboutSingleUser() {

        UserResponseClass responseData = given()
                        .spec(requestSpec)
                        .when()
                        .get("/users/10")
                        .then()
                        .spec(responseSuccess)
                        .extract().as(UserResponseClass.class);

                assertThat(responseData.getData().getId()).isEqualTo(10);
                assertThat(responseData.getData().getEmail()).isEqualTo("byron.fields@reqres.in");
                assertThat(responseData.getData().getFirstName()).isEqualTo("Byron");
                assertThat(responseData.getData().getLastName()).isEqualTo("Fields");
                assertThat(responseData.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/10-image.jpg");
    }


}
