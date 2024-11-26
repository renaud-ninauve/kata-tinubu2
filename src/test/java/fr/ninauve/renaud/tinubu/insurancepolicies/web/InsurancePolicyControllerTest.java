package fr.ninauve.renaud.tinubu.insurancepolicies.web;

import fr.ninauve.renaud.tinubu.insurancepolicies.command.CommandHandler;
import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.query.InsurancePolicyDetailsViewModel;
import fr.ninauve.renaud.tinubu.insurancepolicies.query.QueryHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InsurancePolicyController.class)
@Import(InsurancePolicyController.class)
class InsurancePolicyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommandHandler commandHandler;
    @MockBean
    QueryHandler queryHandler;

    @Test
    void return_policy_when_get() throws Exception {
        given(queryHandler.detailsOf(INSURANCE_POLICY_ID))
                .willReturn(InsurancePolicyDetailsViewModel.builder()
                        .id(INSURANCE_POLICY_ID)
                        .name(INSURANCE_POLICY_NAME)
                        .build());

        mockMvc.perform(get("/insurancePolicies/{id}", INSURANCE_POLICY_ID))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": ${id},
                            "name": "${name}"
                        }
                        """.replace("${id}", INSURANCE_POLICY_JSON_ID)
                        .replace("${name}", INSURANCE_POLICY_NAME)));
    }

    @Test
    void return_404_when_get_unknown_insurance_policy() throws Exception {
        given(queryHandler.detailsOf(INSURANCE_POLICY_ID))
                .willThrow(InsurancePolicyNotFoundException.class);

        mockMvc.perform(get("/insurancePolicies/" + INSURANCE_POLICY_ID))
                .andExpect(status().isNotFound());
    }
}