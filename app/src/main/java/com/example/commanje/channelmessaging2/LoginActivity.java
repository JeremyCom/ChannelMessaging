package com.example.commanje.channelmessaging2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

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
        btnValider.setOnClickListener((View.OnClickListener) this);
        editTextID = (EditText) findViewById(R.id.editTextID);
        textViewID = (TextView) findViewById(R.id.textViewID);
        editTextMDP = (EditText) findViewById(R.id.editTextMDP);
        textViewMDP = (TextView) findViewById(R.id.textViewMDP);
    }

    btnValider.setOnClickListener(new View.OnClickListener()

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

    );

    public void connect() {
        Downloader d = new Downloader(this, Params);

    }
}
