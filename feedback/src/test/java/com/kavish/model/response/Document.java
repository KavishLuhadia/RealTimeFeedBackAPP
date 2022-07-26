package com.kavish.model.response;

import java.util.ArrayList;

public class Document {
    
    public String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public ArrayList<String> getKeyPhrases() {
        return keyPhrases;
    }
    public void setKeyPhrases(ArrayList<String> keyPhrases) {
        this.keyPhrases = keyPhrases;
    }
    public ArrayList<Object> getWarnings() {
        return warnings;
    }
    public void setWarnings(ArrayList<Object> warnings) {
        this.warnings = warnings;
    }
    public ArrayList<String> keyPhrases;
    public ArrayList<Object> warnings;

}
