API for Converting structure Data like Edifact

# Maven SDK
## 1. Add Dependency

Add the following dependency to your pom.xml. Currently, the library is available via our private Pilot Repository (access details provided upon application).
pom.xml

      <dependency>
      <groupId>com.edi-converter</groupId>
      <artifactId>converter</artifactId>
      <version>1.0.3</version>
      </dependency>

## 2. Basic Usage

The SDK is designed to be thread-safe and lightweight. You can initialize a single instance of the EdifactConverter and Converter and reuse it across your application.
EdiConversionService.java

      EdifactConverter edifactConverter = new EdifactConverter();
      edifactConverter.setDirectory("d01b");
      edifactConverter.setMessagetypes(Arrays.asList("ORDERS"));
      edifactConverter.setValidate(false);
      Converter converter = new Converter();
      converter.setConverterAbstract(edifactConverter);
      converter.setEngine(true);
      converter.setFileName("src/test/resources/testfiles/edifact/edifact-d01b-ORDERS.edi");
      converter.setupEngine();
      String result = converter.runEngine();

## 3. Configuration Options


| Method | Parameter                                                    | Description                                                       |
| ------------- |--------------------------------------------------------------|-------------------------------------------------------------------|
| setEngine(bool)  | true                                                         | True/Parser: Edifact->XML ; False/Unparser:XML->Edifact           |
| setFileName(String) | src/test/resources/testfiles/edifact/edifact-d01b-ORDERS.edi | Path to file to convert                                           |
| setValidate(bool) | true                                                         | true=Validation is On; false=Validation is Off                    |
| setDirectory(String) | d01b                                                         | Set mutliple Messagetypes which are part of the desired directory |
| setMessagetypes(Array[String]) | ORDERS                                                       | Set mutliple Messagetypes which are part of the desired directory |
|setEncoding(String)| Default: UTF-8                                               | Set the character encoding for the input EDI stream               |

   Method	Parameter	Description
   setFileName(String)	Default: none	Path to message which should be converted
   setEngine(bool)	Default: true	True/Parser: Edifact=>XML; False/Unparser: XML=>Edifact
   setDirectory(String)	Default: d01b	Set the UN/EDIFACT specific Directory of input.
   setMessagetypes(Array[String])	Default: ORDERS	Set mutliple Messagetypes which are part of the desired directory.
   setValidate(bool)	Default: true	Enables/Disables strict EDI schema validation during conversion.
   setEncoding(String)	Default: UTF-8	Set the character encoding for the input EDI stream.

# Java Standalone
## 1. Prerequisites

To run the Standalone tool, you only need a Java Runtime Environment (JRE) installed on your system.

    Java Version: JRE 17 or higher.
    Platform: Windows, macOS, or Linux.

## 2. Installation

We provide a Java standalone .jar to test all edge cases and formats (Orders, Invoices, Despatch Advices).

[Download Java Standalone (.jar) ](https://docs.edi-converter.com/standalone/converter-1.0.3.jar)
## 3. Command Line Usage

The basic syntax follows a simple input & output pattern.
Terminal / Command Prompt

      Simple conversion 
      java -jar converter-1.0.3.jar --type=edifact --engine=parser --directory=d01b --messagetypes=ORDERS --validate=false --filename=src/test/resources/testfiles/edifact/edifact-d01b-ORDERS.edi

      Configure a hotfolder
      java -jar converter-1.0.3.jar --type=edifact --engine=parser --directory=d01b --messagetypes=ORDERS --validate=false --hotfolder=hotfolder


## 4. CLI Options & Flags

| Flag | Description                                                       | Example  |
| ------------- |-------------------------------------------------------------------|----------|
| --type  | Type of Format like Edifact                                       |--type=edifact|
| --engine  | Parser: Edifact->XML ; Unparser:XML->Edifact                      |--engine=parser|
| --filename | Path to file to convert                                           |--filename=src/test/resources/testfiles/edifact/edifact-d01b-ORDERS.edi|
| --validate | true=Validation is On; false=Validation is Off                    |--validate=false|
| --directory | Set the UN/EDIFACT specific Directory of input                    |--directory=d01b|
| --messagetypes | Set mutliple Messagetypes which are part of the desired directory |--messagetypes=ORDERS|
| --hotfolder | Set a directory (from UserDir) to use hotfolder                   |--hotfolder=hotfolder|

   Privacy Note: The Standalone tool is completely self-contained. It does not phone home or require an active internet connection to function. 