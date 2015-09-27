package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("LiveCommentaryItem")

// Class for holding a chat item in parse
public class LiveCommentaryItem extends ParseObject {

    //default constructor
    public LiveCommentaryItem(){
    }

    public LiveCommentaryItem(String minute, String commentary) {
        super();
        setMinute(minute);
        setCommentary(commentary);
    }

    public void setMinute(String minute){
        put("minute", minute);
    }

    public void setCommentary(String commentary){
        put("commentary",commentary);
    }

    public String getMinute() {
        return getString("minute");
    }

    public String getCommentary() {
        return getString("commentary");
    }

}