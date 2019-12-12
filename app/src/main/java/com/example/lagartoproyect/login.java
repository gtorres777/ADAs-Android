package com.example.lagartoproyect;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class login extends AppCompatActivity {

    EditText loginUsuario;

    Button btn_sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsuario = findViewById(R.id.loginUsuario);
    }
    public void loguear(View v){
        Intent llamar = new Intent(getApplicationContext(),webview.class);
        startActivity(llamar);
        finish();
    }

    public void mostrarDatos(View view){
        Uri uri = Uri.parse("http://tux777.pythonanywhere.com/accounts/login/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
