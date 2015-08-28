package com.ver_techs.qiff_android;

import com.parse.ParseObject;
import com.parse.ParseClassName;

/**
 * Created by Sony on 8/28/2015.
 */

@ParseClassName("ChatItem")
public class ChatItem extends ParseObject{

    String userName;
    String chatMessage;

    //deafult constructor
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