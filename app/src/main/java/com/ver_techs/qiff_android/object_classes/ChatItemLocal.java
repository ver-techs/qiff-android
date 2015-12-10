package com.ver_techs.qiff_android.object_classes;

import android.graphics.Bitmap;

//Class to store local fixture items
public class ChatItemLocal{

    String userName, chatMessage, chatTime, orientation, fullTime;
    Bitmap profilePicture;

    //default constructor
    public ChatItemLocal(){
    }

    public ChatItemLocal(String username, String chatmessage, String chatTime, String orientation, Bitmap profilePicture, String fullTime) {
        super();
        setUserName(username);
        setChatMessage(chatmessage);
        setChatTime(chatTime);
        setOrientation(orientation);
        setProfilePicture(profilePicture);
        setFullTime(fullTime);
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

    public void setProfilePicture(Bitmap profilePicture){
        this.profilePicture = profilePicture;
    }

    public void setFullTime(String fullTime){
        this.fullTime = fullTime;
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

    public Bitmap getProfilePicture(){
        return
                profilePicture;
    }

    public String getFullTime(){
        return fullTime;
    }

}