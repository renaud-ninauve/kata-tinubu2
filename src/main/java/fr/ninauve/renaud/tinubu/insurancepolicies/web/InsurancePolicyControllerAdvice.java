package fr.ninauve.renaud.tinubu.insurancepolicies.web;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class InsurancePolicyControllerAdvice {

    @ExceptionHandler(InsurancePolicyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String insurancePolicyNotFoundHandler(InsurancePolicyNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> invalidHandler(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
                .map(err -> (FieldError) err)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
