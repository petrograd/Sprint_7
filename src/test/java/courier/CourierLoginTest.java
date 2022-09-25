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

@DisplayName("Тест: Логин курьера")
public class CourierLoginTest {

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
    @DisplayName("Курьер может авторизоваться  (обязательные поля Логин и Пароль заполнены)")
    @Description("Ожидаемый код ответа: 200. Запрос возвращает id")
    public void shouldLoginWithValidCredentials() {

        int expectedStatusCode = 200;

        CourierCredentials creds = CourierCredentials.from(courier);
        courierClient.login(creds)
               .assertThat()
               .statusCode(expectedStatusCode)
               .and()
               .body("id", is(notNullValue()));
    }
    @Test
    @DisplayName("Ошибка, если неправильно указать логин")
    @Description("Ожидаемый код ответа: 404")
    public void shouldNotLoginWithWrongLogin() {

        int expectedStatusCode = 404;

        courier.setRandomLogin();
        CourierCredentials creds = CourierCredentials.from(courier);

        courierClient.login(creds)
                .assertThat()
                .statusCode(expectedStatusCode);
    }
    @Test
    @DisplayName("Ошибка, если неправильно указать пароль")
    @Description("Ожидаемый код ответа: 404")
    public void shouldNotLoginWithWrongPassword() {

        int expectedStatusCode = 404;

        courier.setRandomPassword();
        CourierCredentials creds = CourierCredentials.from(courier);

        courierClient.login(creds)
                .assertThat()
                .statusCode(expectedStatusCode);
    }
    @Test
    @DisplayName("Ошибка, Запрос без логина")
    @Description("Ожидаемый код ответа: 400")
    public void shouldNotLoginWithoutLogin() {

        int expectedStatusCode = 400;

        courier.setLogin("");
        CourierCredentials creds = CourierCredentials.from(courier);

        courierClient.login(creds)
                .assertThat()
                .statusCode(expectedStatusCode);
    }
    @Test
    @DisplayName("Ошибка, Запрос без пароля")
    @Description("Ожидаемый код ответа: 400")
    public void shouldNotLoginWithoutPassword() {

        int expectedStatusCode = 400;

        courier.setPassword("");
        CourierCredentials creds = CourierCredentials.from(courier);

        courierClient.login(creds)
                .assertThat()
                .statusCode(expectedStatusCode);
    }

}
