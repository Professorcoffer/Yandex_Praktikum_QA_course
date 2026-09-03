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

public class OrderCreationTest extends TestBase{
    private OrderModel testOrder;
    private UserModel testUser;

    @Test
    @DisplayName("Create valid order with auth")
    @Description("Send valid order creation request with auth and ingredients")
    public void createOrderWithAuth() {
        initialiseOrderWithIngredients();
        loginValidUser();

        Response createResponse = OrderApi.createOrder(testOrder, testUser.getAccessToken());

        assertThat("Заказ не был создан!", createResponse.statusCode(), is(SC_OK));
        assertThat("В ответе нет номера заказа!", createResponse.path("order.number"), is(notNullValue()));
    }

    @Test
    @DisplayName("Create valid order without auth")
    @Description("Send invalid order creation request without auth and with ingredients")
    public void createOrderWithoutAuth() {
        initialiseOrderWithIngredients();
        registerValidUser();

        Response createResponse = OrderApi.createOrder(testOrder, testUser.getAccessToken());

        assertThat("Заказ не был создан!", createResponse.statusCode(), is(SC_OK));
        assertThat("Неверная структура ответа!", createResponse.path("success"), is(true));
    }

    @Test
    @DisplayName("Create invalid order without ingredients")
    @Description("Send invalid order creation request with auth and without ingredients")
    public void createOrderWithoutIngredients() {
        initialiseOrderWithoutIngredients();
        loginValidUser();

        Response createResponse = OrderApi.createOrder(testOrder, testUser.getAccessToken());

        assertThat("При отсутствии ингредиентов ожидается код 400!", createResponse.statusCode(), is(SC_BAD_REQUEST));
        assertThat("Неверная структура ответа!", createResponse.path("message"), is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create invalid order with invalid ingredients")
    @Description("Send invalid order creation request with auth and invalid ingredients")
    public void createOrderWithInvalidIngredients() {
        initialiseOrderWithInvalidIngredients();
        loginValidUser();

        Response createResponse = OrderApi.createOrder(testOrder, testUser.getAccessToken());

        assertThat("При неверном хэше ингредиентов ожидается код 500!", createResponse.statusCode(), is(SC_INTERNAL_SERVER_ERROR));
    }

    @After
    public void cleanUp() {
        if (testUser != null && testUser.getAccessToken() != null) {
            deleteUser();
        }
        testUser = null;
    }

    @Step("Initialise order with ingredients")
    public void initialiseOrderWithIngredients() {
        testOrder = new OrderModel(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"});
    }

    @Step("Initialise order with invalid ingredients")
    public void initialiseOrderWithInvalidIngredients() {
        testOrder = new OrderModel(new String[]{"blabla", "bsbsbs"});
    }

    @Step("Initialise order without ingredients")
    public void initialiseOrderWithoutIngredients() {
        testOrder = new OrderModel(new String[]{});
    }

    @Step("Login valid user")
    public void loginValidUser() {
        registerValidUser();

        LoginRequest loginRequest = new LoginRequest(testUser.getEmail(), testUser.getPassword());

        Response loginResponse = UserApi.loginUser(loginRequest);

        assertThat("Пользователь не был авторизован!", loginResponse.statusCode(), is(SC_OK));
    }

    @Step("Register valid user")
    public void registerValidUser() {
        initialiseValidUser();

        Response registerResponse = UserApi.createUser(testUser);
        testUser.setAccessToken(registerResponse.then().extract().path("accessToken").toString());

        assertThat("Пользователь не был создан!", registerResponse.statusCode(), is(SC_OK));
    }

    @Step("Initialise valid user")
    public void initialiseValidUser() {
        testUser = new UserModel("mymail" + new Random().nextInt(1000) + "@mail.ru", "password", "user" + new Random().nextInt(1000), null);
    }

    @Step("Delete user")
    public void deleteUser() {
        Response deleteResponse = UserApi.deleteUser(testUser.getAccessToken());

        assertThat("Пользователь не был удалён!", deleteResponse.statusCode(), is(SC_ACCEPTED));
    }
}
