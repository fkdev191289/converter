package com.edi_converter.util;





import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.JDOMSource;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class UtilSetup {
    
    public ByteArrayOutputStream getInputStreamFromDoc(Document doc){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Result target = new StreamResult(outputStream);
        Source source = new JDOMSource(doc);
        try {
            TransformerFactory.newInstance().newTransformer().transform(source, target);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    public String changeEdifactParserNS(String xmlString, String directory) {
        String output = xmlString;
        String directoyExtended = directory.toUpperCase();
        try {
            SAXBuilder sax = new SAXBuilder();
            org.jdom2.Document doc = new org.jdom2.Document();
            doc = sax.build(new StringReader(xmlString));
            org.jdom2.Element rootNode = doc.getRootElement();

            Namespace newDNs = Namespace.getNamespace(directoyExtended, "https://edi-converter.com/dfdl/edi/un/edifact/" + directoyExtended);
            rootNode.setNamespace(newDNs);

            Namespace oldDNs = Namespace.getNamespace(directoyExtended, "http://www.ibm.com/dfdl/edi/un/edifact/" + directoyExtended);
            for (org.jdom2.Element e : doc.getDescendants(Filters.element())) {
                if (e.getNamespace() == oldDNs) {
                    e.setNamespace(newDNs);
                }
            }


            Namespace srv = rootNode.getNamespace("srv");
            rootNode.removeNamespaceDeclaration(srv);
            Namespace xsi = rootNode.getNamespace("xsi");
            rootNode.removeNamespaceDeclaration(xsi);
            XMLOutputter xmOut = new XMLOutputter();
            output = xmOut.outputString(doc);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return output;
    }

    public String changeEdifactUnparserNS(String xmlString, String directory) {
        String output = xmlString;
        String directoyExtended = directory.toUpperCase();
        try {
            SAXBuilder sax = new SAXBuilder();
            org.jdom2.Document doc = new org.jdom2.Document();
            doc = sax.build(new StringReader(xmlString));
            org.jdom2.Element rootNode = doc.getRootElement();

            Namespace newDNs = Namespace.getNamespace(directoyExtended, "http://www.ibm.com/dfdl/edi/un/edifact/" + directoyExtended);
            rootNode.setNamespace(newDNs);

            Namespace oldDNs = Namespace.getNamespace(directoyExtended, "https://edi-converter.com/dfdl/edi/un/edifact/" + directoyExtended);
            for (org.jdom2.Element e : doc.getDescendants(Filters.element())) {
                if (e.getNamespace() == oldDNs) {
                    e.setNamespace(newDNs);
                }
            }

            Namespace srv = rootNode.getNamespace("srv");
            rootNode.removeNamespaceDeclaration(srv);
            Namespace xsi = rootNode.getNamespace("xsi");
            rootNode.removeNamespaceDeclaration(xsi);
            XMLOutputter xmOut = new XMLOutputter();
            output = xmOut.outputString(doc);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return output;
    }

    public String prettyPrintByTransformer(String xmlString, int indent, boolean ignoreDeclaration) {

        try {
            
            InputSource src = new InputSource(new StringReader(xmlString));
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            org.w3c.dom.Document document = documentBuilderFactory.newDocumentBuilder().parse(src);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ignoreDeclaration ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Writer out = new StringWriter();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
            transformer.transform(new DOMSource(document), new StreamResult(out));
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }
}
