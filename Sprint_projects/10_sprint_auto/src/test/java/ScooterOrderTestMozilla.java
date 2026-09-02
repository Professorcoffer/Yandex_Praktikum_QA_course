import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.scooter.pageobject.MainYanScooterPage;
import ru.scooter.pageobject.OrderPersonalInfoPage;
import ru.scooter.pageobject.OrderScooterInfoPage;
import utility.DriverBuilder;

//Тесты на составление заказа в Firefox
@RunWith(Parameterized.class)
public class ScooterOrderTestMozilla {
    WebDriver driver;

    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String date;
    private final String color;
    private final String commentary;

    public ScooterOrderTestMozilla(String name, String surname, String address, String phone, String date, String color, String commentary) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.color = color;
        this.commentary = commentary;
    }

    @Parameterized.Parameters(name="Тестовые данные: {0} {1} {2} {3} {4} {5} {6}")
    public static Object[][] getCredentials() {
        return new Object[][] {
                {"Максим", "Бугаев", "г. Москва, Ивановская площадь", "88005553535", "03.11.2026", "black", "Ровно в полдень!"},
                {"Вася", "Пупкин", "г. Сургут, ул.Пушкина, д.14", "+79225553535", "13.12.2026", "grey", ""}
        };
    }

    //Метод для первоначальной настройки драйвера
    @Before
    public void startUp() {
        driver = DriverBuilder.buildFirefox();
        driver.get(DriverBuilder.URL);
    }

    //Составление заказа через верхнюю кнопку
    @Test
    public void testUpperButton() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        OrderPersonalInfoPage personalInfo = new OrderPersonalInfoPage(driver);
        OrderScooterInfoPage scooterInfo = new OrderScooterInfoPage(driver);

        main.acceptCookies();
        main.clickUpperOrderButton();

        personalInfo.fillOrderCredentials(name, surname, address, phone);
        personalInfo.clickOrderProceedButton();

        scooterInfo.fillOrderCredentials(date, color, commentary);
        scooterInfo.acceptOrder();
        scooterInfo.confirmOrder();
        scooterInfo.checkForOrderCompletion();
    }

    //Составление заказа через нижнюю кнопку
    @Test
    public void testBottomButton() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        OrderPersonalInfoPage personalInfo = new OrderPersonalInfoPage(driver);
        OrderScooterInfoPage scooterInfo = new OrderScooterInfoPage(driver);

        main.acceptCookies();
        main.clickBottomOrderButton();

        personalInfo.fillOrderCredentials(name, surname, address, phone);
        personalInfo.clickOrderProceedButton();

        scooterInfo.fillOrderCredentials(date, color, commentary);
        scooterInfo.acceptOrder();
        scooterInfo.confirmOrder();
        scooterInfo.checkForOrderCompletion();
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
