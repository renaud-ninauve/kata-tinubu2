package fr.ninauve.renaud.tinubu.insurancepolicies.web;

import fr.ninauve.renaud.tinubu.insurancepolicies.command.CommandHandler;
import fr.ninauve.renaud.tinubu.insurancepolicies.command.CreateInsurancePolicyCommand;
import fr.ninauve.renaud.tinubu.insurancepolicies.command.UpdateFieldsInsurancePolicyCommand;
import fr.ninauve.renaud.tinubu.insurancepolicies.command.UpdateInsurancePolicyCommand;
import fr.ninauve.renaud.tinubu.insurancepolicies.query.InsurancePolicyDetailsViewModel;
import fr.ninauve.renaud.tinubu.insurancepolicies.query.InsurancePolicyListViewModel;
import fr.ninauve.renaud.tinubu.insurancepolicies.query.QueryHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("insurancePolicies")
@RequiredArgsConstructor
public class InsurancePolicyController {
    private final CommandHandler commandHandler;
    private final QueryHandler queryHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsurancePolicyDetailsViewModel post(@RequestBody @Valid CreateInsurancePolicyCommand command) {
        long id = commandHandler.createInsurancePolicy(command);
        return queryHandler.detailsOf(id);
    }

    @PutMapping("{id}")
    public void update(@RequestBody @Valid UpdateInsurancePolicyCommand command) {
        commandHandler.updateInsurancePolicy(command);
    }

    @PatchMapping("{id}")
    public void updateFields(@RequestBody @Valid UpdateFieldsInsurancePolicyCommand command) {
        commandHandler.updateFieldsInsurancePolicy(command);
    }

    @GetMapping
    public InsurancePolicyListViewModel list(@RequestParam(defaultValue = "0") int page) {
        return queryHandler.list(page);
    }

    @GetMapping("/{id}")
    public InsurancePolicyDetailsViewModel details(@PathVariable long id) {
        return queryHandler.detailsOf(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        commandHandler.deleteInsurancePolicy(id);
    }
}