package com.ver_techs.qiff_android;

import com.parse.ParseObject;
import com.parse.ParseClassName;

/**
 * Created by Sony on 8/28/2015.
 */

public class ChatItem extends ParseObject{

    String userName;
    String chatMessage;

    //deafult constructor
    public  ChatItem(){
    }

    public ChatItem(String username, String chatmessage) {

        super();
        userName=username;
        chatMessage=chatmessage;

    }

    public void setUserName(String username){

        userName=username;

    }

    public void setChatMessage(String chatmessage){

        chatMessage=chatmessage;

    }

    public String getUserName() {

        return userName;

    }

    public String getChatMessage() {

        return chatMessage;

    }

}