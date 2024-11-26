package fr.ninauve.renaud.tinubu.insurancepolicies.usecases;

import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCase;
import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCasesExtension;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ExtendWith(UseCasesExtension.class)
public class CreateTest implements UseCase {
    static final String DATE_PATTERN = "\\d\\d\\d\\d-\\d\\d-\\d\\d";
    static final String TIME_PATTERN = "\\d\\d:\\d\\d:\\d\\d\\.\\d*";
    static final String DATE_TIME_PATTERN = DATE_PATTERN + "T" + TIME_PATTERN + "Z";

    static final String CREATE_COMMAND_TEMPLATE = """
            {
                "name": "${name}",
                "status": "${status}",
                "startDate": "${startDate}",
                "endDate": "${endDate}"
            }
            """;

    private String applicationBaseUri;

    @Test
    void create_when_valid() {
        final String createCommand = CREATE_COMMAND_TEMPLATE
                .replace("${name}", INSURANCE_POLICY_NAME)
                .replace("${status}", INSURANCE_POLICY_JSON_STATUS)
                .replace("${startDate}", INSURANCE_POLICY_JSON_START_DATE)
                .replace("${endDate}", INSURANCE_POLICY_JSON_END_DATE);

        ExtractableResponse<Response> createResponse = given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies")
                .contentType(ContentType.JSON)
                .body(createCommand)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", is(equalTo(INSURANCE_POLICY_NAME)))
                .body("status", is(equalTo(INSURANCE_POLICY_JSON_STATUS)))
                .body("startDate", is(equalTo(INSURANCE_POLICY_JSON_START_DATE)))
                .body("endDate", is(equalTo(INSURANCE_POLICY_JSON_END_DATE)))
                .body("createdDate", is(Matchers.matchesPattern(DATE_TIME_PATTERN)))
                .body("lastModifiedDate", is(Matchers.matchesPattern(DATE_TIME_PATTERN)))
                .extract();

        long id = createResponse.body().jsonPath().getLong("id");

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies/{id}")
                .pathParam("id", id)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", is(equalTo(INSURANCE_POLICY_NAME)))
                .body("status", is(equalTo(INSURANCE_POLICY_JSON_STATUS)))
                .body("startDate", is(equalTo(INSURANCE_POLICY_JSON_START_DATE)))
                .body("endDate", is(equalTo(INSURANCE_POLICY_JSON_END_DATE)))
                .body("createdDate", is(Matchers.matchesPattern(DATE_TIME_PATTERN)))
                .body("lastModifiedDate", is(Matchers.matchesPattern(DATE_TIME_PATTERN)));
    }

    @Test
    void fail_when_name_is_blank() {
        final String createCommand = CREATE_COMMAND_TEMPLATE
                .replace("${name}", "")
                .replace("${status}", INSURANCE_POLICY_JSON_STATUS)
                .replace("${startDate}", INSURANCE_POLICY_JSON_START_DATE)
                .replace("${endDate}", INSURANCE_POLICY_JSON_END_DATE);

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies")
                .contentType(ContentType.JSON)
                .body(createCommand)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Override
    public void setApplicationBaseUri(String applicationBaseUri) {
        this.applicationBaseUri = applicationBaseUri;
    }
}
