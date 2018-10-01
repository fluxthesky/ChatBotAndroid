package com.example.joseph.chatbot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    LinearLayout chatBoxLL;

    LayoutInflater layoutInflater;

    Button send_button;
    EditText messageBoxET;
    String urlTing = "http://18.217.157.32:5000/";
     String header_request = "user_question";
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatBoxLL = (LinearLayout) findViewById(R.id.chat_box_ll);
        messageBoxET = (EditText) findViewById(R.id.message_box_et);

        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        send_button = (Button) findViewById(R.id.button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = messageBoxET.getText().toString();

                if (messageBoxET.getText().toString().trim().length() != 0) {
                    String t = messageBoxET.getText().toString();
                    addSender( t);
                    POST(t , header_request);
                }

                messageBoxET.setText("");

            }
        });


        gson = new Gson();






    }

    public void POST(final String text , final String header_params){



        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlTing,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {


                            JSONObject payload = new JSONObject(response);
                            String payloadContent = payload.get("payload").toString();
                            Log.i("ChatBot" , " this is " +  payloadContent);
                            addReplier(payloadContent);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(header_params, text);

                return params;
            }
        };

        queue.add(postRequest);
    }

    public void addSender(String text){

       View sender_text = layoutInflater.inflate(R.layout.sender_text_box,null);
       TextView tv = sender_text.findViewById(R.id.sender_text_box_textView);
       tv.setText(text);
       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);


       lp.gravity = Gravity.RIGHT;
       lp.bottomMargin =  7;
       lp.topMargin = 7;
       lp.rightMargin = 7;


       chatBoxLL.addView(sender_text);


        sender_text.setLayoutParams(lp);


    }


    public void addReplier(String text){

        View sender_text = layoutInflater.inflate(R.layout.replier_text_box,null);
        TextView tv = sender_text.findViewById(R.id.replier_text_box_textView);
        tv.setText(text);







        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);


        lp.weight = 1;
        lp.gravity = Gravity.LEFT;
        lp.bottomMargin =  7;
        lp.topMargin = 7;
        lp.leftMargin = 7;


        chatBoxLL.addView(sender_text);


        sender_text.setLayoutParams(lp);


    }





}
