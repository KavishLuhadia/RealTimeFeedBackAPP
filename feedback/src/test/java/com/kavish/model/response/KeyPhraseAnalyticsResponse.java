package com.kavish.model.response;

import java.util.ArrayList;

public class KeyPhraseAnalyticsResponse {
        public ArrayList<Document> documents;
        public ArrayList<Document> getDocuments() {
            return documents;
        }
        public void setDocuments(ArrayList<Document> documents) {
            this.documents = documents;
        }
        public ArrayList<Object> getErrors() {
            return errors;
        }
        public void setErrors(ArrayList<Object> errors) {
            this.errors = errors;
        }
        public String getModelVersion() {
            return modelVersion;
        }
        public void setModelVersion(String modelVersion) {
            this.modelVersion = modelVersion;
        }
        public ArrayList<Object> errors;
        public String modelVersion;
    }

