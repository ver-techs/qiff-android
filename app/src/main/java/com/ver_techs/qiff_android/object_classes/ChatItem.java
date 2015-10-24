package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseObject;
import com.parse.ParseClassName;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("ChatItem")

// Class for holding a chat item in parse
public class ChatItem extends ParseObject{

    //default constructor
    public  ChatItem(){
    }

    public ChatItem(String username, String chatmessage) {
        super();
        setUserName(username);
        setChatMessage(chatmessage);
    }

    public void setUserName(String username){
        put("userName", username);
    }

    public void setChatMessage(String chatmessage){
        put("chatMessage",chatmessage);
    }

    public String getUserName() {
        return getString("userName");
    }

    public String getChatMessage() {
        return getString("chatMessage");
    }

}