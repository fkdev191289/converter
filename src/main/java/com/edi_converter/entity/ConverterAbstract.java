package com.edi_converter.entity;

import com.edi_converter.worker.EngineExecuter;
import org.apache.daffodil.japi.Diagnostic;

import java.util.List;

public interface ConverterAbstract {
    void setupAndRunEngine(String messageFilePath, boolean engine, boolean config, EngineExecuter engineExecuter);
    void setupEngine(boolean engine, boolean config, EngineExecuter engineExecuter);
    String runEngine(String messageFilePath, boolean engine, boolean config, EngineExecuter engineExecuter);
    //List<String> executeValidate(byte[] message, String encoding, boolean type, boolean config, EngineExecuter engineExecuter);
    //String runParser(byte[] message, String encoding, boolean type, boolean config, EngineExecuter engineExecuter);
    //String runUnparser(byte[] message, String encoding, boolean type, boolean config, EngineExecuter engineExecuter);
}
