import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginTest extends TestBase {
    private CourierModel courier;

    @Before
    public void setUp() {
        initialiseValidCourier();
        createValidCourier();
    }

    @Test
    @DisplayName("Login valid courier")
    @Description("Send valid request with valid courier")
    public void loginValidCourier() {
        Response loginResponse = CourierApi.logInCourier(courier);

        assertThat("Курьер не вошёл в систему!", loginResponse.statusCode(), is(SC_OK));
        assertThat("В ответе нет id курьера!", loginResponse.path("id"), notNullValue());

        courier.setId(loginResponse.then().extract().path("id").toString());
    }

    //Тут баг с бесконечным ожиданием сервера при недостатке вводных данных для логина!!!
    @Test
    @DisplayName("Login invalid courier without password")
    @Description("Send invalid request with courier without password")
    public void loginCourierWithoutPassword() {
        courier.setId(CourierApi.logInCourier(courier).then().extract().path("id").toString());
        courier.setPassword(null);

        Response loginResponse = CourierApi.logInCourier(courier);

        assertThat("Для входа без пароля ожидается код 400!", loginResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Структура ответа неверна!", loginResponse.path("message"), is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login invalid courier without login")
    @Description("Send invalid request with courier without login")
    public void loginCourierWithoutLogin() {
        courier.setId(CourierApi.logInCourier(courier).then().extract().path("id").toString());
        courier.setLogin(null);

        Response loginResponse = CourierApi.logInCourier(courier);

        assertThat("Для входа без логина ожидается код 400!", loginResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Структура ответа неверна!", loginResponse.path("message"), is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with wrong password")
    @Description("Send invalid request with wrong password for courier")
    public void loginCourierWithWrongPassword() {
        courier.setId(CourierApi.logInCourier(courier).then().extract().path("id").toString());
        courier.setPassword("blabla");

        Response loginResponse = CourierApi.logInCourier(courier);

        assertThat("Для входа с неверным паролем ожидается код 404!", loginResponse.statusCode(), is(SC_NOT_FOUND));
        assertThat("Структура ответа неверна!", loginResponse.path("message"), is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with wrong login")
    @Description("Send an invalid request with wrong login for courier")
    public void loginCourierWithWrongLogin() {
        courier.setId(CourierApi.logInCourier(courier).then().extract().path("id").toString());
        courier.setLogin("blabla");

        Response loginResponse = CourierApi.logInCourier(courier);

        assertThat("Для входа с неверным логином ожидается код 404!", loginResponse.statusCode(), is(SC_NOT_FOUND));
        assertThat("Структура ответа неверна!", loginResponse.path("message"), is("Учетная запись не найдена"));
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

    @Step("Create valid courier")
    public void createValidCourier() {
        Response createResponse = CourierApi.createCourier(courier);

        assertThat("Курьер не был создан!", createResponse.statusCode(), is(SC_CREATED));
    }

    @Step("Delete tested courier")
    public void deleteCourier() {
        Response deleteResponse = CourierApi.deleteCourier(courier.getId());

        assertThat("Курьер не был удалён!", deleteResponse.statusCode(), is(SC_OK));
    }
}
