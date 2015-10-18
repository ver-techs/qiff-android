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

import com.ver_techs.qiff_android.R;

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

//        send.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                boolean ok = true;
//                name = name_suggestion.getText().toString();
//                suggestion = complaint_suggestion.getText().toString();
//                email = email_suggestion.getText().toString();
//
//                if(suggestion.length() == 0){
//                    Toast.makeText(Suggestion.this, "Complaint Field cannot be left blank !", Toast.LENGTH_SHORT).show();
//                    ok = false;
//                }
//                else if(suggestion.length() != 0 && !(suggestion.charAt(0)=='M' || suggestion.charAt(0)=='B' || suggestion.charAt(0)=='P')){
//                    ok = false;
//                    Toast.makeText(Suggestion.this, "Invalid RollNo !", Toast.LENGTH_SHORT).show();
//                }
//                else if(suggestion.length() != 9){
//                    ok = false;
//                    Toast.makeText(Suggestion.this, "Invalid RollNo !", Toast.LENGTH_SHORT).show();
//                }
//
//                if(ok == true){
//                    try{
//                        new Task().execute();
//                    }
//                    catch(Exception ex){
//                        ex.printStackTrace();
//                        Toast.makeText(Suggestion.this, "URL Exception !", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else{
//                    Toast.makeText(Suggestion.this, "Check Network Connection !", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    class Task extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {

            String postReceiverUrl = "http://aakifah.co.nf/recieve.php";

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(postReceiverUrl);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("suggestion", suggestion));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            }
            catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = null;
                try {
                    responseStr = EntityUtils.toString(resEntity).trim();
                }
                catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Log.v("aaki", "Response: " +  responseStr);

            }

            return null;

        }

        protected void onPostExecute(Void result) {

            // TODO: do something with the feed
            Toast.makeText(Suggestion.this, "Sent successfully !", Toast.LENGTH_SHORT).show();
        }
    }

}