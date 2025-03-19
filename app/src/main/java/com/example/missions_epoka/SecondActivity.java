package com.example.missions_epoka;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private Button btnQuitter;
    private Button btnAjouterMission;
    private String salNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //récupération du numéro du salarié
        Intent intent = getIntent();
        salNo = intent.getStringExtra("no");
        Log.d("recupererNo", salNo);

        //définition de l'id des boutons
        btnQuitter = findViewById(R.id.btnEnvoyer);
        btnQuitter.setOnClickListener(this::onClickBtnQuitter);

        btnAjouterMission = findViewById(R.id.btnAjouterMission);
        btnAjouterMission.setOnClickListener(this::onClickBtnAjouterMission);
    }

    public void onClickBtnQuitter(View v){
        finish();
    }

    public void onClickBtnAjouterMission(View v){
        Intent intent = new Intent(SecondActivity.this, MissionActivity.class);
        intent.putExtra("no", salNo);
        startActivity(intent);
    }
}