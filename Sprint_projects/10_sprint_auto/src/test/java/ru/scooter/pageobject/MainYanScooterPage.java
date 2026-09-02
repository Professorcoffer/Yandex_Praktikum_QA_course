package ru.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MainYanScooterPage {
    private final WebDriver driver;

    //Локаторы кнопок заказа
    private final By upperOrderButton = By.className("Button_Button__ra12g");
    private final By bottomOrderButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");

    //Кнопка согласия на куки
    private final By cookieAcceptButton = By.id("rcc-confirm-button");

    //Для доп. задания!\/
    //Кнопка-логотип Самоката
    private final By scooterLogo = By.xpath(".//img[@alt='Scooter']");
    private final String mainURL = "https://qa-scooter.praktikum-services.ru/";

    //Кнопка-логотип Яндекса
    private final By yanLogo = By.xpath(".//img[@alt='Yandex']");
    private final String yanURL = "https://dzen.ru";

    //Кнопки и поле ввода для поиска заказа
    private final By openOrderCodeInputButton = By.className("Header_Link__1TAG7");
    private final By orderCodeInput = By.xpath(".//input[@placeholder='Введите номер заказа']");
    private final By submitOrderCodeButton = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");
    //Для доп. задания!/\

    public MainYanScooterPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод для нажатия на кнопку согласия на куки
    public void acceptCookies() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
    }

    //Единый метод для проверки FAQ
    public void checkFAQText(String index, String expectedText) {
        acceptCookies();
        driver.findElement(getQuestionHeading(index)).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(getPriceQuestionText(index)));
        String questionText = driver.findElement(getPriceQuestionText(index)).getText();
        assertEquals("Текст вопроса \"Сколько это стоит? И как оплатить?\" неверный!", questionText, expectedText);
    }

    public By getQuestionHeading(String index) {
        return By.xpath("(.//div[contains(@id, 'accordion__heading-')])["+ index +"]");
    }

    public By getPriceQuestionText(String index) {
        return By.xpath("(.//div[contains(@id, 'accordion__panel-')])["+ index +"]");
    }

    //Нажатия на кнопки заказа
    public void clickUpperOrderButton() {
        driver.findElement(upperOrderButton).click();
    }

    public void clickBottomOrderButton() {
        driver.findElement(bottomOrderButton).click();
    }

    //Далее - для доп. задания!
    //Методы для нажатия на лого Яндекса и Самоката
    public void ClickScooterLogoButton() {
        driver.findElement(scooterLogo).click();
    }

    public void clickYanLogoButton() {
        driver.findElement(yanLogo).click();
    }

    //Единый метод для проверки работы кнопки Самоката
    public void checkScooterLogoButton() {
        ClickScooterLogoButton();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlMatches(mainURL));
    }

    //Единый метод для проверки работы кнопки Яндекса
    public void checkYanLogoButton() {
        String scooterWindow = driver.getWindowHandle();
        clickYanLogoButton();

        Set<String> allWindows = driver.getWindowHandles();
        for(String window : allWindows) {
            if (!window.equals(scooterWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlMatches(yanURL));
    }

    //Нажатие на кнопку поиска заказа (Появления поля ввода номера заказа)
    public void clickOpenOrderCodeInputButton() {
        driver.findElement(openOrderCodeInputButton).click();
    }

    //Заполнение поля номера заказа
    public void fillOrderCodeInput(String code) {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(orderCodeInput));
        driver.findElement(orderCodeInput).sendKeys(code);
    }

    //Нажатие на кнопку поиска заказа
    public void clickSubmitOrderCodeButton() {
        driver.findElement(submitOrderCodeButton).click();
    }

    //Единый метод для проверки поиска несуществующего кода
    public void submitWrongOrderCode(String code) {
        clickOpenOrderCodeInputButton();
        fillOrderCodeInput(code);
        clickSubmitOrderCodeButton();
    }
}
