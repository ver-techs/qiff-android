package com.ver_techs.qiff_android;

import com.parse.ParseObject;
import com.parse.ParseClassName;

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
        put("userName",username);
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