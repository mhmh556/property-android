package com.moataz.salah.propertymanagement.model.printer;

public class PrinterModel {
    String name ;
    String type ;
    String path ;
    String page_size;

    public PrinterModel(String name, String type , String path , String page_size) {
        this.name = name;
        this.type = type;
        this.path = path;
        this.page_size = page_size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }
}
