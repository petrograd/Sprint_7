package orders;

import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тест: Получение списка заказов")
public class OrderListTest {
    private OrderClient orderClient;

    @Test
    @DisplayName("Можно Получить список заказов")
    @Description("Список не пустой и код ответа: 200")
    public void shouldGetOrderListTest() {
        int expectedStatusCode = 200;
        orderClient = new OrderClient();
        ValidatableResponse response = orderClient.getListOfOrders();
        response
                .assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("orders", notNullValue());
    }
}
