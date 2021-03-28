package com.briancollison.sbdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Joke {
    private String id;
    @JsonProperty("icon_url")
    private String iconUrl;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
