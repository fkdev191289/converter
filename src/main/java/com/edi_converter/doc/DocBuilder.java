package com.edi_converter.doc;

import com.edi_converter.defaults.DefaultSeperators;
import com.edi_converter.entity.EdifactConverter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DocBuilder {
    private static final Logger log = LoggerFactory.getLogger(DocBuilder.class);
    private DefaultSeperators defaultSeperators;

    public DocBuilder() {
        defaultSeperators = new DefaultSeperators();
    }

    public Document buildDocForEdifact(String edifactDirectory, List<String> messageTypes, String validationMode, boolean typeOfParser) {
        defaultSeperators.initEdifactDefaultSeps();
        Namespace nsRoot = Namespace.getNamespace("https://www.smooks.org/xsd/smooks-2.0.xsd");
        Namespace nsCartridge = Namespace.getNamespace("edifact", "https://www.smooks.org/xsd/smooks/edifact-2.0.xsd");
        Namespace nsCore = Namespace.getNamespace("core", "https://www.smooks.org/xsd/smooks/smooks-core-1.6.xsd");
        Document document = new Document();
        
        Element rootElement = new Element("smooks-resource-list");
        rootElement.setNamespace(nsRoot);
        rootElement.addNamespaceDeclaration(nsCartridge);
        rootElement.addNamespaceDeclaration(nsCore);
        Element filterSettings = new Element("filterSettings", nsCore);
        Element standardmeetsParserorUnparser = null;
        String typeOfParserInitiator = "parser";

        String schemaUriDirectory = "/"+edifactDirectory+"/EDIFACT-Messages.dfdl.xsd";
        if(typeOfParser){
            typeOfParserInitiator = "parser";
            standardmeetsParserorUnparser = new Element(typeOfParserInitiator, nsCartridge);
            standardmeetsParserorUnparser.setAttribute("schemaUri", schemaUriDirectory);
            standardmeetsParserorUnparser.setAttribute("validationMode", validationMode);
            standardmeetsParserorUnparser.setAttribute("segmentTerminator", defaultSeperators.getSegmentseperator());
            standardmeetsParserorUnparser.setAttribute("dataElementSeparator", defaultSeperators.getFieldseperator());
            standardmeetsParserorUnparser.setAttribute("compositeDataElementSeparator", defaultSeperators.getCompositeelementseperator());
            standardmeetsParserorUnparser.setAttribute("decimalSign", defaultSeperators.getDecimalseperator());
            standardmeetsParserorUnparser.setAttribute("escapeCharacter", defaultSeperators.getEscapeseperator());
            standardmeetsParserorUnparser.setAttribute("repetitionSeparator", defaultSeperators.getRepeatseperator());
            standardmeetsParserorUnparser.setAttribute("triadSeparator", defaultSeperators.getTriadseperator());

            filterSettings.setAttribute("defaultSerialization", "true");

        }else{
            typeOfParserInitiator = "unparser";
            standardmeetsParserorUnparser = new Element(typeOfParserInitiator, nsCartridge);
            standardmeetsParserorUnparser.setAttribute("unparseOnNode", "*");
            standardmeetsParserorUnparser.setAttribute("schemaUri", schemaUriDirectory);
            standardmeetsParserorUnparser.setAttribute("validationMode", validationMode);
            standardmeetsParserorUnparser.setAttribute("segmentTerminator", defaultSeperators.getSegmentseperator());
            standardmeetsParserorUnparser.setAttribute("dataElementSeparator", defaultSeperators.getFieldseperator());
            standardmeetsParserorUnparser.setAttribute("compositeDataElementSeparator", defaultSeperators.getCompositeelementseperator());
            standardmeetsParserorUnparser.setAttribute("decimalSign", defaultSeperators.getDecimalseperator());
            standardmeetsParserorUnparser.setAttribute("escapeCharacter", defaultSeperators.getEscapeseperator());
            standardmeetsParserorUnparser.setAttribute("repetitionSeparator", defaultSeperators.getRepeatseperator());
            standardmeetsParserorUnparser.setAttribute("triadSeparator", defaultSeperators.getTriadseperator());

            filterSettings.setAttribute("defaultSerialization", "false");
        }


        Element messagetypesElement =  new Element("messageTypes", nsCartridge);

        for (String messageType : messageTypes) {
            Element messagetypeElement =  new Element("messageType", nsCartridge);
            messagetypeElement.setText(messageType);
            messagetypesElement.addContent(messagetypeElement);
        }

        rootElement.addContent(filterSettings);
        rootElement.addContent(standardmeetsParserorUnparser);
        document.setRootElement(rootElement);
        outputDocument(document);
        return document;
    }

    private Document outputDocument(Document document) {
        XMLOutputter xmOut = new XMLOutputter(Format.getPrettyFormat());
        log.info((xmOut.outputString(document)));
        return document;
    }


}
