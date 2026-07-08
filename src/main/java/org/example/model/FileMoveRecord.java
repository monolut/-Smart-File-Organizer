package org.example.model;

public class FileMoveRecord {

    private String source;
    private String destination;

    public FileMoveRecord() {
    }

    public FileMoveRecord(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}