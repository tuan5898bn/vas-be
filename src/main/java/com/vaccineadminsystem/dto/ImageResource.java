package com.vaccineadminsystem.dto;

public class ImageResource {

    private final String name;
    private final long size;
    private final String link;

    public ImageResource(String name, long size, String link) {
        this.name = name;
        this.size = size;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getLink() {
        return link;
    }
}
