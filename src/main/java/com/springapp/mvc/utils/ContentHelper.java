package com.springapp.mvc.utils;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

public class ContentHelper {

    public static final int MAX_CONTENT_LENGTH_IN_SUBSTRING = 140;

    public String getContent() {
        return content.length() > MAX_CONTENT_LENGTH_IN_SUBSTRING
                ? String.format("%s...",content.substring(0,MAX_CONTENT_LENGTH_IN_SUBSTRING))
                : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;
}
