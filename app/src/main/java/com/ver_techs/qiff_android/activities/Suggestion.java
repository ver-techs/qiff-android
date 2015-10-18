package com.ver_techs.qiff_android.activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.SaveCallback;
import com.ver_techs.qiff_android.R;
import com.ver_techs.qiff_android.object_classes.ChatItem;
import com.ver_techs.qiff_android.object_classes.SuggestionItem;

public class Suggestion extends Activity {

    EditText name_suggestion, complaint_suggestion, email_suggestion;
    String name="", suggestion ="", email ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Button send = (Button) findViewById(R.id.send_suggestion);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), getString(R.string.font_path));

        name_suggestion = (EditText) findViewById(R.id.name);
        complaint_suggestion = (EditText) findViewById(R.id.suggestion);
        email_suggestion = (EditText) findViewById(R.id.email);

        name_suggestion.setTypeface(custom_font);
        complaint_suggestion.setTypeface(custom_font);
        email_suggestion.setTypeface(custom_font);

        name_suggestion.getBackground().setColorFilter(getResources().getColor(R.color.color_tertiary), PorterDuff.Mode.SRC_ATOP);
        complaint_suggestion.getBackground().setColorFilter(getResources().getColor(R.color.color_tertiary), PorterDuff.Mode.SRC_ATOP);
        email_suggestion.getBackground().setColorFilter(getResources().getColor(R.color.color_tertiary), PorterDuff.Mode.SRC_ATOP);

        send.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

                boolean ok = true;
                name = name_suggestion.getText().toString();
                suggestion = complaint_suggestion.getText().toString();
                email = email_suggestion.getText().toString();

                if(suggestion.length() == 0){
                    Toast.makeText(Suggestion.this, "Complaint Field cannot be left blank !", Toast.LENGTH_SHORT).show();
                    ok = false;
                }
                else if(name.length() == 0){
                    ok = false;
                    Toast.makeText(Suggestion.this, "Name cannot be left blank !", Toast.LENGTH_SHORT).show();
                }

                if(ok == true){
                    try{

                        SuggestionItem suggestionItem = new SuggestionItem(name, email, suggestion); //create a new chatitem

                        // Save the data to Parse whenever internet is available
                        suggestionItem.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(com.parse.ParseException e) {

                                if (e == null) {
                                } else {
                                }
                            }
                        });

                        name_suggestion.setText(""); //clear the message box
                        email_suggestion.setText(""); //clear the message box
                        complaint_suggestion.setText(""); //clear the message box

                        Intent i = new Intent(Suggestion.this, MainActivity.class);
                        startActivity(i);

                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

}