

package courier;

import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

@DisplayName("Тест: Удаление курьера")
public class CourierDeleteTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId = 0;


    @Before
    //create courier
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");

        assertTrue(isOk);

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .extract().path("id");
    }

    @After
    public void teardown() {
        if (courierId != 0)
            courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Существующего курьера удалить можно")
    @Description("Ожидаемый код ответа: 200.")
    public void shouldDeleteValidCourier() {

       int expectedStatusCode = 200;

       courierClient.delete(courierId)
               .assertThat()
               .statusCode(expectedStatusCode)
               .and()
               .body("ok", is(true));
        courierId = 0;
    }

    @Test
    @DisplayName("Ошибка при попытке удалить курьера с несуществующим id")
    @Description("Ожидаемый код ответа: 404")
    public void shouldNotDeleteWithWrongId() {

        int expectedStatusCode = 404;

        courierClient.delete(courierId)
                        .statusCode(200);

        courierClient.delete(courierId)
                .assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("message", is("Курьера с таким id нет."));
    }

//    @Test
//    @DisplayName("Удаление курьера. Ошибка в id запроса")
//    @Description("Ожидаемый код ответа: 500")
//    public void shouldNotDeleteWithoutId() {
//
//        int expectedStatusCode = 500;
//        courierClient.deleteWithoutId()
//                .assertThat()
//                .statusCode(expectedStatusCode);
//    }



}

