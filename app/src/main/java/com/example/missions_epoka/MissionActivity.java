
package com.example.missions_epoka;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MissionActivity extends AppCompatActivity {
    private EditText dateDebut, dateFin;
    private int mYear, mMonth, mDay, idVille;
    private Button btnEnvoyer, btnDateDebut, btnDateFin;
    private Spinner spiVilles;
    private List<String> listeVillesStr;
    private List<Ville> listeVilles;
    private ArrayAdapter<String> adapter;
    private String URLVilles = "http://10.0.2.2/missionepoka/listevilles.php";
    private String URLMissions = "http://10.0.2.2/missionepoka/mission.php";
    private int salNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        Intent intent = getIntent();
        salNo = Integer.parseInt(intent.getStringExtra("no"));

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

        chargerVilles();
    }

    //méthode qui prend les données en json renovyées par le service à l'url donnée, et les stock dans le spinner
    private void chargerVilles() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLVilles, null,
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
        String dateDebutContent = dateDebut.getText().toString();
        String dateFinContent = dateFin.getText().toString();
        String villeSelectionnee = spiVilles.getSelectedItem().toString();

        for (int i=0; i<listeVillesStr.size(); i++){
            if(listeVillesStr.get(i).equals(villeSelectionnee)){
                idVille = listeVilles.get(i).getId();
            }
        }

        if(verifDates(dateDebutContent, dateFinContent)){
            EnvoyerMission(dateDebutContent, dateFinContent, salNo, idVille);
        }
    }

    //méthode qui vérifie les champs dates
    public boolean verifDates(String dateDebutStr, String dateFinStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date dateDebut = sdf.parse(dateDebutStr);
            Date dateFin = sdf.parse(dateFinStr);
            Calendar todayCal = Calendar.getInstance();
            todayCal.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = todayCal.getTime();

            if (dateDebut.before(yesterday)) {
                Toast.makeText(this, "La date de début ne peut pas être avant aujourd'hui", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (dateFin.before(yesterday)) {
                Toast.makeText(this, "La date de fin ne peut pas être avant aujourd'hui", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (dateDebut.after(dateFin)) {
                Toast.makeText(this, "La date de début doit avant à la date de fin", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;

        } catch (ParseException e) {
            Toast.makeText(this, "Erreur lors de la conversion des dates", Toast.LENGTH_SHORT).show();
            Log.e("MissionActivity", "Erreur de parsing de date", e);
            return false;
        }
    }

    public void EnvoyerMission(String dateDebut, String dateFin, int salNo, int idVille) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLMissions,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String message = jsonResponse.getString("message");

                            if (success) {
                                Toast.makeText(MissionActivity.this, "Mission ajoutée avec succès.", Toast.LENGTH_LONG).show();
                                finish(); // Ferme l'activité courante
                            } else {
                                Toast.makeText(MissionActivity.this, "Veuillez saisir tous les champs.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MissionActivity.this, "Erreur de parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MissionActivity.this, "Erreur réseau: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dateDebut", dateDebut);
                params.put("dateFin", dateFin);
                params.put("noSalarie", String.valueOf(salNo));
                params.put("noVille", String.valueOf(idVille));
                return params;
            }
        };

        // Ajout de la requête à la file d'attente Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}