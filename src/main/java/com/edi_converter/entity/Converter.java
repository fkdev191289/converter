package com.edi_converter.entity;

import com.edi_converter.Main;
import com.edi_converter.worker.EngineExecuter;
import org.apache.commons.io.FileUtils;
import org.apache.daffodil.japi.Diagnostic;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Converter {
   
    private ConverterAbstract converterAbstract;
    private EngineExecuter engineExecuter;
    private String fileName;
    private String encoding;
    
    private boolean engine = true;
    
    
    private boolean config = false;

    public Converter() {
        this.engineExecuter = new EngineExecuter();
    }

    public ConverterAbstract getConverterAbstract() {
        return converterAbstract;
    }

    public void setConverterAbstract(ConverterAbstract converterAbstract) {
        this.converterAbstract = converterAbstract;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isConfig() {
        return config;
    }

    public void setConfig(boolean config) {
        this.config = config;
    }

    public boolean isEngine() {
        return engine;
    }

    public void setEngine(boolean engine) {
        this.engine = engine;
    }

    public void setupAndRunEngine() {
            converterAbstract.setupAndRunEngine(fileName, engine, config, engineExecuter);
    }

    public void setupEngine() {
        converterAbstract.setupEngine(engine, config, engineExecuter);
    }

    public void runEngine() {
        converterAbstract.runEngine(fileName, engine, config, engineExecuter);
    }
}
