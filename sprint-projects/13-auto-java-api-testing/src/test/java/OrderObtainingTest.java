import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderObtainingTest extends TestBase {
    private OrderModel order;

    @Test
    @DisplayName("Get order")
    @Description("Send valid request to get order info")
    public void getOrder() {
        initialiseOrderWithoutColor();
        createOrderWithoutColor();

        Response orderResponse = OrderApi.obtainResponse(order.getTrack());

        assertThat("Заказ не был получен!", orderResponse.statusCode(), is(SC_OK));
        assertThat("Неверная структура ответа!", orderResponse.path("order.id"), notNullValue());
    }

    @Test
    @DisplayName("Get order with wrong ID")
    @Description("Send invalid request to get order info with wrong order ID")
    public void getOrderWithWrongID() {
        Response orderResponse = OrderApi.obtainResponse("69");

        assertThat("Для неверного ID заказа ожидается код 404!", orderResponse.statusCode(), is(SC_NOT_FOUND));
        assertThat("Неверная структура ответа!", orderResponse.path("message"), is("Заказ не найден"));
    }

    @Test
    @DisplayName("Get order without ID")
    @Description("Send invalid request to get order info without order ID")
    public void getOrderWithoutID() {
        Response orderResponse = OrderApi.obtainResponse("");

        assertThat("Для отсутствующего ID заказа ожидается код 400!", orderResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Неверная структура ответа!", orderResponse.path("message"), is("Недостаточно данных для поиска"));
    }

    @After
    public void cleanUp() {
        if (order != null && order.getTrack() != null) {
            cancelOrder(); //Закоментировать чтобы избежать ошибки по отмене заказа
        }
        order = null;
    }

    //Баг с неотменяющимися заказами!
    @Step("Cancel tested order")
    public void cancelOrder() {
        Map<String, String> trackMap = new HashMap<>();
        trackMap.put("track", order.getTrack());

        Response cancelResponse = OrderApi.cancelOrder(trackMap);

        assertThat("Заказ не был отменён!", cancelResponse.statusCode(), is(SC_OK));
    }

    @Step("Initialise valid order without colors")
    public void initialiseOrderWithoutColor() {
        order = new OrderModel("Naruto",
                "Uzumaki",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                "5",
                "2020-06-06",
                "Saske, come back to Konoha");
    }

    @Step("Create valid order without colors")
    public void createOrderWithoutColor() {
        Response createResponse = OrderApi.createOrder(order);

        assertThat("Заказ не был создан!", createResponse.statusCode(), is(SC_CREATED));

        order.setTrack(createResponse.then().extract().path("track").toString());
    }
}
