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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewAccountActivity extends AppCompatActivity {
    Button btn_newaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_newaccount);

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
    public boolean validateEmail(String emailStr)
    {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(emailStr);
        if(matcher.find())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void callLogin(View v){
        startActivity(new Intent(NewAccountActivity.this, LoginActivity.class));
    }
    public void registrar(View v){
        String correo = getMail();
        String password = getPassword();
        //if (validateEmail(correo)){
            if(password.length()>=4){
                try {
                    String api = "https://api-receta.herokuapp.com/";
                    URL url = new URL(api + "agregarUsuario?correo="+correo+"&"+"password="+password);
                    HttpURLConnection urlConnection = null;
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder b = new StringBuilder();
                    String input;

                    while ((input = br.readLine()) != null){
                        b.append(input);
                    }
                    String resul = b.toString();
                    System.out.println("Resul Input:"+resul);
                    if (resul.equals( "Usuario Registrado")){
                        Toast.makeText(NewAccountActivity.this, "Se ha registrado satisfactoriamente.", Toast.LENGTH_LONG).show();
                        callLogin(findViewById(android.R.id.content));

                    }
                    else{
                        Toast.makeText(NewAccountActivity.this, "El usuario ya existe.", Toast.LENGTH_LONG).show();
                        callLogin(findViewById(android.R.id.content));
                    }

                    br.close();
                    urlConnection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else{
                Toast.makeText(NewAccountActivity.this, "Error: La contraseña debe tener mas de 4 digitos.",
                        Toast.LENGTH_LONG).show();
            }
        }
        //else{
         //   Toast.makeText(NewAccountActivity.this, "Error: Formato de correo invalido.", Toast.LENGTH_LONG).show();
        //}

    //}
}
