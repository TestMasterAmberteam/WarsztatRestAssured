import Config.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Testy API osób")
public class PeopleTest {
    TestConfig testConfig = new TestConfig();
    @BeforeEach
    void setup(){
        testConfig.setupEnvironment(testConfig.getPersonApiUri());
    }

    @Test
    @DisplayName("Test autentykacji - Basic Auth")
    void basicAuth(){
        RestAssured.given().auth().basic("AmberTeam", "AmberPassword")
                .when()
                .get("/people/all")
                .then()
                .assertThat()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());
    }
}
