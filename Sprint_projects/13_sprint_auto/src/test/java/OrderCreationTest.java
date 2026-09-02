import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreationTest extends TestBase {
    private OrderModel order;
    private final String[] colorSet;
    private final int expectedStatus;

    public OrderCreationTest(String[] colorSet, int expectedStatus) {
        this.colorSet = colorSet;
        this.expectedStatus = expectedStatus;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {new String[]{"BLACK", "GREY"}, SC_CREATED},
                {new String[]{"GREY"}, SC_CREATED},
                {new String[]{"BLACK"}, SC_CREATED},
                {null, SC_CREATED}
        };
    }

    @Before
    public void setUp() {
        initialiseOrderWithoutColors();
    }

    @Test
    @DisplayName("Create order with both colors")
    @Description("Send valid request with both colors selected in order")
    public void createOrder() {
        order.setColor(colorSet);

        Response createResponse = OrderApi.createOrder(order);

        assertThat("Заказ не был создан!", createResponse.statusCode(), is(expectedStatus));
        assertThat("Тело ответа не содержит трек номер!", createResponse.path("track"), notNullValue());

        order.setTrack(createResponse.then().extract().path("track").toString());
    }

    @After
    public void cleanUp() {
        if (order != null && order.getTrack() != null) {
            cancelOrder(); //Закоментировать чтобы избежать ошибки по отмене заказа
        }
        order = null;
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

    //Баг с неотменяющимися заказами!
    @Step("Cancel tested order")
    public void cancelOrder() {
        Map<String, String> trackMap = new HashMap<>();
        trackMap.put("track", order.getTrack());

        Response cancelResponse = OrderApi.cancelOrder(trackMap);

        assertThat("Заказ не был отменён!", cancelResponse.statusCode(), is(SC_OK));
    }
}
