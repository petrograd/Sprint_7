package client;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import order.Order;

public class OrderClient extends BaseClient {

    public static final String BLACK = "BLACK";
    public static final String GREY = "GREY";
    public static final String YELLOW = "YELLOW";

    @Step("Запрос на создание заказа")
    public ValidatableResponse create(Order order) {
        Response response = getSpec()
                .and()
                .body(order)
                .when()
                .post(Config.ORDER_ENDPOINT);
        return response
                .then()
                .log().all();
    }

    @Step("Получение номера трека заказа")
    public int getTrack(ValidatableResponse response) {
        return response
                .extract()
                .path("track");
    }

    @Step("Запрос на удаление (отмену) заказа")
    public ValidatableResponse cancelOrder(int track) {
        Response response = getSpec()
                .queryParam("track", track)
                .when()
                .put(Config.ORDER_CANCEL);
        return response
                .then()
                .log().all();
    }

}
