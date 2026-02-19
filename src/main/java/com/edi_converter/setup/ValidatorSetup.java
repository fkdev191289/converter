package com.edi_converter.setup;

import java.util.Optional;

public class ValidatorSetup {
    public String validatorToString(Optional<Boolean> validate) {
        String validationMode = "Off";
        if (validate.orElse(false)) {
            validationMode = "Limited";
            //validationMode = "Full";
            return validationMode;
        } else {
            return validationMode;
        }
    }
}
