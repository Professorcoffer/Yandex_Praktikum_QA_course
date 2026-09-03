import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
    public static Response createUser(UserModel user) {
        return given().header("Content-Type", "application/json").body(user).when().post("/api/auth/register");
    }

    public static Response loginUser(LoginRequest loginRequest) {
        return given().header("Content-Type", "application/json").body(loginRequest).when().post("/api/auth/login");
    }

    public static Response deleteUser(String accessToken) {
        return given().header("Authorization", accessToken).when().delete("/api/auth/user");
    }
}
