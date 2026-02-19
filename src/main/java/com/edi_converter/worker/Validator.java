package com.edi_converter.worker;

import org.apache.daffodil.japi.Diagnostic;
import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;

import org.smooks.io.source.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;

public class Validator {
    public List<Diagnostic> validate(Smooks smooks, byte[] content) {
        ExecutionContext executionContext = smooks.createExecutionContext();
        smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(content)));
        smooks.close();
        return executionContext.get(org.smooks.cartridges.dfdl.parser.DfdlParser.DIAGNOSTICS_TYPED_KEY);
    }
}
