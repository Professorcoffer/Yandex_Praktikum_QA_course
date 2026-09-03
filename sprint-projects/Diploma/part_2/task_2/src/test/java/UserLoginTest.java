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
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserLoginTest extends TestBase{
    private UserModel testUser;

    @Before
    public void prepareUser() {
        registerValidUser();
    }

    @Test
    @DisplayName("Login valid user")
    @Description("Send valid login request with valid user")
    public void loginValidUser() {
        LoginRequest loginRequest = new LoginRequest(testUser.getEmail(), testUser.getPassword());

        Response loginResponse = UserApi.loginUser(loginRequest);

        assertThat("Пользователь не был авторизован!", loginResponse.statusCode(), is(SC_OK));
        assertThat("В ответе нет токена доступа!", loginResponse.path("accessToken"), startsWith("Bearer"));
    }

    @Test
    @DisplayName("Login user with invalid email")
    @Description("Send invalid login request with user with invalid email")
    public void loginUserWithInvalidEmail() {
        LoginRequest loginRequest = new LoginRequest("blabla@mail.ru", testUser.getPassword());

        Response loginResponse = UserApi.loginUser(loginRequest);

        assertThat("Для неверных данных при авторизации ожидается код 401!", loginResponse.statusCode(), is(SC_UNAUTHORIZED));
        assertThat("Неверная структура ответа!", loginResponse.path("message"), is("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login user with invalid password")
    @Description("Send invalid login request with user with invalid password")
    public void loginUserWithInvalidPassword() {
        LoginRequest loginRequest = new LoginRequest(testUser.getEmail(),"blabla");

        Response loginResponse = UserApi.loginUser(loginRequest);

        assertThat("Для неверных данных при авторизации ожидается код 401!", loginResponse.statusCode(), is(SC_UNAUTHORIZED));
        assertThat("Неверная структура ответа!", loginResponse.path("message"), is("email or password are incorrect"));
    }

    @After
    public void cleanUp() {
        if (testUser != null && testUser.getAccessToken() != null) {
            deleteUser();
        }
        testUser = null;
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
