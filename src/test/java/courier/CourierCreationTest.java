package courier;

import client.CourierClient;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
@DisplayName("Тест: Создание курьера")
public class CourierCreationTest {

    Courier courier;
    Courier existingCourier; // для теста по удалению уже существующего клиента
    CourierClient courierClient;
    private int courierId = 0;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }
    @After
    public void teardown() {
        if (courierId != 0)
            courierClient.delete(courierId);

    }

    @Test
    @DisplayName("Можно зарегистрировать курьера (все обязательные поля заполнены)")
    @Description("Ожидаемый код ответа: 201")
    public void createCourierTest() {
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .extract().path("id");

        if (courierId != 0) {
            existingCourier = courier;
        }
        assertTrue(isOk);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Ожидаемый код ответа: 409")
    public void createTwoExistingCouriers() {
        if (existingCourier == null)
            createCourierTest();

        assertNotNull(existingCourier);

        courierClient.create(existingCourier)
                .assertThat()
                .statusCode(409);
    }

    @Test
    @DisplayName("Нельзя зарегистрировать курьера с повторяющимся логином")
    @Description("Ожидаемый код ответа: 409")
    public void createClientWithSameLogin() {
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .extract().path("id");

        String loginExistingCourier = creds.getLogin();

        Courier newCourier = Courier.getRandomCourier();
        newCourier.setLogin(loginExistingCourier);

        courierClient.create(newCourier)
                .assertThat()
                .statusCode(409);

    }


    @Test
    @DisplayName("Нельзя зарегистрировать курьера без обязательного поля ПАРОЛЬ")
    @Description("Ожидаемый код ответа: 400")
    public void createWithoutPasswordOnlyRequest() {
        courier = Courier.getWithoutPassword();

        courierClient.create(courier)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя зарегистрировать курьера без обязательного поля ЛОГИН")
    @Description("Ожидаемый код ответа: 400")
    public void createWithoutLoginOnlyRequest() {
        courier = Courier.getWithoutLogin();

        courierClient.create(courier)
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

}
