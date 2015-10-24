package com.ver_techs.qiff_android.custom_adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.ChatItemLocal;
import com.ver_techs.qiff_android.object_classes.FixtureItemLocal;

import java.util.ArrayList;
import java.util.List;

public class ChatCustomAdapter extends BaseAdapter {

    private TextView nameText, chatText;
    ArrayList<ChatItemLocal> chatItemArrayList; //Local variable list that stores all fixture items
    private Context context;

    public ChatCustomAdapter(Context context, ArrayList<ChatItemLocal> chatItemArray) {
        this.context = context;
        this.chatItemArrayList = chatItemArray;
        //Log.i("aaki",Integer.toString(fixtureItemArrayList.size()));
    }

    @Override
    public int getCount() {
        return chatItemArrayList.size();
    }

    @Override
    public ChatItemLocal getItem(int index) {
        return chatItemArrayList.get(index);
    }

    @Override
    public long getItemId(int i) {
        return i; // index number
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ChatItemLocal chatMessageObj = getItem(position);
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.getOrientation().equals("right")) {
            row = inflater.inflate(R.layout.layout_chat_row_right, parent, false);
        }else{
            row = inflater.inflate(R.layout.layout_chat_row_left, parent, false);
        }

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_path));

        nameText = (TextView) row.findViewById(R.id.name);
        nameText.setText(chatMessageObj.getUserName());
        nameText.setTypeface(custom_font);

        chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.getChatMessage());
        chatText.setTypeface(custom_font);

        return row;
    }
}