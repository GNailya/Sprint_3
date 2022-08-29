import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получаем список заказов")
    public void getOrderListTest() {
        ValidatableResponse response = orderClient.getOrderList();
        List<Object> orders = response.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());
    }
}
