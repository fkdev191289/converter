package com.edi_converter.entity;


import com.edi_converter.worker.EngineExecuter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvConverter implements ConverterAbstract {
    private String root;
    private String record;
    private String fields;
    private Map<String,String> seperators = new HashMap<>();
    private String skiplines;

    public CsvConverter() {
    }

    public CsvConverter(String fields) {
        this.fields = fields;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getSkiplines() {
        return skiplines;
    }

    public void setSkiplines(String skiplines) {
        this.skiplines = skiplines;
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
    public String runEngine(String messageFilePath, boolean engine, boolean config, EngineExecuter engineExecuter) {
        return null;
    }
}
