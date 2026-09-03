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

public class UserCreationTest extends TestBase {
    private UserModel testUser;

    @Test
    @DisplayName("Register valid user")
    @Description("Send valid register request with valid user")
    public void registerValidUser() {
        initialiseValidUser();

        Response registerResponse = UserApi.createUser(testUser);
        testUser.setAccessToken(registerResponse.then().extract().path("accessToken").toString());

        assertThat("Пользователь не был создан!", registerResponse.statusCode(), is(SC_OK));
        assertThat("В ответе нет токена доступа!", registerResponse.path("accessToken"), startsWith("Bearer"));
    }

    @Test
    @DisplayName("Register already existing user")
    @Description("Send valid register request with already existing user")
    public void registerExistingUser() {
        initialiseValidUser();

        Response firstRegisterResponse = UserApi.createUser(testUser);
        testUser.setAccessToken(firstRegisterResponse.then().extract().path("accessToken").toString());

        assertThat("Первый пользователь не был создан!", firstRegisterResponse.statusCode(), is(SC_OK));
        assertThat("В ответе нет токена доступа!", firstRegisterResponse.path("accessToken"), startsWith("Bearer"));

        Response secondRegisterResponse = UserApi.createUser(testUser);

        assertThat("Для регистрации уже существующего пользователя ожидается код 403!", secondRegisterResponse.statusCode(), is(SC_FORBIDDEN));
        assertThat("Неверная структура ответа!", secondRegisterResponse.path("message"), is("User already exists"));
    }

    @Test
    @DisplayName("Register user without email")
    @Description("Send invalid register request with user without email")
    public void registerUserWithoutEmail() {
        initialiseUserWithoutEmail();

        Response registerResponse = UserApi.createUser(testUser);

        assertThat("Для незаполненных обязательных полей ожидается код 403!", registerResponse.statusCode(), is(SC_FORBIDDEN));
        assertThat("Неверная структура ответа!", registerResponse.path("message"), is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Register user without password")
    @Description("Send invalid register request with user without password")
    public void registerUserWithoutPassword() {
        initialiseUserWithoutPassword();

        Response registerResponse = UserApi.createUser(testUser);

        assertThat("Для незаполненных обязательных полей ожидается код 403!", registerResponse.statusCode(), is(SC_FORBIDDEN));
        assertThat("Неверная структура ответа!", registerResponse.path("message"), is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Register user without name")
    @Description("Send invalid register request with user without name")
    public void registerUserWithoutName() {
        initialiseUserWithoutName();

        Response registerResponse = UserApi.createUser(testUser);

        assertThat("Для незаполненных обязательных полей ожидается код 403!", registerResponse.statusCode(), is(SC_FORBIDDEN));
        assertThat("Неверная структура ответа!", registerResponse.path("message"), is("Email, password and name are required fields"));
    }

    @After
    public void cleanUp() {
        if (testUser != null && testUser.getAccessToken() != null) {
            deleteUser();
        }
        testUser = null;
    }

    @Step("Initialise valid user")
    public void initialiseValidUser() {
        testUser = new UserModel("mymail" + new Random().nextInt(1000) + "@mail.ru", "password", "user" + new Random().nextInt(1000), null);
    }

    @Step("Initialise user without email")
    public void initialiseUserWithoutEmail() {
        testUser = new UserModel(null, "password", "user" + new Random().nextInt(1000), null);
    }

    @Step("Initialise user without password")
    public void initialiseUserWithoutPassword() {
        testUser = new UserModel("mymail" + new Random().nextInt(1000) + "@mail.ru", null, "user" + new Random().nextInt(1000), null);
    }

    @Step("Initialise user without name")
    public void initialiseUserWithoutName() {
        testUser = new UserModel("mymail" + new Random().nextInt(1000) + "@mail.ru", "password", null, null);
    }

    @Step("Delete user")
    public void deleteUser() {
        Response deleteResponse = UserApi.deleteUser(testUser.getAccessToken());

        assertThat("Пользователь не был удалён!", deleteResponse.statusCode(), is(SC_ACCEPTED));
    }
 }
