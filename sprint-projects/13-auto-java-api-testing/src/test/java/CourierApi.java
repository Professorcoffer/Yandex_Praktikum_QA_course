import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {
    public static Response createCourier(CourierModel courier) {
        return given().header("Content-Type", "application/json").body(courier).when().post("/api/v1/courier");
    }

    public static Response logInCourier(CourierModel courier) {
        return given().header("Content-Type", "application/json").body(courier).when().post("/api/v1/courier/login");
    }

    public static Response deleteCourier(String id) {
        return given().delete("/api/v1/courier/" + id);
    }
}
