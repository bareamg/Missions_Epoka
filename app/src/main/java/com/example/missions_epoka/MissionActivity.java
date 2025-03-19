
package com.example.missions_epoka;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MissionActivity extends AppCompatActivity {
    private EditText dateDebut, dateFin;
    private int mYear, mMonth, mDay;
    private Button btnEnvoyer, btnDateDebut, btnDateFin;
    private Spinner spiVilles;
    private List<String> listeVillesStr;
    private List<Ville> listeVilles;
    private ArrayAdapter<String> adapter;
    private String url = "http://10.0.2.2/missionepoka/listevilles.php";
    private Ville maVille;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

//initialisation des champs de saisies
        dateDebut = findViewById(R.id.dateDebut);
        dateFin = findViewById(R.id.dateFin);

//initialisation des boutons
        btnEnvoyer = findViewById(R.id.btnEnvoyer);
        btnEnvoyer.setOnClickListener(this::onClickBtnEnvoyer);
        btnDateDebut = findViewById(R.id.btnDateDebut);
        btnDateDebut.setOnClickListener(this::onClickBtnDateDebut);
        btnDateFin = findViewById(R.id.btnDateFin);
        btnDateFin.setOnClickListener(this::onClickBtnDateFin);

//initialisation du spinner
        spiVilles = findViewById(R.id.spiVilles);
        listeVillesStr = new ArrayList<>();
        listeVilles = new ArrayList<>();

//initialisation de l'adapter pour le spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listeVillesStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiVilles.setAdapter(adapter);

        chargerVilles(maVille);
        for(String ville : listeVillesStr){
            Log.d("MyActivity", ville);
        }
    }

    //méthode qui prend les données en json renovyées par le service à l'url donnée, et les stock dans le spinner
    private void chargerVilles(Ville maVille) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listeVillesStr.clear(); // Vider la liste existante
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject ville = response.getJSONObject(i);
                                int idVille = ville.getInt("Vil_No");
                                String nomVille = ville.getString("Vil_Nom");
                                String cpVille = ville.getString("Vil_CP");
                                listeVillesStr.add(nomVille + " " + cpVille);
                                listeVilles.add(new Ville(idVille, nomVille, cpVille));
                            }
                            adapter.notifyDataSetChanged();
                            Log.d("MissionActivity", "Nombre de villes chargées : " + listeVilles.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("MissionActivity", "Erreur JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MissionActivity", "Erreur Volley : " + error.toString());
                    }
                });

        queue.add(jsonArrayRequest);
    }

    //méthode au clique du bouton btnDateDebut qui affiche le menu avec toute les dates, et qui la stock dans le champs de saisie.
    public void onClickBtnDateDebut(View v) {
        final Calendar calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepPickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateDebut.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },mYear,mMonth,mDay);
        datepPickerDialog.show();
    }

    public void onClickBtnDateFin(View v) {
        final Calendar calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepPickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateFin.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },mYear,mMonth,mDay);
        datepPickerDialog.show();
    }

    public void onClickBtnEnvoyer(View v){
        String dateDebut;
        /*for (Ville ville : listeVilles){
            if (ville.getNom() == spiVilles.getSelectedItem().toString()){
        }*/
        String villeSelectionnee = spiVilles.getSelectedItem().toString();
    }
}