package fr.ninauve.renaud.tinubu.insurancepolicies.usecases;

import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCase;
import fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension.UseCasesExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@ExtendWith(UseCasesExtension.class)
public class OpenApiTest implements UseCase {
    private String applicationBaseUri;

    @Test
    void list_insurancePolicies_paths() {
        given()
                .baseUri(applicationBaseUri)
                .when()
                .get("/v3/api-docs")
                .then()
                .statusCode(200)
                .body("paths.'/insurancePolicies'.keySet()", hasItems("get", "post"))
                .body("paths.'/insurancePolicies/{id}'.keySet()", hasItems("get", "put", "delete"));
    }

    @Override
    public void setApplicationBaseUri(String applicationBaseUri) {
        this.applicationBaseUri = applicationBaseUri;
    }
}
