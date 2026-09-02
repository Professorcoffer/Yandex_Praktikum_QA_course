import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import org.junit.BeforeClass;

public class TestBase {
    @BeforeClass
    public static void configure() {
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 25000)
                .setParam("http.socket.timeout", 25000));
    }
}
