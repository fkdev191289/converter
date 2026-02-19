package com.edi_converter.worker;

import com.edi_converter.defaults.DefaultSeperators;
import com.edi_converter.doc.DocBuilder;
import com.edi_converter.entity.EdifactConverter;
import com.edi_converter.output.ValidatorOutput;
import com.edi_converter.setup.SmooksSetup;
import com.edi_converter.setup.ValidatorSetup;
import com.edi_converter.util.UtilSetup;
import org.apache.commons.io.FileUtils;
import org.apache.daffodil.japi.Diagnostic;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.Smooks;
import org.smooks.api.SmooksException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EngineExecuter {
    private static final Logger log = LoggerFactory.getLogger(EngineExecuter.class);
    private DocBuilder docBuilder;
    private UtilSetup utilSetup;
    private Validator validator;
    private ValidatorSetup validatorSetup;
    private SmooksSetup smooksSetup;
    private ValidatorOutput validatorOutput;
    private Smooks smooks;

    public EngineExecuter() {
        this.docBuilder = new DocBuilder();
        this.utilSetup = new UtilSetup();
        this.validator = new Validator();
        this.validatorSetup = new ValidatorSetup();
        this.smooksSetup = new SmooksSetup();
        this.validatorOutput = new ValidatorOutput();
    }

    public List<String> setupAndValidate(String filename, String directory, List<String> messageTypes, Optional<Boolean> validate, Map<String,String> seperators, Boolean engine) {
        byte[] message = null;
        try {
            message = FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Document doc = docBuilder.buildDocForEdifact(directory, messageTypes, validatorSetup.validatorToString(validate), engine);
        ByteArrayOutputStream baos = utilSetup.getInputStreamFromDoc(doc);
        Smooks smooks = smooksSetup.setup(new ByteArrayInputStream(baos.toByteArray()));
        List<String> errors = new ArrayList<>();
        try {
            List<Diagnostic> diagnostics = validator.validate(smooks, message);
            errors = validatorOutput.buildResult(diagnostics);
        } catch (SmooksException se) {
            errors.add(se.getCause().getMessage().split("\\R", 2)[0]);
        }
        return errors;
    }

    public List<String> setupAndConvert(String filename, String directory, List<String> messageTypes, Optional<Boolean> validate, Map<String,String> seperators, boolean typeOfConverter) {
        byte[] message = null;
        try {
            message = FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //message = changeUnparserNsAndGetBytes(messageFilePath, typeOfConverter, directory, encoding);
        
        Document doc = docBuilder.buildDocForEdifact(directory, messageTypes, validatorSetup.validatorToString(validate), typeOfConverter);
        ByteArrayOutputStream baos = utilSetup.getInputStreamFromDoc(doc);
        Smooks smooks = smooksSetup.setup(new ByteArrayInputStream(baos.toByteArray()));
        String result = smooksSetup.convert(smooks, message);
        
        if(typeOfConverter){
            //String messageRefactor = changeNsParser(directory, result);
            result = utilSetup.prettyPrintByTransformer(result, 2, true);
        }
         
        List<String> results = new ArrayList<>();
        results.add(result);
        return results;
    }

    public void setupEngine(String directory, List<String> messageTypes, Optional<Boolean> validate, boolean engine) {
        Document doc = docBuilder.buildDocForEdifact(directory, messageTypes, validatorSetup.validatorToString(validate), engine);
        ByteArrayOutputStream baos = utilSetup.getInputStreamFromDoc(doc);
        log.info("Creating Context can take up to one minute. Starting now...");
        this.smooks = smooksSetup.setupAndCreateContext(new ByteArrayInputStream(baos.toByteArray()));
        log.info("Creating Context finished.");
    }
    
    public List<String> runConverter(String filename, boolean engine) {
        byte[] message = null;
        try {
            message = FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String result = smooksSetup.convert(this.smooks, message);

        if(engine){
            result = utilSetup.prettyPrintByTransformer(result, 2, true);
        }

        List<String> results = new ArrayList<>();
        results.add(result);
        return results;
    }

    public List<String> runValidate(String filename) {
        byte[] message = null;
        try {
            message = FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> errors = new ArrayList<>();
        try {
            List<Diagnostic> diagnostics = validator.validate(this.smooks, message);
            errors = validatorOutput.buildResult(diagnostics);
        } catch (SmooksException se) {
            errors.add(se.getCause().getMessage().split("\\R", 2)[0]);
        }
        return errors;
    }
    
}
