package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("SuggestionItem")

// Class for holding a chat item in parse
public class SuggestionItem extends ParseObject{

    //default constructor
    public  SuggestionItem(){
    }

    public SuggestionItem(String name, String email, String suggestion) {
        super();
        setName(name);
        setEmail(email);
        setSuggestion(suggestion);
    }

    public void setName(String name){ put("name", name);}

    public void setEmail(String email){
        put("email", email);
    }

    public void setSuggestion(String suggestion){
        put("suggestion", suggestion);
    }

    public String getName() {
        return getString("name");
    }

    public String getEmail() {
        return getString("email");
    }

    public String getSuggestion() {
        return getString("suggestion");
    }

}