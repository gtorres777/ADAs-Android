package com.example.lagartoproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtUsuario,txtClave;
    TextView lblEstado;
    Button btnLogear, btnSalir;
    Handler handler = new Handler();
    final int TIEMPO = 1100;
    String ultimaAccion = "Sentarse";
    String token = "";

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

        mostrarDatosHandler();
        token = getIntent().getStringExtra("token");
        System.out.println(token + "---------------------------------------------------------------");
    }

    public void mostrarDatosHandler() {
        handler.postDelayed(new Runnable() {
            public void run() {
                mostrarDatos();
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);
    }

    public void mostrarDatos(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://tux777.pythonanywhere.com/datos/";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    System.out.println(response.length());
                                    String valorTemperatura = response.getJSONObject(response.length()-1).getString("temperatura");
                                    String valorHumedad = response.getJSONObject(response.length()-1).getString("humedad");
                                    //Log.i(valor,valor);
                                    if(valorTemperatura.equals("") && valorHumedad.equals("")){
                                        //wasa;
                                    }else{
                                        txtUsuario.setText(valorTemperatura);
                                        txtClave.setText(valorHumedad);
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
                        lblEstado.setText("Compruebe que tiene acceso a internet - mostrarDatos");
                    }
                });
                queue.add(stringRequest);
            };
        });
    }

    //Peticion de post actualizar datos en 0 y 1     http://192.168.1.12:8000/token/?temperatura=200&humedad=100
    //Fin del codigo antiguo de juan y edagardo

    // Funcion para que el robot se pare
    public void accionParar(View v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://tux777.pythonanywhere.com/accion/?accion=pararse";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Parado");
                                        ultimaAccion = "Pararse";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion/?accion=sentarse";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Sentado");
                                        ultimaAccion = "Sentarse";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }

                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion/?accion=avanzar";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Avanzando...");
                                        ultimaAccion = "Avanzar";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion/?accion=retroceder";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Retrocediendo...");
                                        ultimaAccion = "Retroceder";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion/?accion=girarIzquierda";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Girando a la Izquierda...");
                                        ultimaAccion = "Girar Izquierda";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion/?accion=girarDerecha";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Girando a la Derecha...");
                                        ultimaAccion = "Girar Derecha";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion_vip/?accion=saludar";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje VIP");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Saludando...");
                                        ultimaAccion = "Saludar";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
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
                String url ="http://tux777.pythonanywhere.com/accion_vip/?accion=bailar";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valor = response.getJSONObject(0).getString("Mensaje VIP");
                                    Log.i(valor,valor);
                                    if(valor.equals("Update Success")){
                                        lblEstado.setText("Estado: Bailando...");
                                        ultimaAccion = "Bailar";
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
                        lblEstado.setText("Compruebe que tiene acceso a internet");
                    }
                })
                {
                        @Override
                        public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }

                };
                queue.add(stringRequest);
            };
        });
    }
    public void enviar(View view){
        this.doInBackground("http://tux777.pythonanywhere.com/o/token/?","authorization_code","codeaca",
                "http://tux777.pythonanywhere.com/code/","d0vZvJPYejBKMRO1brP9AmVixfyzD3BBL6ySlj5k",
                "W47KxYKFu5SV27crYXWrfqmpczv22NrNdoEmvLtcBvdCwhGoqkCXpe1hOpcczCt3rdJkl8JPnWoOlk3XDkl9sGN1nURipB54U3BY9c4hc51od5NijBoRpD8xMzmmaTne","password");
    }

    protected String doInBackground(String... params) {

        String urlString = params[0];
        String grant_type = params[1];
        String code = params[2];
        String redirect_uri = params[3];
        String client_id = params[4];
        String client_secret = params[5];
        String code_verifier = params[6];
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

            data += "&" + URLEncoder.encode("code", "UTF-8") + "="
                    + URLEncoder.encode(code, "UTF-8");

            data += "&" + URLEncoder.encode("redirect_uri", "UTF-8") + "="
                    + URLEncoder.encode(redirect_uri, "UTF-8");

            data += "&" + URLEncoder.encode("client_id", "UTF-8") + "="
                    + URLEncoder.encode(client_id, "UTF-8");

            data += "&" + URLEncoder.encode("client_secret", "UTF-8") + "="
                    + URLEncoder.encode(client_secret, "UTF-8");

            data += "&" + URLEncoder.encode("code_verifier", "UTF-8") + "="
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


    public void loguearaa(View v){
        String usuario = "tux777";
        Intent registroIntent = new Intent(getApplicationContext(),registro.class);
        registroIntent.putExtra("usuario", usuario);
        registroIntent.putExtra("ultimaAccion", ultimaAccion);
        registroIntent.putExtra("token",token);
        startActivity(registroIntent);
        finish();
    }

}


