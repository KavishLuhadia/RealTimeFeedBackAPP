package com.kavish.model.request;


public class Document {
    
    public String language;
    public String id;
    public String text;
    
    public String getLanguage() {
        return language;
    }
    public Document(String language, String id, String text) {
        this.language = language;
        this.id = id;
        this.text = text;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
}
