package com.example.lagartoproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario,txtClave;
    TextView estado;
    Button btnLogear, btnRegistrar,btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave=findViewById(R.id.txtClave);
        btnLogear=findViewById(R.id.btnLogear);
        btnSalir=findViewById(R.id.btnSalir);
        estado = findViewById(R.id.estado);
    }

    //Peticion de post actualizar datos en 0 y 1     http://192.168.1.12:8000/token/?temperatura=200&humedad=100


    public void Logear(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //String nombre = txtUsuario.getText().toString().trim();
                //String clave = txtClave.getText().toString().trim();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/datos";
                //String url ="http://192.168.0.27:8080/WebApp06_WebAppSistema/rest/usuarios/login?";
                //url = url + "nombre=" + nombre + "&clave=" + clave;


                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("id");
                                    Log.i(valor,valor);
                                    if(valor.equals("1")){

                                        Intent llamar = new Intent(getApplicationContext(),MostrarDatos.class);
                                        startActivity(llamar);
                                        finish();

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Credenciales invalidas",
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
            };
        });
    }




    public void enviarDatos(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String dato1 = txtUsuario.getText().toString().trim();
                String dato2 = txtClave.getText().toString().trim();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                //String url ="http://192.168.1.12:8000/datos";
                String url ="http://192.168.1.131:8000/token/?";
                url = url + "temperatura=" + dato1 + "&humedad=" + dato2;

                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){

                                        //Intent llamar = new Intent(getApplicationContext(),MostrarDatos.class);
                                        //startActivity(llamar);
                                        //finish();
                                        estado.setText("subiendo...");
                                        Toast.makeText(getApplicationContext(),"Su mensaje fue enviado con exito",
                                                Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Credenciales invalidas",
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
            };
        });
    }



    public void avanzar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String dato1 = "1";
                String dato2 = "0";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                //String url ="http://192.168.1.12:8000/datos";
                String url ="http://192.168.1.131:8000/accion/?";
                url = url + "accion=pararse";

                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){

                                        //Intent llamar = new Intent(getApplicationContext(),MostrarDatos.class);
                                        //startActivity(llamar);
                                        //finish();
                                        estado.setText("avanzando...");
                                        Toast.makeText(getApplicationContext(),"Avanzar fue enviado con exito",
                                                Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Credenciales invalidas",
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
            };
        });
    }



    public void retroceder(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String dato1 = "0";
                String dato2 = "1";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                //String url ="http://192.168.1.12:8000/datos";
                String url ="http://192.168.1.131:8000/accion/?";
                url = url + "accion=sentarse";

                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){

                                        //Intent llamar = new Intent(getApplicationContext(),MostrarDatos.class);
                                        //startActivity(llamar);
                                        //finish();
                                        estado.setText("Retrocediendo...");
                                        Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito",
                                                Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Credenciales invalidas",
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
            };
        });
    }

}
