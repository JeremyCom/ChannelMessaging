package com.example.commanje.channelmessaging2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private Button btnValider;
    private EditText editTextID;
    private EditText editTextMDP;
    private TextView textViewID;
    private TextView textViewMDP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener(this);
        editTextID = (EditText) findViewById(R.id.editTextID);
        textViewID = (TextView) findViewById(R.id.textViewID);
        editTextMDP = (EditText) findViewById(R.id.editTextMDP);
        textViewMDP = (TextView) findViewById(R.id.textViewMDP);
        /*btnValider.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick (View v){
                                              HashMap<String, String> params = new HashMap<String, String>();
                                              params.put("username", login.getText().toString());
                                              params.put("password", password.getText().toString());
                                              Connexion connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=connect");
                                              connexion.setParmetres(params);
                                              connexion.setOnNewsUpdateListener(LoginActivity.this);
                                              connexion.execute();
                                          }
                                      }

        );*/
    }


    @Override
    public void onClick(View v) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", editTextID.getText().toString());
        params.put("password", editTextMDP.getText().toString());
        Downloader d = new Downloader(this,"http://www.raphaelbischof.fr/messaging/?function=connect", params);
        d.setDownloaderList(this);
        d.execute();
    }

    @Override
    public void onDownloadCompleted(String result) {
        Gson gson = new Gson();
        Result r = gson.fromJson(result, Result.class);

        if(r.code == 200){

            Toast.makeText(this, "Vous êtes connecté !" ,Toast.LENGTH_SHORT).show();

            SharedPreferences settings = getSharedPreferences(AppConfig.PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accesstoken", r.accesstoken);
            editor.commit();

            Intent newActivity = new Intent(getApplicationContext(),ChannelListActivity.class);
            startActivity(newActivity);

        }
        else{

            Toast.makeText(this, "Identifiant ou mot de passe érroné !" ,Toast.LENGTH_SHORT).show();

        }
    }
}
