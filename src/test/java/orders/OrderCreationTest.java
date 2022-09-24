package orders;

import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Создание заказа")
public class OrderCreationTest {
    private final List<String> colors;
    private OrderClient orderClient;
    private int track;

    public OrderCreationTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Color: {0}")
    public static Object[] dataForTest() {
        return new Object[]{
                asList(OrderClient.GREY),
                asList(OrderClient.BLACK),
                asList(OrderClient.BLACK, OrderClient.GREY),
                asList(OrderClient.YELLOW),
                asList(""),
                null,
        };
    }

    @Test
    @DisplayName("Можно создать заказ с любым набором цветов")
    @Description("Ожидаемый код ответа: 201")
    public void shouldPlaceOrderWithAnyColors() {

        int expectedStatusCode = 201;

        Order order = Order.getDefault();
        order.setColor(colors);

        orderClient = new OrderClient();
        ValidatableResponse response = orderClient.create(order);
        response
                .assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("track", is(notNullValue()));

        track = orderClient.getTrack(response);
    }

    @After
    @DisplayName("Удаление тестового заказа")
    public void teardown() {
        orderClient.cancelOrder(track);
    }
}




