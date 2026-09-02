import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.educationservices.stellarburgers.MainStellarBurgersPage;
import utility.DriverBuilder;

public class SectionsTest {
    WebDriver driver;

    @Before
    public void startUp() throws Exception {
        driver = DriverBuilder.build();
        driver.get(DriverBuilder.URL);
    }

    @Test
    @DisplayName("Check buns section visibility")
    @Description("Take valid steps to check buns section visibility from main page")
    public void goToBunsSectionTest() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);

        main.checkBunsSectionButton();
    }

    @Test
    @DisplayName("Check sauces section visibility")
    @Description("Take valid steps to check sauces section visibility from main page")
    public void goToSaucesSectionTest() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);

        main.checkSaucesSectionButton();
    }

    @Test
    @DisplayName("Check fillings section visibility")
    @Description("Take valid steps to check fillings section visibility from main page")
    public void goToFillingsSectionTest() {
        MainStellarBurgersPage main = new MainStellarBurgersPage(driver);

        main.checkFillingsSectionButton();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
