package com.example.lagartoproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class webview extends AppCompatActivity {

    Button btnIngresar;
    Handler handler = new Handler();
    final int TIEMPO = 1000;
    boolean primeraVez = true;
    String ultimoCode = "";
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        btnIngresar = findViewById(R.id.btnIngresarwebview);
        btnIngresar.setVisibility(View.GONE);

        revisarDatosHandler();

        WebView aea = (WebView) findViewById(R.id.web_1);
        aea.getSettings().setJavaScriptEnabled(true);
        aea.getSettings().setBuiltInZoomControls(true);
        aea.loadUrl("http://tux777.pythonanywhere.com/o/authorize/?response_type=code&client_id" +
                        "=d0vZvJPYejBKMRO1brP9AmVixfyzD3BBL6ySlj5k&redirect_uri=http%3A%2F%2Ftux777." +
                        "pythonanywhere.com%2Fcode%2F&scope=write&state=1234zyx&code_challenge=" +
                        "47DEQpj8HBSa-_TImW-5JCeuQeRkm5NMpJWZG3hSuFU&code_challenge_method=S256");

                aea.setWebViewClient(new WebViewClient(){
                    public boolean shouldOverriceUrlLoading(WebView view, String url){
                        return false;
                    }
                });
    }

    public void revisarDatosHandler() {
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
                String url ="http://tux777.pythonanywhere.com/code/";
                JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    String valorCode = response.getJSONObject(0).getString("code");
                                    if(primeraVez){
                                        ultimoCode = valorCode;
                                        primeraVez = false;
                                    }else{
                                        if(!ultimoCode.equals(valorCode)){
                                            btnIngresar.setVisibility(View.VISIBLE);
                                            ultimoCode = valorCode;
                                            Toast.makeText(getApplicationContext(),
                                                    "Ingrese por el boton",
                                                    Toast.LENGTH_LONG).show();
                                        }
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
                                "Compruebe que tiene internet",
                                Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            };
        });
    }

    public void ingresarMain(View view){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                JSONObject json = new JSONObject();
                try {
                    json.put("grant_type","authorization_code");
                    json.put("code",ultimoCode);
                    json.put("redirect_uri","http://tux777.pythonanywhere.com/code/");
                    json.put("client_id","d0vZvJPYejBKMRO1brP9AmVixfyzD3BBL6ySlj5k");
                    json.put("code_verifier","password");
                    json.put("client_secret","W47KxYKFu5SV27crYXWrfqmpczv22NrNdoEmvLtcBvdCwhGoqkCXpe1hOpcczCt3rdJkl8JPnWoOlk3XDkl9sGN1nURipB54U3BY9c4hc51od5NijBoRpD8xMzmmaTne");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(json.toString());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://tux777.pythonanywhere.com/o/token/";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    token = response.getString("access_token");
                                    System.out.println(token + "--------------------------------------------------------------------------");
                                    if (!token.equals("")){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
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
                                        "Compruebe que tiene internet" + error.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }){ @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                         };
                queue.add(jsonObjectRequest);
            };
        });
    }

    public void ingresarMain2(View view){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    //for POST requests, only the following line should be changed to

                StringRequest sr = new StringRequest(Request.Method.POST, "http://tux777.pythonanywhere.com/o/token/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    System.out.println(obj.toString());
                                    token = obj.getString("access_token");
                                    System.out.println(token + "--------------------------------------------------------------------------");
                                    if (!token.equals("")){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("token",token);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("HttpClient", "error: " + error.toString());
                            }
                        })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("grant_type","authorization_code");
                        params.put("code",ultimoCode);
                        params.put("redirect_uri","http://tux777.pythonanywhere.com/code/");
                        params.put("client_id","d0vZvJPYejBKMRO1brP9AmVixfyzD3BBL6ySlj5k");
                        params.put("code_verifier","password");
                        params.put("client_secret","W47KxYKFu5SV27crYXWrfqmpczv22NrNdoEmvLtcBvdCwhGoqkCXpe1hOpcczCt3rdJkl8JPnWoOlk3XDkl9sGN1nURipB54U3BY9c4hc51od5NijBoRpD8xMzmmaTne");
                        return params;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }
                };
                queue.add(sr);
            };
        });
    }

}
