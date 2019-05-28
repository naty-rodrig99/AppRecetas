package com.example.apprecetas.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apprecetas.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Button btn_newaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_newaccount = (Button) findViewById(R.id.btn_new_ac);
        btn_newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    public String getMail(){
        EditText ed = findViewById(R.id.txt_correo2);
        String res = ed.getText().toString();
        return res;
    }

    public String getPassword(){
        EditText ed = findViewById(R.id.txt_password2);
        String res = ed.getText().toString();
        return res;
    }

    public void callMenu(View v){
        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
    }


    public void iniciarSesion(View v){
        String correo = getMail();
        String password = getPassword();
        try {
            String api = "https://api-receta.herokuapp.com/";
            URL url = new URL(api + "login?correo="+correo+"&"+"password="+password);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder b = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null){
                b.append(input);
            }
            String resul = b.toString();
            System.out.println("--------------------------Resultado:"+resul);
            if (resul.equals( "Login exitoso")){
                callMenu(findViewById(android.R.id.content));

            }
            else{
                Toast.makeText(LoginActivity.this, "Datos incorrectos!", Toast.LENGTH_LONG).show();

            }
            br.close();
            urlConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}