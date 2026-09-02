import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.scooter.pageobject.MainYanScooterPage;
import ru.scooter.pageobject.OrderPersonalInfoPage;
import ru.scooter.pageobject.TrackOrderPage;
import utility.DriverBuilder;

//Дополнительные тесты
public class AdditionalTestsChrome {
    WebDriver driver;

    //Метод для первоначальной настройки драйвера
    @Before
    public void startUp() {
        driver = DriverBuilder.buildChrome();
        driver.get(DriverBuilder.URL);
    }

    //Тест открытия главной страницы при нажатии на лого Самоката
    @Test
    public void scooterLogoTest() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        main.checkScooterLogoButton();
    }

    //Тест открытия поиска Яндекс при нажатии на лого Яндекса
    @Test
    public void yanLogoTest() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        main.checkYanLogoButton();
    }

    //Тест на ошибки полей в заказе
    @Test
    public void orderInputsErrorsTest() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        OrderPersonalInfoPage personal = new OrderPersonalInfoPage(driver);

        main.acceptCookies();
        main.clickBottomOrderButton();
        personal.clickOrderProceedButton();
        personal.checkInputsErrorMessages();
    }

    //Тест на поиск несуществующего заказа
    @Test
    public void wrongOrderCodeSearchTest() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        main.submitWrongOrderCode("00000");

        TrackOrderPage track = new TrackOrderPage(driver);
        track.checkOrderNotFoundImage();
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
