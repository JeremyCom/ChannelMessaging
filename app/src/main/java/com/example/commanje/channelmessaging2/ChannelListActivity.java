package com.example.commanje.channelmessaging2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelListActivity extends Activity implements AdapterView.OnItemClickListener, OnDownloadCompleteListener {

    private ListView lVChannels;
    private String accesstoken;
    private Channels chs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        SharedPreferences settings = getSharedPreferences(AppConfig.PREFS_NAME, 0);
        accesstoken = settings.getString("accesstoken", "");

        HashMap<String, String> Params = new HashMap<String, String>();
        Params.put("accesstoken", accesstoken);

        Downloader d = new Downloader(this, "http://www.raphaelbischof.fr/messaging/?function=getchannels", Params);
        d.setDownloaderList(this);
        d.execute();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Channel myChannel = chs.channels.get(position);

        SharedPreferences settings = getSharedPreferences(AppConfig.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("channelID", String.valueOf(myChannel.channelID));
        editor.commit();

        Intent newActivity = new Intent(getApplicationContext(),ChannelMessage.class);
        //newActivity.pu
        startActivity(newActivity);

    }

    @Override
    public void onDownloadCompleted(String result) {

        Gson gson = new Gson();
        chs = gson.fromJson(result, Channels.class);


        lVChannels = (ListView) findViewById(R.id.lVChannels);
        lVChannels.setAdapter(new ChannelsAdapter(getApplicationContext(), chs.channels));
        lVChannels.setOnItemClickListener(this);

    }
}
