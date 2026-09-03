import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    public static Response createOrder(OrderModel order, String accessToken) {
        return given()
                .header("Authorization", accessToken).contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/orders");
    }
}
