package fr.ninauve.renaud.tinubu.insurancepolicies.usecases;

import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCase;
import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCasesExtension;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static fr.ninauve.renaud.tinubu.insurancepolicies.usecases.CreateTest.CREATE_COMMAND_TEMPLATE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

@ExtendWith(UseCasesExtension.class)
public class ListTest implements UseCase {
    private String applicationBaseUri;

    @Test
    void list() {
        final String createCommand = CREATE_COMMAND_TEMPLATE
                .replace("${name}", INSURANCE_POLICY_NAME)
                .replace("${status}", INSURANCE_POLICY_STATUS)
                .replace("${startDate}", INSURANCE_POLICY_JSON_START_DATE)
                .replace("${endDate}", INSURANCE_POLICY_JSON_END_DATE);

        for (int i = 0; i < 123; i++) {

            given()
                    .baseUri(applicationBaseUri)
                    .basePath("/insurancePolicies")
                    .contentType(ContentType.JSON)
                    .body(createCommand)
                    .when()
                    .post()
                    .then()
                    .statusCode(201);
        }

        given()
                .baseUri(applicationBaseUri)
                .basePath("/insurancePolicies")
                .queryParam("page", 2)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("insurancePolicies", hasSize(3))
                .body("pageInfo.page", is(equalTo(2)))
                .body("pageInfo.totalElements", is(equalTo(123)))
                .body("pageInfo.totalPages", is(equalTo(41)));
    }

    @Override
    public void setApplicationBaseUri(String applicationBaseUri) {
        this.applicationBaseUri = applicationBaseUri;
    }
}
