package com.kavish.model.request;
import java.util.ArrayList;

public class KeyPhraseAnalyticsRequest {
    
        public ArrayList<Document> documents= new ArrayList<>();

        public ArrayList<Document> getDocuments() {
            return documents;
        }

        public void setDocuments(ArrayList<Document> documents) {
            this.documents = documents;
        }
    
    
    
}

