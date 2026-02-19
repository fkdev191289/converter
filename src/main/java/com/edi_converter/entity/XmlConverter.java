package com.edi_converter.entity;



import com.edi_converter.worker.EngineExecuter;

import java.util.List;

public class XmlConverter implements ConverterAbstract {
    private String filePath;
    private List<String> param;
    private String xmlEncoding;

    public XmlConverter() {
    }

    public XmlConverter(String filePath) {
        this.filePath = filePath;
    }

    public String getXmlEncoding() {
        return xmlEncoding;
    }

    public void setXmlEncoding(String xmlEncoding) {
        this.xmlEncoding = xmlEncoding;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getParam() {
        return param;
    }

    public void setParam(List<String> param) {
        this.param = param;
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
