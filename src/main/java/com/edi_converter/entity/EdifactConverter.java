package com.edi_converter.entity;

import com.edi_converter.worker.EngineExecuter;
import org.apache.commons.io.FileUtils;
import org.apache.daffodil.japi.Diagnostic;
import org.jdom2.transform.JDOMSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


public class EdifactConverter implements ConverterAbstract {

    private static final Logger log = LoggerFactory.getLogger(EdifactConverter.class);
    
    private String directory = "d01b";
    private List<String> messagetypes = Arrays.asList("ORDERS");
    private Map<String,String> seperators = new HashMap<>();
    private boolean validate = true;

    public EdifactConverter() {
    }
    
    public EdifactConverter(String directory, List<String> messagetypes) {
        this.directory = directory;
        this.messagetypes = messagetypes;
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
    public void setupAndRunEngine(String filename, boolean engine, boolean config, EngineExecuter engineExecuter) {
        String userDir = System.getProperty("user.dir");
        new File(userDir, "output").mkdir();
        StringBuilder outputPath = new StringBuilder(userDir + "/output/");
        outputPath
                .append("result")
                .append("-");
        List<String> result;
        if(validate){
            result = engineExecuter.setupAndValidate(filename, directory, messagetypes, Optional.of(validate), seperators, engine);

            if(result.size()>2){
                outputPath
                        .append("invalid")
                        .append("-");
            } else {
                outputPath
                        .append("valid")
                        .append("-");
            }
            outputPath
                    .append("parser")
                    .append("-")
                    .append(directory)
                    .append("-")
                    .append(messagetypes)
                    .append(".txt");
        } else {
            if(engine){
                result = engineExecuter.setupAndConvert(filename, directory, messagetypes, Optional.of(validate), seperators, engine);
                outputPath
                        .append("parser")
                        .append("-")
                        .append(directory)
                        .append("-")
                        .append(messagetypes)
                        .append(".xml");
            } else {
                result = engineExecuter.setupAndConvert(filename, directory, messagetypes, Optional.of(validate), seperators, engine);
                outputPath
                        .append("unparser")
                        .append("-")
                        .append(directory)
                        .append("-")
                        .append(messagetypes)
                        .append(".edi");

            }
        }
        log.info("Default Result Path: " + outputPath);
        StringBuilder stringBuilder = new StringBuilder();
        result.forEach(e -> stringBuilder.append(e).append("\n"));
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputPath.toString()));
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setupEngine(boolean engine, boolean config, EngineExecuter engineExecuter) {
        engineExecuter.setupEngine(directory, messagetypes, Optional.of(validate), engine);
    }

    @Override
    public void runEngine(String filename, boolean engine, boolean config, EngineExecuter engineExecuter) {
        List<String> result;
        String userDir = System.getProperty("user.dir");
        new File(userDir, "output").mkdir();
        StringBuilder outputPath = new StringBuilder(userDir + "/output/");
        outputPath
                .append("result")
                .append("-");
        if(validate){
            result = engineExecuter.runValidate(filename);
            if(result.size()>2){
                outputPath
                        .append("invalid")
                        .append("-");
            } else {
                outputPath
                        .append("valid")
                        .append("-");
            }
            outputPath
                    .append("edifact")
                    .append("-")
                    .append("parser")
                    .append("-")
                    .append(directory)
                    .append("-")
                    .append(messagetypes)
                    .append(".txt");
        } else {
            if(engine){
                result = engineExecuter.runConverter(filename, engine);
                outputPath
                        .append("edifact")
                        .append("-")
                        .append("parser")
                        .append("-")
                        .append(directory)
                        .append("-")
                        .append(messagetypes)
                        .append(".xml");
            } else {
                result = engineExecuter.runConverter(filename, engine);
                outputPath
                        .append("edifact")
                        .append("-")
                        .append("unparser")
                        .append("-")
                        .append(directory)
                        .append("-")
                        .append(messagetypes)
                        .append(".edi");

            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        result.forEach(e -> stringBuilder.append(e).append("\n"));
        log.info("RESULT");
        log.info(stringBuilder.toString());
        log.info("Default Result Path: " + outputPath);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputPath.toString()));
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
}
