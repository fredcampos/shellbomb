package com.shellshock;

public class FileHolder {

    public String name;
    public String type;
    public String size;
    public String downloadLink;
    public String link;
    public String fullPath;

    public FileHolder(String name, String type, String size, String fullPath) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.fullPath = fullPath;

    }

    public FileHolder() {

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
