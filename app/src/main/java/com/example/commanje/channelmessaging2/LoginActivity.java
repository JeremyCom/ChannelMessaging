package com.example.commanje.channelmessaging2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private Button btnValider;
    private EditText txtId;
    private EditText txtPwd;
    private View myview;

    public static final String PREFS_NAME = "Stockage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener((View.OnClickListener) this) ;
        txtId = (EditText) findViewById(R.id.editTextID);
        txtPwd = (EditText) findViewById(R.id.editTextMDP);
        myview = (View) findViewById(R.id.vue);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnValider) {

            HashMap<String, String> Params = new HashMap<String, String>();
            Params.put("username", txtId.getText().toString());
            Params.put("password", txtPwd.getText().toString());
            save("username", txtId.getText().toString());
            Downloader d = new Downloader(this, "http://www.raphaelbischof.fr/messaging/?function=connect" ,Params);
            d.setDownloaderList(this);
            d.execute();

        }

    }

    @Override
    public void onDownloadCompleted(String result, int type) {

        Gson gson = new Gson();

        Result r = gson.fromJson(result, Result.class);

        if(r.code == 200){

            Toast.makeText(this, "Vous etes connecté !" ,Toast.LENGTH_SHORT).show();

            save("accesstoken", r.accesstoken);

            Intent loginIntent = new Intent(LoginActivity.this, ChannelListActivity.class);
            startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, myview, "logo").toBundle());

        }
        else{

            Toast.makeText(this, "Identifiant ou mot de passe érroné !" ,Toast.LENGTH_SHORT).show();

        }

    }

    public void save(String key, String value){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
}