import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class CourierErrorCreatedTest {
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void createWithoutLogin() {
        String password = RandomStringUtils.randomNumeric(5);
        String login = "";

        Courier courier = new Courier(login, password, "Anna");
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    public void createWithoutPassword(){
        String login = RandomStringUtils.randomNumeric(5);
        String password = "";

        Courier courier = new Courier(login, password, "Lena");
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    public void createWithoutFirstName(){
        String login = RandomStringUtils.randomNumeric(6);
        String firstName = "" ;

        Courier courier = new Courier(login, "qwerty", firstName);
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");

        assertThat(statusCode, equalTo(400));// баг: создается пользователь без имени
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }
}

