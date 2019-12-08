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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    TextView txtUsuario,txtClave;
    TextView lblEstado;
    Button btnLogear, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave=findViewById(R.id.txtClave);

        btnSalir=findViewById(R.id.btnSalir);
        lblEstado = findViewById(R.id.lblEstado);
    }

    //Peticion de post actualizar datos en 0 y 1     http://192.168.1.12:8000/token/?temperatura=200&humedad=100
    //Inicio del codigo antiguo de juan y edagardo
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
                                        lblEstado.setText("subiendo...");
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
    //Fin del codigo antiguo de juan y edagardo

    // Funcion para que el robot se pare
    public void accionParar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=pararse";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Parado");
                                        //Toast.makeText(getApplicationContext(),"Avanzar fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot se siente
    public void accionSentar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=sentarse";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Sentado");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot avance
    public void accionAvanzar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=avanzar";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Avanzando...");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot retroceda
    public void accionRetroceder(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=retroceder";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Retrocediendo...");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot gire a la izquierda
    public void accionGirarIzquierda(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=girarIzquierda";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Girando...");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot gire a la derecha
    public void accionGirarDerecha(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=girarDerecha";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Girando...");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot salude
    public void accionSaludar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=saludar";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Saludando...");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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

    // Funcion para que el robot baile
    public void accionBailar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.131:8000/accion/?accion=bailar";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Bailando...");
                                        //Toast.makeText(getApplicationContext(),"Retroceder fue enviado con exito", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"No fue enviada la acción correctamente",
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
    public void enviar(View view){
        this.doInBackground("http://192.168.1.131:8000/o/token/?","password","kozak","totodiletux999","nl1hQDOjoTodris2ooxifflEATQSOk0lU5cCO2mV","u0JOw7C0GWWXyS8MCmeIL9r7vpuw0OzkkVjfByyvq6FCKh3XSIW5MpB3WjKsOkuA9nssmnRy7BjHTY2oij69zRYh4FW15nBXQVlKiklrFVoLo8hBRKpvNEgd09mz3LXz");

    }



    protected String doInBackground(String... params) {

        String urlString = params[0];
        String grant_type = params[1];
        String username = params[2];
        String password = params[3];
        String client_id = params[4];
        String client_secret = params[5];
        URL url = null;
        InputStream stream = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("grant_type", "UTF-8")
                    + "=" + URLEncoder.encode(grant_type, "UTF-8");

            data += "&" + URLEncoder.encode("username", "UTF-8") + "="
                    + URLEncoder.encode(username, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                    + URLEncoder.encode(password, "UTF-8");

            data += "&" + URLEncoder.encode("client_id", "UTF-8") + "="
                    + URLEncoder.encode(client_id, "UTF-8");

            data += "&" + URLEncoder.encode("client_secret", "UTF-8") + "="
                    + URLEncoder.encode(client_secret, "UTF-8");

            urlConnection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(data);
            wr.flush();

            stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
            String result = reader.readLine();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Result", "SLEEP ERROR");
        }
        return null;
    }

}


