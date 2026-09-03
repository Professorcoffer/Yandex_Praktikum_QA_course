import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierDeletionTest extends TestBase{
    private CourierModel courier;

    @Test
    @DisplayName("Delete courier")
    @Description("Send valid request for courier deletion")
    public void deleteValidCourier() {
        initialiseValidCourier();
        createValidCourier();
        courier.setId(getCourierID());

        Response deleteResponse = CourierApi.deleteCourier(courier.getId());

        assertThat("Курьер не был удалён!", deleteResponse.statusCode(), is(SC_OK));
        assertThat("Ответ имеет неверную структуру!", deleteResponse.path("ok"), is(true));
    }

    @Test
    @DisplayName("Delete courier without ID")
    @Description("Send invalid request for courier deletion without ID")
    public void deleteCourierWithoutID() {
        Response deleteResponse = CourierApi.deleteCourier("");

        assertThat("Неверный код ответа на удаление без ID!", deleteResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Ответ имеет неверную структуру!", deleteResponse.path("message"), is("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Delete courier with invalid ID")
    @Description("Send invalid request for courier deletion with wrong ID")
    public void deleteCourierWithWrongID() {
        Response deleteResponse = CourierApi.deleteCourier("69");

        assertThat("Неверный код ответа на удаление с неверным ID!", deleteResponse.statusCode(), is(SC_NOT_FOUND));
        assertThat("Ответ имеет неверную структуру!", deleteResponse.path("message"), is("Курьера с таким id нет"));
    }

    @After
    public void cleanUp() {
        courier = null;
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
}
