package com.edi_converter.entity;

import com.edi_converter.worker.EngineExecuter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomConverter implements ConverterAbstract {
    private String filePath;
    private String schemadesign;
    private Map<String,String> seperators = new HashMap<>();
    private String schemaEncoding;

    public CustomConverter(String filePath) {
        this.filePath = filePath;
    }

    public CustomConverter() {
    }

    public String getSchemaEncoding() {
        return schemaEncoding;
    }

    public void setSchemaEncoding(String schemaEncoding) {
        this.schemaEncoding = schemaEncoding;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSchemadesign() {
        return schemadesign;
    }

    public void setSchemadesign(String schemadesign) {
        this.schemadesign = schemadesign;
    }

    public Map<String, String> getSeperators() {
        return seperators;
    }

    public void setSeperators(Map<String, String> seperators) {
        this.seperators = seperators;
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
