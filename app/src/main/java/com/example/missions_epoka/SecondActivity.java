package com.example.missions_epoka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private Button btnQuitter;
    private Button btnAjouterMission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

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
        startActivity(intent);
    }
}