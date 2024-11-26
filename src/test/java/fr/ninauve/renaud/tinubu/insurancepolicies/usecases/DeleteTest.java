package fr.ninauve.renaud.tinubu.insurancepolicies.usecases;

import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCase;
import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCasesExtension;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static fr.ninauve.renaud.tinubu.insurancepolicies.usecases.CreateTest.CREATE_COMMAND_TEMPLATE;
import static io.restassured.RestAssured.given;

@ExtendWith(UseCasesExtension.class)
public class DeleteTest implements UseCase {
    private String applicationBaseUri;

    @Test
    void delete() {
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

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies/{id}")
                .pathParam("id", id)
                .when()
                .delete()
                .then()
                .statusCode(200);

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies/{id}")
                .pathParam("id", id)
                .when()
                .get()
                .then()
                .statusCode(404);
    }

    @Override
    public void setApplicationBaseUri(String applicationBaseUri) {
        this.applicationBaseUri = applicationBaseUri;
    }
}
