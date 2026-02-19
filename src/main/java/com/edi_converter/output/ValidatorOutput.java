package com.edi_converter.output;

import org.apache.daffodil.japi.Diagnostic;

import java.util.ArrayList;
import java.util.List;

public class ValidatorOutput {
    public List<String> buildResult(List<Diagnostic> diagnostics) {
        List<String> errors = new ArrayList<>();
        if(diagnostics != null){
            errors.add("Validation contains "+diagnostics.size()+" Errors/Warnings");
            errors.add("---------------------------------------------------------------------------------------");
            for (Diagnostic diagnostic : diagnostics) {
                String errorMessage = buildErroMessage(diagnostic);
                errors.add(errorMessage);
            }
        } else {
            errors.add("Validation contains 0 Errors/Warnings");
            errors.add("---------------------------------------------------------------------------------------");
        }

        return errors;
    }

    private String buildErroMessage(Diagnostic diagnostic) {
        String errorMessage = "";
        String[] messageSplittetFull = diagnostic.getMessage().split("\\n");
        String firstPartSplittet = messageSplittetFull[0].replaceAll("Validation Error: ", "");
        errorMessage = firstPartSplittet+ " : " + messageSplittetFull[2] + " in message";
        return errorMessage;
    }
}
