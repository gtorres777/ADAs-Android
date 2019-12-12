package com.example.lagartoproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    EditText loginUsuario, loginClave;
    Button btn_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsuario = findViewById(R.id.loginUsuario);
    }
    public void loguear(View v){
        String Usuariolbl = loginUsuario.getText().toString();
        Intent llamar = new Intent(getApplicationContext(),MainActivity.class);
        llamar.putExtra("usuario", Usuariolbl);
        startActivity(llamar);
        finish();
    }
}
