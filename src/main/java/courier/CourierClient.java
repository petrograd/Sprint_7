package courier;

import config.Config;
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
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(Config.COURIER_ID)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
