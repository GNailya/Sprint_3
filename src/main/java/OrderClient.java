import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private final String ORDER = "/api/v1/orders";
    private final String NUMBER_ORDER = "/api/v1/orders/track?t=";

    @Step("Создание заказа")
    public ValidatableResponse orderCreate(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER)
                .then();//.extract().body().path("track");
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Получение информации заказа по его номеру")
    public ValidatableResponse getOrderInfo(int trackID) {
        return given()
                .spec(getSpec())
                .when()
                .get(NUMBER_ORDER + trackID)
                .then();
    }

}


