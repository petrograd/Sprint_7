package client;

import client.BaseClient;
import config.Config;
import courier.Courier;
import courier.CourierCredentials;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends BaseClient {
    @Step("Запрос на создание курьера")
    public ValidatableResponse create(Courier courier) {
        return getSpec()
               .body(courier)
               .when()
               .post(Config.COURIER_ENDPOINT)
               .then().log().all();
    }
    @Step("Запрос на авторизацию курьера")
    public ValidatableResponse login(CourierCredentials creds) {
        return getSpec()
               .body(creds)
               .when()
               .post(Config.LOGIN)
               .then().log().all();
    }

    @Step("Запрос на удаление курьера")
    public ValidatableResponse delete(int courierId) {
       return getSpec()
               .pathParam("courierId", courierId)
               .when()
               .delete(Config.COURIER_ID)
               .then().log().all();

    }
}
