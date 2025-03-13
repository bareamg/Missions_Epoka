package com.example.missions_epoka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText txfNo, txfMdp;
    private Button btnSeConnecter;
    private final String URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txfNo = findViewById(R.id.txfNo);
        txfMdp = findViewById(R.id.txfMdp);

        btnSeConnecter = findViewById(R.id.btnSeConnecter);
        btnSeConnecter.setOnClickListener(this::OnClickBtnSeConnecter);
    }

    public void OnClickBtnSeConnecter(View v){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}