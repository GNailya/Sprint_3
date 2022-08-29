import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class CourierLoginTest  {
    Courier courier = Courier.getRandom();
    CourierCredentials creds = CourierCredentials.from(courier);
    private CourierClient courierClient;
    private int courierId;


    @Before
    public void setUp() {
        courier = Courier.getRandom();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {

       courierClient.delete(courierId);
   }

    @Test
    @DisplayName("Успешная авторизация")
    public void loginCheck(){
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        int statusCode = login.extract().statusCode();
        courierId = login.extract().path("id");

        assertThat (statusCode, equalTo(200));
        assertThat(courierId, notNullValue());

    }

    @Test
    @DisplayName("Авторизация без логина")
    public void courierWithoutLogin() {
        String nullLogin = "";
        ValidatableResponse login = courierClient.login(new CourierCredentials(nullLogin, courier.password));

        int statusCode = login.extract().statusCode();
        String message = login.extract().path("message");

        assertThat (statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void courierWithoutPassword() {
        String nullPassword = "";
        ValidatableResponse login = courierClient.login(new CourierCredentials(courier.login , nullPassword));

        int statusCode = login.extract().statusCode();
        String message = login.extract().path("message");

        assertThat (statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с некорректным паролем")
    public void courierNotCorrectPassword() {
        String noPassword = "nopassword";
        ValidatableResponse login= courierClient.login(new CourierCredentials(courier.login, noPassword));

        int statusCode = login.extract().statusCode();
        String message = login.extract().path("message");

        assertThat (statusCode, equalTo(404));
        assertThat(message, equalTo("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("Авторизация с некорректным логином")
    public void courierNotCorrectLogin() {
        String noLogin = "noLogin";
        ValidatableResponse login = courierClient.login(new CourierCredentials(noLogin, courier.password));

        int statusCode = login.extract().statusCode();
        String message = login.extract().path("message");

        assertThat (statusCode, equalTo(404));
        assertThat(message, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка при отсутствии поля с паролем")
    public void courierLoginNotField() {
        ValidatableResponse login  = new CourierClient().login(CourierCredentials.getCourierNotfield(courier));

        int statusCode = login.extract().statusCode();
        assertThat(statusCode, equalTo(400));

        String message = login.extract().path("message");
        assertThat(message, equalTo("Недостаточно данных для входа"));

        //Баг: приходит 504 ошибка
    }

}

