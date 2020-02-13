import Config.TestConfig;
import Model.CertificateModel;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Testy API certyfikatów")
public class CertificatesTest {

    TestConfig testConfig = new TestConfig();

    @BeforeEach
    void setupCertificates(){
        testConfig.setupEnvironment(testConfig.getCertificateApiVersion());
    }

    @Test
    @DisplayName("Pobieranie wszystkich certyfikatów")
    void getAllCertificates(){
        RestAssured.given()
                .when()
                .get("/certificates/all")
                .then().log().body()
                .assertThat()
                .statusCode(200)
                .body("id",notNullValue());
    }

    @Test
    @DisplayName("Dodawanie certyfikatu")
    void addCertificate(){
        CertificateModel certificateModel = new CertificateModel().setName("Wstęp do automatyzacji API z JAVA")
                .setOrganization("ConSelenium").setPeriod("Bezterminowo").setTrade("IT");

        String newCertificateId = RestAssured.given()
                .config(RestAssuredConfig.config().logConfig(LogConfig.logConfig().blacklistHeader("Accept")))
                .when()
                .contentType(testConfig.getContentType())
                .log().all()
                .body(certificateModel)
                .post("/certificate")

                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo(certificateModel.getName()))
                .extract()
                .response().jsonPath().get("id").toString();

        RestAssured.given()
                .when()
                .param("id", newCertificateId)
                .get("/certificate")
                .then().log().body()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(certificateModel.getName()));
    }
    
}
