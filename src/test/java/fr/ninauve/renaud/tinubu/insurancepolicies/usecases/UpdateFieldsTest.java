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
import static fr.ninauve.renaud.tinubu.insurancepolicies.usecases.CreateTest.CREATE_COMMAND_TEMPLATE;
import static fr.ninauve.renaud.tinubu.insurancepolicies.usecases.CreateTest.DATE_TIME_PATTERN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ExtendWith(UseCasesExtension.class)
public class UpdateFieldsTest implements UseCase {
    private static final String UPDATE_NAME = "update name";

    static final String UPDATE_NAME_COMMAND_TEMPLATE = """
            {
                "id": ${id},
                "name": "${name}"
            }
            """;

    private String applicationBaseUri;

    @Test
    void update_name_when_valid() {
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
                .extract();

        long id = createResponse.body().jsonPath().getLong("id");

        final String updateFieldsCommand = UPDATE_NAME_COMMAND_TEMPLATE
                .replace("${id}", "" + id)
                .replace("${name}", UPDATE_NAME);

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies/{id}")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(updateFieldsCommand)
                .when()
                .patch()
                .then()
                .statusCode(200);

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies/{id}")
                .pathParam("id", id)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("name", is(equalTo(UPDATE_NAME)))
                .body("status", is(equalTo(INSURANCE_POLICY_JSON_STATUS)))
                .body("startDate", is(equalTo(INSURANCE_POLICY_JSON_START_DATE)))
                .body("endDate", is(equalTo(INSURANCE_POLICY_JSON_END_DATE)))
                .body("createdDate", is(Matchers.matchesPattern(DATE_TIME_PATTERN)))
                .body("lastModifiedDate", is(Matchers.matchesPattern(DATE_TIME_PATTERN)));
    }

    @Test
    void fail_when_name_is_blank() {
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
                .extract();

        long id = createResponse.body().jsonPath().getLong("id");

        final String updateFieldsCommand = UPDATE_NAME_COMMAND_TEMPLATE
                .replace("${id}", "" + id)
                .replace("${name}", "");

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies/{id}")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(updateFieldsCommand)
                .when()
                .patch()
                .then()
                .statusCode(400);
    }

    @Override
    public void setApplicationBaseUri(String applicationBaseUri) {
        this.applicationBaseUri = applicationBaseUri;
    }
}
