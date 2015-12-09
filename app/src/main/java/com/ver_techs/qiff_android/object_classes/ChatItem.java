package com.ver_techs.qiff_android.object_classes;

import com.parse.ParseFile;
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

    public ChatItem(String username, String chatmessage, ParseFile profilePicture) {
        super();
        setUserName(username);
        setChatMessage(chatmessage);
        setProfilePicture(profilePicture);
    }

    public void setUserName(String username){
        put("userName", username);
    }

    public void setChatMessage(String chatmessage){
        put("chatMessage",chatmessage);
    }

    public void setProfilePicture(ParseFile profilePicture){
        put("profilePic", profilePicture);
    }

    public String getUserName() {
        return getString("userName");
    }

    public String getChatMessage() {
        return getString("chatMessage");
    }

}