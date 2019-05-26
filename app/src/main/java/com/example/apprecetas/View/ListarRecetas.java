package com.example.apprecetas.View;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.apprecetas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListarRecetas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_recetas);


    }
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("-----Fuera------");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("-----1------");
                    String api = "https://api-receta.herokuapp.com/";
                    URL url = new URL(api + "listarTodo");
                    HttpURLConnection urlConnection = null;
                    System.out.println("-----2------");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder b = new StringBuilder();
                    String input;

                    while ((input = br.readLine()) != null){
                        b.append(input);
                    }

                    try {
                        JSONArray j = new JSONArray(b.toString());
                        JSONObject o = j.getJSONObject(0);      //Prueba de recibir el JSON
                        System.out.println(o.get("NOM"));
                        System.out.println(j);

                    } catch (JSONException e) {
                        Log.e("MYAPP", "unexpected JSON exception", e);
                    }
                    br.close();
                    urlConnection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /*
    class RetrieveFeedTask extends AsyncTask<URL, String, String> {
        protected String doInBackground(String address) {
            String api = "https://api-receta.herokuapp.com/";
            try {
                URL url = new URL(api + "listarTodo");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    System.out.println(stringBuilder.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);

            }
        }
    }

    public class Receta{
        public String nombre;
        public ArrayList ingredientes;
        public String tipo;
        public ArrayList pasos;
        public ArrayList imagenes;

    }
    */

}
