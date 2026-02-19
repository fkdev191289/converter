package com.edi_converter.defaults;

import java.util.Map;

public class DefaultSeperators {

    private String segmentseperator;
    private String fieldseperator;
    private String compositeelementseperator;
    private String decimalseperator;
    private String repeatseperator;
    private String escapeseperator;
    private String triadseperator;

    public void initEdifactDefaultSeps(){
        this.segmentseperator = "'%NL;%WSP*; '%WSP*;";
        this.fieldseperator = "+";
        this.compositeelementseperator = ":";
        this.decimalseperator = ".";
        this.repeatseperator = "*";
        this.escapeseperator = "?";
        this.triadseperator = ",";
    }

    public String getSegmentseperator() {
        return segmentseperator;
    }

    public void setSegmentseperator(String segmentseperator) {
        this.segmentseperator = segmentseperator;
    }

    public String getFieldseperator() {
        return fieldseperator;
    }

    public void setFieldseperator(String fieldseperator) {
        this.fieldseperator = fieldseperator;
    }

    public String getCompositeelementseperator() {
        return compositeelementseperator;
    }

    public void setCompositeelementseperator(String compositeelementseperator) {
        this.compositeelementseperator = compositeelementseperator;
    }

    public String getDecimalseperator() {
        return decimalseperator;
    }

    public void setDecimalseperator(String decimalseperator) {
        this.decimalseperator = decimalseperator;
    }

    public String getRepeatseperator() {
        return repeatseperator;
    }

    public void setRepeatseperator(String repeatseperator) {
        this.repeatseperator = repeatseperator;
    }

    public String getEscapeseperator() {
        return escapeseperator;
    }

    public void setEscapeseperator(String escapeseperator) {
        this.escapeseperator = escapeseperator;
    }

    public String getTriadseperator() {
        return triadseperator;
    }

    public void setTriadseperator(String triadseperator) {
        this.triadseperator = triadseperator;
    }
}
