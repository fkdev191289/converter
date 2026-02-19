package com.edi_converter.setup;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.io.payload.StringResult;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SmooksSetup {
    
    private ExecutionContext executionContext;

    public Smooks setup(InputStream inputStream) {
        Smooks smooks = null;
        try {
            smooks = new Smooks();
            smooks.addResourceConfigs(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return smooks;
    }

    public Smooks setupAndCreateContext(InputStream inputStream) {
        Smooks smooks = null;
        try {
            smooks = new Smooks();
            smooks.addResourceConfigs(inputStream);
            this.executionContext = smooks.createExecutionContext();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return smooks;
    }

    public String convert(Smooks smooks, byte[] content) {
        StringResult stringResult = new StringResult();
        smooks.filterSource(this.executionContext, new StreamSource(new ByteArrayInputStream(content)), stringResult);
        smooks.close();
        return stringResult.getWriter().toString();
    }

    public String createAndConvert(Smooks smooks, byte[] content) {
        StringResult stringResult = new StringResult();
        ExecutionContext executionContext = smooks.createExecutionContext();
        smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(content)), stringResult);
        smooks.close();
        return stringResult.getWriter().toString();
    }
}
