package com.edi_converter.entity;


import com.edi_converter.worker.EngineExecuter;

import java.util.List;

public class YamlConverter implements ConverterAbstract {
    public YamlConverter() {
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
