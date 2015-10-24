package com.ver_techs.qiff_android.object_classes;

//Class to store local fixture items
public class ChatItemLocal{

    String userName, chatMessage, chatTime, orientation;

    //default constructor
    public ChatItemLocal(){
    }

    public ChatItemLocal(String username, String chatmessage, String chatTime, String orientation) {
        super();
        setUserName(username);
        setChatMessage(chatmessage);
        setChatTime(chatTime);
        setOrientation(orientation);
    }

    public void setUserName(String username){
        userName = username;
    }

    public void setChatMessage(String chatmessage){
        chatMessage = chatmessage;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public void setOrientation(String orientation){
        this.orientation = orientation;
    }

    public String getUserName() {
        return userName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getChatTime() {
        return chatTime;
    }

    public String getOrientation(){
        return orientation;
    }

}