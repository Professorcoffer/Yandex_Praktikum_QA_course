import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.scooter.pageobject.MainYanScooterPage;
import utility.DriverBuilder;

//Тест на раздел FAQ в Chrome
@RunWith(Parameterized.class)
public class FAQTextTestChrome {
    private WebDriver driver;

    private final String questionIndex;
    private final String questionText;

    public FAQTextTestChrome(String questionIndex, String questionText) {
        this.questionIndex = questionIndex;
        this.questionText = questionText;
    }

    @Parameterized.Parameters(name="Тестовые данные: {0} {1}")
    public static Object[][] getFAQCredentials() {
        return new Object[][] {
                {"1", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"2", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"3", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"4", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"5", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"6", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"7", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"8", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    //Метод для первоначальной настройки драйвера
    @Before
    public void startUp() {
        driver = DriverBuilder.buildChrome();
        driver.get(DriverBuilder.URL);
    }

    @Test
    public void testFAQTexts() {
        MainYanScooterPage main = new MainYanScooterPage(driver);
        main.checkFAQText(questionIndex, questionText);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}