import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderAcceptanceTest extends TestBase{
    private OrderModel order;
    private CourierModel courier;

    @Test
    @DisplayName("Accept valid order")
    @Description("Send valid request with valid order and valid courier")
    public void acceptValidOrder() {
        initialiseValidCourier();
        createValidCourier();
        courier.setId(getCourierID());

        initialiseOrderWithoutColors();
        createOrderWithoutColors();

        Response acceptResponse = OrderApi.acceptOrder(courier.getId(), order.getId());

        assertThat("Заказ не был принят!", acceptResponse.statusCode(), is(SC_OK));
        assertThat("Неверная структура ответа!", acceptResponse.path("ok"), is(true));
    }

    @Test
    @DisplayName("Accept order with wrong courier ID")
    @Description("Send invalid request with valid order and wrong courier ID")
    public void acceptOrderWithWrongCourierID() {
        initialiseOrderWithoutColors();
        createOrderWithoutColors();

        Response acceptResponse = OrderApi.acceptOrder("69", order.getId());

        assertThat("Для неверного ID курьера ожидается код 404!", acceptResponse.statusCode(), is(SC_NOT_FOUND));
        assertThat("Неверная структура ответа!", acceptResponse.path("message"), is("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Accept order without courier ID")
    @Description("Send invalid request without courier ID")
    public void acceptOrderWithoutCourierID() {
        initialiseOrderWithoutColors();
        createOrderWithoutColors();

        Response acceptResponse = OrderApi.acceptOrder("", order.getId());

        assertThat("Для отсутствия ID курьера ожидается код 400!", acceptResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Неверная структура ответа!", acceptResponse.path("message"), is("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Accept order with wrong order ID")
    @Description("Send an invalid request with wrong order ID and valid courier")
    public void acceptOrderWithWrongOrderID() {
        initialiseValidCourier();
        createValidCourier();
        courier.setId(getCourierID());

        Response acceptResponse = OrderApi.acceptOrder(courier.getId(), "69");

        assertThat("Для неверного ID заказа ожидается код 404!", acceptResponse.statusCode(), is(SC_NOT_FOUND));
        assertThat("Неверная структура ответа!", acceptResponse.path("message"), is("Заказа с таким id не существует"));
    }

    @Test
    @DisplayName("Accept order without order ID")
    @Description("Send invalid request without order ID and valid courier")
    public void acceptOrderWithoutOrderID() {
        initialiseValidCourier();
        createValidCourier();
        courier.setId(getCourierID());

        Response acceptResponse = OrderApi.acceptOrder(courier.getId(), "");

        assertThat("Неверный код ответа при принятии заказа без номера заказа!", acceptResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Неверная структура ответа!", acceptResponse.path("message"), is("Недостаточно данных для поиска"));
    }

    @After
    public void cleanUp() {
        if (courier != null && courier.getId() != null) {
            deleteCourier();
        }
        if (order != null && order.getTrack() != null) {
            cancelOrder(); //Закоментировать чтобы избежать ошибки по отмене заказа
        }
        courier = null;
        order = null;
    }

    @Step("Initialise valid courier")
    public void initialiseValidCourier() {
        courier = new CourierModel("Sharingan" + new Random().nextInt(1000), "1234", "Saske", null);
    }

    @Step("Create valid courier")
    public void createValidCourier() {
        Response createResponse = CourierApi.createCourier(courier);

        assertThat("Курьер не был создан!", createResponse.statusCode(), is(SC_CREATED));
    }

    @Step("Get courier ID for deletion")
    public String getCourierID() {
        Response loginResponse = CourierApi.logInCourier(courier);

        assertThat("Курьер не вошёл в систему!", loginResponse.statusCode(), is(SC_OK));

        return loginResponse.then().extract().path("id").toString();
    }

    @Step("Delete tested courier")
    public void deleteCourier() {
        Response deleteResponse = CourierApi.deleteCourier(courier.getId());

        assertThat("Курьер не был удалён!", deleteResponse.statusCode(), is(SC_OK));
    }

    @Step("Initialise valid order without colors")
    public void initialiseOrderWithoutColors() {
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
    public void createOrderWithoutColors() {
        Response createResponse = OrderApi.createOrder(order);

        assertThat("Заказ не был создан!", createResponse.statusCode(), is(SC_CREATED));

        order.setTrack(createResponse.then().extract().path("track").toString());
        order.setId(getOrderID());
    }

    //Баг с неотменяющимися заказами!
    @Step("Cancel tested order")
    public void cancelOrder() {
        Map<String, String> trackMap = new HashMap<>();
        trackMap.put("track", order.getTrack());

        Response cancelResponse = OrderApi.cancelOrder(trackMap);

        assertThat("Заказ не был отменён!", cancelResponse.statusCode(), is(SC_OK));
    }

    @Step("Get order ID")
    public String getOrderID() {
        Response orderResponse = OrderApi.getOrderID(order);

        assertThat("Заказ не был получен!", orderResponse.statusCode(), is(SC_OK));

        return orderResponse.path("order.id").toString();
    }
}
