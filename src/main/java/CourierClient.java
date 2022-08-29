import io.qameta.allure.Step;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private final String ROOT = "api/v1/courier/";


    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(ROOT)
                .then();

    }

    @Step("Логин курьера")
    public ValidatableResponse login(CourierCredentials creds) {
        return given()
                .spec(getSpec())
                .body(creds)
                .when()
                .post(ROOT + "login/")
                .then();

    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(ROOT + courierId)
                .then();


    }


}

