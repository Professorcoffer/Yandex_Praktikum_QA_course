import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.educationservices.stellarburgers.*;
import utility.DriverBuilder;

import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogInTest extends TestBase{
    WebDriver driver;
    private UserModel testUser;

    String tempEmail;
    final String password = "password";

    @Before
    public void startUp() throws Exception {
        driver = DriverBuilder.build();
        driver.get(DriverBuilder.URL);

        initialiseValidUser();

        Response registerResponse = UserApi.createUser(testUser);
        testUser.setAccessToken(registerResponse.then().extract().path("accessToken").toString());

        assertThat("Пользователь не был создан!", registerResponse.statusCode(), is(SC_OK));
    }

    @Test
    @DisplayName("Login from order button")
    @Description("Take valid steps to log in from order button on main page")
    public void orderButtonLogIn() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);
        LogInPage log = new LogInPage(driver);

        main.clickOrderLogInButton();
        log.logIn(tempEmail, password);
        main.checkSuccessfulLogIn();
    }

    @Test
    @DisplayName("Login from account button")
    @Description("Take valid steps to log in from account button on main page")
    public void accountButtonLogIn() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);
        LogInPage log = new LogInPage(driver);

        main.clickHeaderLogInButton();
        log.logIn(tempEmail, password);
        main.checkSuccessfulLogIn();
    }

    @Test
    @DisplayName("Login from registration page")
    @Description("Take valid steps to log in from registration page")
    public void registrationButtonLogIn() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);
        LogInPage log = new LogInPage(driver);
        RegisterPage register = new RegisterPage(driver);

        main.clickHeaderLogInButton();
        log.clickRegisterButton();
        register.clickLogInButton();

        log.logIn(tempEmail, password);
        main.checkSuccessfulLogIn();
    }

    @Test
    @DisplayName("Login from recover password page")
    @Description("Take valid steps to log in from recover password page")
    public void recoverPasswordLogIn() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);
        LogInPage log = new LogInPage(driver);
        PasswordRecoverPage pass = new PasswordRecoverPage(driver);

        main.clickHeaderLogInButton();
        log.clickRecoverPasswordButton();
        pass.clickLogInButton();

        log.logIn(tempEmail, password);
        main.checkSuccessfulLogIn();
    }

    @After
    public void logOutAndTearDown() {
        try {
            if (testUser != null && testUser.getAccessToken() != null) {
                deleteUser();
            }
            testUser = null;
        } finally {
            driver.quit();
        }
    }

    @Step("Initialise valid user")
    public void initialiseValidUser() {
        tempEmail = "ValidGuy" + new Random().nextInt(1000) + "@mail.ru";
        testUser = new UserModel(tempEmail, "password", "ValidGuy" + new Random().nextInt(1000), null);
    }

    @Step("Delete user")
    public void deleteUser() {
        Response deleteResponse = UserApi.deleteUser(testUser.getAccessToken());

        assertThat("Пользователь не был удалён!", deleteResponse.statusCode(), is(SC_ACCEPTED));
    }
}
