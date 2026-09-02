import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierCreationTest extends TestBase {
    private CourierModel courier;

    @Test
    @DisplayName("Create valid courier")
    @Description("Send valid request with valid courier")
    public void createValidCourier() {
        initialiseValidCourier();

        Response createResponse = CourierApi.createCourier(courier);

        courier.setId(getCourierID());

        assertThat("Курьер не был создан!", createResponse.statusCode(), is(SC_CREATED));
        assertThat("Ответ имеет неверную структуру!", createResponse.path("ok"), is(true));
    }

    @Test
    @DisplayName("Create identical couriers")
    @Description("Send invalid request with two identical couriers")
    public void createIdenticalCouriers() {
        initialiseValidCourier();

        Response createFirstResponse = CourierApi.createCourier(courier);

        assertThat("Первый курьер не был создан!", createFirstResponse.statusCode(), is(SC_CREATED));
        assertThat("Ответ имеет неверную структуру!", createFirstResponse.path("ok"), is(true));

        Response createSecondResponse = CourierApi.createCourier(courier);

        assertThat("Для одинаковых курьеров ожидается код 409!", createSecondResponse.statusCode(), is(SC_CONFLICT));
        assertThat("Ответ имеет неверную структуру!", createSecondResponse.path("message"), is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Send invalid request with courier without password")
    public void createCourierWithoutPassword() {
        initialiseCourierWithoutPassword();

        Response createInvalidResponse = CourierApi.createCourier(courier);

        assertThat("Для курьера без пароля ожидается код 400!", createInvalidResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Ответ имеет неверную структуру!", createInvalidResponse.path("message"), is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Send invalid request with courier without login")
    public void createCourierWithoutLogin() {
        initialiseCourierWithoutLogin();

        Response createInvalidResponse = CourierApi.createCourier(courier);

        assertThat("Для курьера без логина ожидается код 400!", createInvalidResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Ответ имеет неверную структуру!", createInvalidResponse.path("message"), is("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        if (courier != null && courier.getId() != null) {
            deleteCourier();
        }
        courier = null;
    }

    @Step("Initialise valid courier")
    public void initialiseValidCourier() {
        courier = new CourierModel("Sharingan" + new Random().nextInt(1000), "1234", "Saske", null);
    }

    @Step("Initialise invalid courier without password")
    public void initialiseCourierWithoutPassword() {
        courier = new CourierModel("Sharingan" + new Random().nextInt(1000), null, "Saske", null);
    }

    @Step("Initialise invalid courier without login")
    public void initialiseCourierWithoutLogin() {
        courier = new CourierModel(null, "1234", "Saske", null);
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
}
