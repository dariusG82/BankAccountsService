package eu.dariusgovedas.bankaccountservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeValidator implements ConstraintValidator<ValidTime, LocalTime> {

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

        try {
            formatter.parse(value.toString());
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
