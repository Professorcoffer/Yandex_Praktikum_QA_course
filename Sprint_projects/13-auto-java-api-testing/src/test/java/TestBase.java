import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import org.junit.BeforeClass;

public class TestBase {
    @BeforeClass
    public static void startUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 25000)
                .setParam("http.socket.timeout", 25000));
    }

}
