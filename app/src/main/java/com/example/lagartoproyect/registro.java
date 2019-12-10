package com.example.lagartoproyect;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class registro extends AppCompatActivity {

    TextView registroUsuarioName, registroAccion;
    EditText registroDescripcion, registroPeriodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registroUsuarioName = findViewById(R.id.registroUsuarioName);
        registroAccion = findViewById(R.id.registroAccion);

        registroDescripcion = findViewById(R.id.registroDescripcion);
        registroPeriodo = findViewById(R.id.registroPeriodo);

        registroUsuarioName.setText(getIntent().getStringExtra("usuario"));
        registroAccion.setText(getIntent().getStringExtra("ultimaAccion"));
    }


    public void loguear(View v){
        if(registroPeriodo.getText().toString().equals("")){}
        else {
                AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String descripcion = registroDescripcion.getText().toString();
                    String nuevaDecripcion = "";
                    if(descripcion.equals("")){
                        String chara;
                        for (int i = 0; i < descripcion.length(); i++){
                            chara = descripcion.charAt(i) + "";
                            if(chara.equals(" ")){
                                nuevaDecripcion = nuevaDecripcion + "%20";
                            }else{
                                nuevaDecripcion = nuevaDecripcion + chara;
                            }

                        }
                    }
                    System.out.println(nuevaDecripcion);
                    String url = "http://tux777.pythonanywhere.com/reporte/?usuario=" +
                            getIntent().getStringExtra("usuario") + "&periodo=" +
                            registroPeriodo.getText().toString() + "&descripcion=" +
                            nuevaDecripcion + "&ultimaAccion=" +
                            getIntent().getStringExtra("ultimaAccion");
                    JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
                                        String valor = response.getJSONObject(0).getString("Mensaje");
                                        Log.i(valor, valor);
                                        if (valor.equals("Add Success")) {
                                            Intent llamar = new Intent(getApplicationContext(), MostrarDatos.class);
                                            startActivity(llamar);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "No fue enviada la acción correctamente",
                                                    Toast.LENGTH_LONG).show();
                                        }
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
                }

                ;
            });
        }
    }
}
