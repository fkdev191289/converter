package com.edi_converter;

import com.edi_converter.entity.*;
import com.edi_converter.watchservice.WatchServiceCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        
        log.info("Start");
        log.info("Usage: java -jar EdiConverter.jar --key=<value>");
        log.info("Pass your args with Leading --");
        log.info("Possible keys: engine, type, filename, messageencoding, validate, directory, messagetypes, schemafilepath, schemaencoding, xsltfilepath, xmlencoding, fields, record, root, param, hotfolder");

            
            // handle is Config?
            Map<String, String> argsConsumer = new HashMap<>();
            for (int i = 0; i < args.length; ++i) {
                
                
                log.info("Passed arg["+i+"] "+ args[i]);
                if(args[i].charAt(0) == '-' && args[i].charAt(1) == '-'){
                    StringBuilder sb = new StringBuilder(args[i]);
                    while (sb.length() > 0 && sb.charAt(0) == '-') {
                        sb.deleteCharAt(0);
                    }
                    String withoutLeadingDash = sb.toString();
                    String arg1 = withoutLeadingDash.toLowerCase().split("=")[0];
                    String arg2 = null;
                    if(
                            withoutLeadingDash.split("=")[0].equals("param")
                                    || withoutLeadingDash.split("=")[0].equals("root")
                                    || withoutLeadingDash.split("=")[0].equals("record")
                                    || withoutLeadingDash.split("=")[0].equals("fields")
                                    || withoutLeadingDash.split("=")[0].equals("filename")
                                    || withoutLeadingDash.split("=")[0].equals("hotfolder")
                    ){
                        arg2 = withoutLeadingDash.split("=")[1];
                    } else {
                        arg2 = withoutLeadingDash.toLowerCase().split("=")[1];
                    }
                    argsConsumer.put(arg1, arg2);
                }
            }

        
                
                Converter converter = new Converter();
        
        if(argsConsumer.containsKey("filename")){
            log.info("Provide your filename in User directory");
            File file = new File(argsConsumer.get("filename"));
            if(file.exists()){
            } else {
                log.info("File does not exist");
            }
        } else {
            log.info("Provide your Filename in User directory");
        }

                
            boolean engine = true;
            if(argsConsumer.get("engine") != null && argsConsumer.get("engine").equals("parser")){
                engine = true;
                log.info("Choosed parser engine");
            } else if (argsConsumer.get("engine") != null && argsConsumer.get("engine").equals("unparser")) {
                engine = false;
                log.info("Choosed unparser engine");
            } else if (argsConsumer.get("engine") != null && argsConsumer.get("engine").equals("convert")) {
                // standard is xml
                // engine = true;
                log.info("Choosed convert engine");
            } else {
                log.info("No engine choosed. Using default=parser");
            }
        boolean validate = true;
        if(argsConsumer.get("validate") != null && argsConsumer.get("validate").equals("false")){
            validate = false;
            log.info("Validation is Off");
        } else {
            log.info("Validation is On");
        }
        
            if(!argsConsumer.containsKey("type")){
                log.info("arg type is missing");
            } else {
                switch(argsConsumer.get("type")) {
                    case "edifact":
                        EdifactConverter edifactConverter = new EdifactConverter();
                        String directory = argsConsumer.get("directory");
                        edifactConverter.setDirectory(directory);
                        List<String> messagetypes = Arrays.asList(argsConsumer.get("messagetypes").toUpperCase().split(","));
                        edifactConverter.setMessagetypes(messagetypes);
                        edifactConverter.setValidate(validate);
                        converter.setConverterAbstract(edifactConverter);
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupEngine();
                        break;
                    case "x12":
                        log.info("type x12/ansi acutally not supported");
                        X12Converter x12Converter = new X12Converter();
                        x12Converter.createX12Converter(argsConsumer);
                        x12Converter.setValidate(validate);
                        converter.setConverterAbstract(x12Converter);
                        converter.setEngine(engine);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setupAndRunEngine();
                        break;
                    case "custom":
                        CustomConverter customConverter = new CustomConverter();
                        //MultipartFile mf1 = getMultipartFilefromPath(argsConsumer.get("schemafilepath"));
                        //customConverter.setFile(mf1);
                        customConverter.setSchemaEncoding(argsConsumer.get("schemaencoding"));
                        customConverter.setSchemadesign(argsConsumer.get("schemadesign"));
                        converter.setConverterAbstract(customConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "flatfile":
                        FlatfileConverter flatfileConverter = new FlatfileConverter();
                        //MultipartFile mf2 = getMultipartFilefromPath(argsConsumer.get("schemafilepath"));
                        //flatfileConverter.setFile(mf2);
                        flatfileConverter.setSchemaEncoding(argsConsumer.get("schemaencoding"));
                        flatfileConverter.setSchemadesign(argsConsumer.get("schemadesign"));
                        converter.setConverterAbstract(flatfileConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "fixlength":
                        FixlengthConverter fixlengthConverter = new FixlengthConverter();
                        fixlengthConverter.setFields(argsConsumer.get("fields"));
                        fixlengthConverter.setRecord(argsConsumer.get("record"));
                        fixlengthConverter.setRoot(argsConsumer.get("root"));
                        converter.setConverterAbstract(fixlengthConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "csv":
                        CsvConverter csvConverter = new CsvConverter();
                        csvConverter.setFields(argsConsumer.get("fields"));
                        csvConverter.setRecord(argsConsumer.get("record"));
                        csvConverter.setRoot(argsConsumer.get("root"));
                        converter.setConverterAbstract(csvConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "json":
                        JsonConverter jsonConverter = new JsonConverter();
                        converter.setConverterAbstract(jsonConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "yaml":
                        YamlConverter yamlConverter = new YamlConverter();
                        converter.setConverterAbstract(yamlConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "xml":
                        XmlConverter xmlConverter = new XmlConverter();
                        //MultipartFile mf3 = getMultipartFilefromPath(argsConsumer.get("xsltfilepath"));
                        //xmlConverter.setFile(mf3);
                        if(argsConsumer.get("param") != null){
                            xmlConverter.setParam(Arrays.asList(argsConsumer.get("param")));
                        }
                        xmlConverter.setXmlEncoding(argsConsumer.get("xmlencoding"));
                        converter.setConverterAbstract(xmlConverter);
                        converter.setFileName(argsConsumer.get("filename"));
                        converter.setEngine(engine);
                        converter.setEncoding(argsConsumer.get("messageencoding"));
                        converter.setupAndRunEngine();
                        break;
                    case "empty":
                        break;
                    default:
                        log.info("Unknown type");
                }
            }
        if(!argsConsumer.containsKey("hotfolder")){
            converter.setFileName(argsConsumer.get("filename"));
            converter.runEngine();
            } else {
                log.info("Watch Service is activated. Please save/upload you message in Directory: " +argsConsumer.get("hotfolder"));
                new WatchServiceCall(converter, argsConsumer.get("hotfolder"));
            }

            log.info("Finish");
        }
    
/*
    private static MultipartFile getMultipartFilefromPath(String targetPath) {
        Path path = Paths.get(targetPath);
        String name = "mf-file.txt";
        String originalFileName = "mf-file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile mf = new MockMultipartFile(name,
                originalFileName, contentType, content);
        return mf;
    }
    
 */
    
}