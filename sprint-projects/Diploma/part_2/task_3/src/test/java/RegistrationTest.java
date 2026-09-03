import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.educationservices.stellarburgers.LogInPage;
import ru.educationservices.stellarburgers.MainStellarBurgersPage;
import ru.educationservices.stellarburgers.RegisterPage;
import utility.DriverBuilder;

import java.util.Random;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegistrationTest extends TestBase{
    WebDriver driver;
    private UserModel testUser;

    @Before
    public void startUp() throws Exception {
        driver = DriverBuilder.build();
        driver.get(DriverBuilder.URL);
    }

    @Test
    @DisplayName("Valid register")
    @Description("Take valid steps to register from main page")
    public void validRegistrationTest() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);
        LogInPage login = new LogInPage(driver);
        RegisterPage register = new RegisterPage(driver);

        testUser = new UserModel("ValidGuy" + new Random().nextInt(1000) + "@mail.ru", "password", "ValidGuy" + new Random().nextInt(1000), null);

        main.clickHeaderLogInButton();
        login.clickRegisterButton();
        register.registerValidly(testUser.getName(), testUser.getEmail(), testUser.getPassword());
        login.checkSuccessfulRegistration();
    }

    @Test
    @DisplayName("Check for invalid password error")
    @Description("Take steps to fill in invalid from main page")
    public void invalidPasswordRegistrationTest() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);
        LogInPage login = new LogInPage(driver);
        RegisterPage register = new RegisterPage(driver);

        main.clickHeaderLogInButton();
        login.clickRegisterButton();
        register.registerWithInvalidPassword();
    }

    @After
    public void logOutAndTearDown() {
        try {
            if (testUser != null) {
                logInUser();
                deleteUser();
                testUser = null;
            }
        } finally {
            driver.quit();
        }
    }

    @Step
    public void logInUser() {
        LoginRequest loginRequest = new LoginRequest(testUser.getEmail(), testUser.getPassword());
        Response loginResponse = UserApi.loginUser(loginRequest);
        testUser.setAccessToken(loginResponse.then().extract().path("accessToken").toString());
    }

    @Step("Delete user")
    public void deleteUser() {
        Response deleteResponse = UserApi.deleteUser(testUser.getAccessToken());

        assertThat("Пользователь не был удалён!", deleteResponse.statusCode(), is(SC_ACCEPTED));
    }
}
