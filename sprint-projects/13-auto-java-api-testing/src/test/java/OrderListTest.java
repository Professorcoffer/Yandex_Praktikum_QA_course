import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderListTest extends TestBase {
    @Test
    @DisplayName("Get list of orders")
    @Description("Send valid request for list of orders without additional parameters")
    public void getListOfOrders() {
        Response listResponse = OrderApi.getListOfOrders();

        assertThat("Список заказов не был получен!", listResponse.statusCode(), is(SC_OK));
        assertThat("В ответе нет списка заказов!", listResponse.body().path("orders[0]"), notNullValue());
        assertThat("В ответе нет указания страниц!", listResponse.body().path("pageInfo.page"), notNullValue());
        assertThat("В ответе нет списка доступных станций!", listResponse.body().path("availableStations[0]"), notNullValue());
    }
}
