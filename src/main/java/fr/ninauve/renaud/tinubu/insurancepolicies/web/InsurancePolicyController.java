package fr.ninauve.renaud.tinubu.insurancepolicies.web;

import fr.ninauve.renaud.tinubu.insurancepolicies.command.CommandHandler;
import fr.ninauve.renaud.tinubu.insurancepolicies.command.CreateInsurancePolicyCommand;
import fr.ninauve.renaud.tinubu.insurancepolicies.command.UpdateInsurancePolicyCommand;
import fr.ninauve.renaud.tinubu.insurancepolicies.query.InsurancePolicyDetailsViewModel;
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

    @GetMapping("{id}")
    public InsurancePolicyDetailsViewModel details(@PathVariable long id) {
        return queryHandler.detailsOf(id);
    }

    @DeleteMapping("{id}")
    public void delete(long id) {
        commandHandler.deleteInsurancePolicy(id);
    }
}