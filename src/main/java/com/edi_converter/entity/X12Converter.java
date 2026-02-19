package com.edi_converter.entity;

import com.edi_converter.Main;
import com.edi_converter.worker.EngineExecuter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

public class X12Converter implements ConverterAbstract {
    private static final Logger log = LoggerFactory.getLogger(X12Converter.class);
    private String directory = "004010";
    private List<String> messagetypes = Arrays.asList("850");
    private Map<String,String> seperators = new HashMap<>();
    private boolean validate = true;

    public X12Converter() {
    }


    public X12Converter(String directory, List<String> messagetypes) {
        this.directory = directory;
        this.messagetypes = messagetypes;
    }

    public X12Converter createX12Converter(Map<String, String> argsConsumer) {
        String direction = "004010";
        if(argsConsumer.containsKey("directory")){
            log.info("provided directory: {}", argsConsumer.get("directory"));
            direction = argsConsumer.get("directory");
        } else {
            log.info("no directory provided. Using 004010");
        }

        List<String> messagetypes = List.of("850");
        if(argsConsumer.containsKey("messagetypes")){
            log.info("provided messagetypes: {}", argsConsumer.get("messagetypes"));
            String[] messagetypesSplitted = argsConsumer.get("messagetypes").toUpperCase().split(",");
            messagetypes = Arrays.asList(messagetypesSplitted);
        } else {
            log.info("no messagetype provided. Using 850");
        }
        return new X12Converter(direction, messagetypes);
    }
    
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<String> getMessagetypes() {
        return messagetypes;
    }

    public void setMessagetypes(List<String> messagetypes) {
        this.messagetypes = messagetypes;
    }

    public Map<String, String> getSeperators() {
        return seperators;
    }

    public void setSeperators(Map<String, String> seperators) {
        this.seperators = seperators;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }



    @Override
    public void setupAndRunEngine(String messageFilePath, boolean engine, boolean config, EngineExecuter engineExecuter) {
        
    }

    @Override
    public void setupEngine(boolean engine, boolean config, EngineExecuter engineExecuter) {

    }

    @Override
    public void runEngine(String messageFilePath, boolean engine, boolean config, EngineExecuter engineExecuter) {

    }
}
