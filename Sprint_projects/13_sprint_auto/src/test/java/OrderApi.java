import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderApi {
    public static Response createOrder(OrderModel order) {
        return given().header("Content-Type", "application/json").body(order).when().post("/api/v1/orders");
    }

    public static Response cancelOrder(Map<String, String> trackMap) {
        return given().header("Content-Type", "application/json").body(trackMap).when().put("/api/v1/orders/cancel");
    }

    public static Response getOrderID(OrderModel order) {
        return given().queryParam("t", order.getTrack()).when().get("/api/v1/orders/track");
    }

    public static Response getListOfOrders() {
        return given().get("/api/v1/orders");
    }

    public static Response obtainResponse(String track) {
        if(track.isEmpty()) {
            return given().get("/api/v1/orders/track");
        } else {
            return given().queryParam("t", track).when().get("/api/v1/orders/track");
        }
    }

    public static Response acceptOrder(String courierID, String orderID) {
        if(courierID.isEmpty()) {
            if(orderID.isEmpty()) {
                return given().put("/api/v1/orders/accept/");
            } else {
                return given().when().put("/api/v1/orders/accept/" + orderID);
            }
        } else {
            if(orderID.isEmpty()) {
                return given().queryParam("courierId", courierID).when().put("/api/v1/orders/accept/");
            } else {
                return given().queryParam("courierId", courierID).when().put("/api/v1/orders/accept/" + orderID);
            }
        }
    }
}
