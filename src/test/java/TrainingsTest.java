import Config.TestConfig;
import Model.TrainingModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Testy API szkoleń")
public class TrainingsTest {
    TestConfig testConfig = new TestConfig();
    private String testContentType = testConfig.getContentType();

    @BeforeEach
    void setup(){
        testConfig.setupEnvironment(testConfig.getTrainingsApiVersion());
    }


    @Test
    @DisplayName("Test pobierania szkoleń")
    void getAllTrainings(){
        RestAssured.given()
                .when()
                .get("/trainings/all")
                .then()
                .assertThat().statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Test poprawnego dodania szkolenia")
    void addTraining(){
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "szkolenie");
        payload.put("place", "łódź");
        payload.put("price", 10);
        payload.put("trainer", "Marek");
        payload.put("maxParticipants", 5);

        Response trainingBody = RestAssured.given()
                .when()
                .contentType(testContentType)
                .body(payload)
                .log().uri()
                .post("/training")

                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .response();

        String trainingId = trainingBody.jsonPath().get("id").toString();

        RestAssured.given()
                .when()
                .pathParam("id", trainingId)
                .get("/training/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("trainer", equalTo(payload.get("trainer")))
                .log().body();
    }

    @Test
    @DisplayName("Test edycji szkolenia")
    void editTraining(){

        TrainingModel newTrainingBody = new TrainingModel().setName("nowe").setPlace("warszawa").setMaxParticipants(5)
                .setPrice(1000).setTrainer("Stefan");

        String trainingId = RestAssured.given()
                .when()
                .contentType(testContentType)
                .body(newTrainingBody)
                .log().uri()
                .post("/training")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .response()
                .jsonPath().get("id").toString();

        TrainingModel editedTrainingBody = new TrainingModel().setName("Szkolenie").setPlace("Kraków").setMaxParticipants(5)
                .setPrice(1000).setTrainer("Stefan");


        RestAssured.given()
                .when()
                .contentType(testContentType)
                .body(editedTrainingBody)
                .log().method()
                .pathParam("id", trainingId)
                .put("/training/{id}")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(editedTrainingBody.getName()));

        RestAssured.given()
                .when()
                .pathParam("id", trainingId)

                .get("/training/{id}")
                .then()
                .assertThat().statusCode(200)
                .body("name", equalTo(editedTrainingBody.getName()))
                .log().all();

    }


    @Test
    @DisplayName("Usuwanie szkolenia")
    void deleteTraining(){
        TrainingModel trainingForDeletion = new TrainingModel().setName("usuwamy").setPlace("Gdańsk").setMaxParticipants(5)
                .setPrice(1000).setTrainer("Piotrek");

        String trainingId = RestAssured.given()
                .when()
                .contentType(testContentType)
                .body(trainingForDeletion)
                .log().uri()
                .post("/training")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .response()
                .jsonPath().get("id").toString();

        RestAssured.given()
                .when()
                .pathParam("id", trainingId)
                .delete("/training/{id}")
                .then()
                .assertThat()
                .statusCode(200);

        RestAssured.given()
                .when()
                .pathParam("id", trainingId)
                .delete("/training/{id}")
                .then()
                .assertThat()
                .body("message", equalTo("Brak szkolenia o id: "+trainingId))
                .statusCode(404);

        RestAssured.given()
                .when()
                .param("id", trainingId)
                .get("/training")
                .then()
                .assertThat()
                .statusCode(404);
    }





}
