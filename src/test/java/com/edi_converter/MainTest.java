package com.edi_converter;

import com.edi_converter.entity.Converter;
import com.edi_converter.entity.EdifactConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MainTest {

    @DisplayName("Convert Edifact to XML:Edifact")
    @Test
    void convertEdifactToXML() {
        EdifactConverter edifactConverter = new EdifactConverter();
        Converter converter = new Converter();
        edifactConverter.setDirectory("d01b");
        edifactConverter.setMessagetypes(Arrays.asList("ORDERS"));
        edifactConverter.setValidate(false);
        converter.setConverterAbstract(edifactConverter);
        converter.setEngine(true);
        converter.setupEngine();
        converter.setFileName("src/test/resources/testfiles/edifact/edifact-d01b-ORDERS.edi");
        String result = converter.runEngine();
    }

    @DisplayName("Convert XML:Edifact to Edifact")
    @Test
    void convertXmlToEdifact() {
        EdifactConverter edifactConverter = new EdifactConverter();
        Converter converter = new Converter();
        edifactConverter.setDirectory("d01b");
        edifactConverter.setMessagetypes(Arrays.asList("ORDERS"));
        edifactConverter.setValidate(false);
        converter.setConverterAbstract(edifactConverter);
        converter.setEngine(false);
        converter.setupEngine();
        converter.setFileName("src/test/resources/testfiles/xml/edifact-d01b-ORDERS.xml");
        String result = converter.runEngine();
    }

    @DisplayName("Test Valid Edifact")
    @Test
    void validEdifact() {
        EdifactConverter edifactConverter = new EdifactConverter();
        Converter converter = new Converter();
        edifactConverter.setDirectory("d01b");
        edifactConverter.setMessagetypes(Arrays.asList("ORDERS"));
        edifactConverter.setValidate(true);
        converter.setConverterAbstract(edifactConverter);
        converter.setEngine(true);
        converter.setupEngine();
        converter.setFileName("src/test/resources/testfiles/edifact/edifact-d01b-ORDERS-valid.edi");
        String result = converter.runEngine();
    }

    @DisplayName("Test Invalid Edifact")
    @Test
    void invalidEdifact() {
        EdifactConverter edifactConverter = new EdifactConverter();
        Converter converter = new Converter();
        edifactConverter.setDirectory("d01b");
        edifactConverter.setMessagetypes(Arrays.asList("ORDERS"));
        edifactConverter.setValidate(true);
        converter.setConverterAbstract(edifactConverter);
        converter.setEngine(true);
        converter.setupEngine();
        converter.setFileName("src/test/resources/testfiles/edifact/edifact-d01b-ORDERS-invalid.edi");
        String result = converter.runEngine();
    }
}