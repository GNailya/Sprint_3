import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setup() {

        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Создание курьера")
    public void courierCreate() {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        boolean created = response.extract().path("ok");

        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        courierId = login.extract().path("id");

        assertThat(statusCode, equalTo(201));
        assertTrue(created);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Проверка при дубле логина")
    public void courierLoginDouble() {

        courierClient.create(courier).assertThat().statusCode(201);
        ValidatableResponse created = courierClient.create(courier).assertThat().statusCode(409).body("message", equalTo("Этот логин уже используется."));

        //Баг в message:
        //ОР: "Этот логин уже используется"
        //ФР: "Этот логин уже используется. Попробуйте другой."

    }
}

