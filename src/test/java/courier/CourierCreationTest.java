package courier;

import config.Config;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class CourierCreationTest {


    @Test
    public void createRandomCourier() {
        boolean isOr = given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(Courier.getRandomCourier())
                .when()
                .post(Config.COURIER_ENDPOINT)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }





}
