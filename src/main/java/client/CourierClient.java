package client;

import client.BaseClient;
import config.Config;
import courier.Courier;
import courier.CourierCredentials;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends BaseClient {

    public ValidatableResponse create(Courier courier) {
        return getSpec()
               .body(courier)
               .when()
               .post(Config.COURIER_ENDPOINT)
               .then().log().all();
    }

    public ValidatableResponse login(CourierCredentials creds) {
        return getSpec()
               .body(creds)
               .when()
               .post(Config.LOGIN)
               .then().log().all();
    }

    public ValidatableResponse delete(int courierId) {
       return getSpec()
               .pathParam("courierId", courierId)
               .when()
               .delete(Config.COURIER_ID)
               .then().log().all();

    }
}
