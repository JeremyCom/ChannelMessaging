package com.example.commanje.channelmessaging2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelMessage extends Activity implements OnDownloadCompleteListener, AdapterView.OnItemClickListener{

    public static final String PREFS_NAME = "Stockage";
    private String channelID;
    private String accesstoken;
    private Messages messages;
    private ListView lVMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_message);
        //getIntent().getStringExtra()

        lVMessage = (ListView) findViewById(R.id.lVMessage);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        channelID = settings.getString("channelID", "");
        accesstoken = settings.getString("accesstoken", "");

        HashMap<String, String> Params = new HashMap<>();
        Params.put("accesstoken", accesstoken);
        Params.put("channelid", channelID);

        Downloader d = new Downloader(this, "http://www.raphaelbischof.fr/messaging/?function=getmessages" ,Params);
        d.setDownloaderList(this);
        d.execute();

    }

    @Override
    public void onDownloadCompleted(String result) {

        Gson gson = new Gson();
        messages = gson.fromJson(result, Messages.class);

        lVMessage.setAdapter(new MessageAdapter(getApplicationContext(), messages.messages));
        lVMessage.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
