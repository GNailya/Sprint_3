import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private Order order;
    private OrderClient orderClient;
    private final List<String> color;


    @Before
    public void setUp() {

        order = Order.getDefault(color);
        orderClient = new OrderClient();
    }

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
            {List.of("BLACK")},
            {List.of("GREY")},
            {List.of("BLACK", "GREY")},
            {List.of()}
        };
    }

    @Test
    @DisplayName("Оформление заказа с разными цветами")
    public void orderCreateTest() {

        ValidatableResponse response;
        response = orderClient.orderCreate(order);
        int statusCode = response.extract().statusCode();
        int trackID = response.extract().path("track");

        ValidatableResponse responseId = orderClient.getOrderInfo(trackID);
        List<String> actualColor = responseId.extract().jsonPath().getList("order.color");

        assertEquals(201, statusCode);
        assertNotEquals(0, trackID);
        assertEquals("Цвет не соответствует", this.color, actualColor);

    }


}
