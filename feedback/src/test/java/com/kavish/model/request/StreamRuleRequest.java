package com.kavish.model.request;

import java.util.ArrayList;
import java.util.List;

public class StreamRuleRequest {
   
        public List<StreamRule> addRule = new ArrayList<>();

        public List<StreamRule> getAddRule() {
            return addRule;
        }

        public void setAdd(List<StreamRule> add) {
            this.addRule = add;
        }

        public void add(String value, String tag){
            getAddRule().add(new StreamRule(value,tag));

        }
    }
    
    

