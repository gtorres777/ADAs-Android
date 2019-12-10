package com.example.lagartoproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MostrarDatos extends AppCompatActivity {

    TextView lblUsuario, lblTempP, lblHumP, lblPeriodo, lblAccion, lblDescripcion, lblFecha, lblHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        lblUsuario = findViewById(R.id.lblUsuario);
        lblTempP = findViewById(R.id.lblTempP);
        lblHumP = findViewById(R.id.lblHumP);
        lblPeriodo = findViewById(R.id.lblPeriodo);
        lblAccion = findViewById(R.id.lblAccion);
        lblDescripcion = findViewById(R.id.lblDescripcion);
        lblFecha = findViewById(R.id.lblFecha);
        lblHora = findViewById(R.id.lblHora);

        mostrarDatosReportes();
    }

    public void mostrarDatosReportes(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://tux777.pythonanywhere.com/reportes/";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valorUsuario = response.getJSONObject(0).getString("usuario");
                                    String valorHumedadP = response.getJSONObject(0).getString("humedad_prom");
                                    String valorPeriodo = response.getJSONObject(0).getString("periodo");
                                    String valorTemperaturaP= response.getJSONObject(0).getString("temperatura_prom");
                                    String valorDescripcion = response.getJSONObject(0).getString("descripcion");
                                    String valorHora = response.getJSONObject(0).getString("hora");
                                    String valorFecha = response.getJSONObject(0).getString("fecha");
                                    String valorUltimaA = response.getJSONObject(0).getString("ultima_Accion");
                                    //Log.i(valor,valor);

                                    lblUsuario.setText(valorUsuario);
                                    lblTempP.setText(valorTemperaturaP);
                                    lblHumP.setText(valorHumedadP);
                                    lblPeriodo.setText(valorPeriodo);
                                    lblAccion.setText(valorUltimaA);
                                    lblDescripcion.setText(valorDescripcion);
                                    lblFecha.setText(valorFecha);
                                    lblHora.setText(valorHora);

                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error en la data recibida",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Compruebe que tiene acceso a internet",
                                Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            };
        });
    }

    public void loguear(View v){
        Intent llamar = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(llamar);
        finish();
    }
}
