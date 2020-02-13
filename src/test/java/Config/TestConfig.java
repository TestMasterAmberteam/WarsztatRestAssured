package Config;

import io.restassured.RestAssured;


public class TestConfig {


    private final static String TRAININGS_API_VERSION = "/v1";
    private final static String CONTENT_TYPE = "application/json";
    private final static String CERTIFICATE_API_VERSION = "/v1";


    private final static String PERSON_API_URI="/security";

    public void setupEnvironment(String apiVersion) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9999;
        RestAssured.basePath= "/api/rest"+apiVersion;
    }
    public String getPersonApiUri() {
        return PERSON_API_URI;
    }

    public String getTrainingsApiVersion() {
        return TRAININGS_API_VERSION;
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }

    public String getCertificateApiVersion() {
        return CERTIFICATE_API_VERSION;
    }

}
