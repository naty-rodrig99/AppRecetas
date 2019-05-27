package com.example.apprecetas.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.apprecetas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private FloatingActionButton fl_btn_search1;
    private FloatingActionButton fl_btn_search2;
    public static ArrayList<Receta> recetas = new ArrayList<Receta>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fl_btn_search1 = (FloatingActionButton) findViewById(R.id.search_fab2);
        fl_btn_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        fl_btn_search2 = (FloatingActionButton) findViewById(R.id.search_fab1);
        fl_btn_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String api = "https://api-receta.herokuapp.com/";
                    URL url = new URL(api + "listarTodo");
                    HttpURLConnection urlConnection = null;
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder b = new StringBuilder();
                    String input;

                    while ((input = br.readLine()) != null){
                        b.append(input);
                    }

                    try {
                        JSONArray j = new JSONArray(b.toString());
                        int cont = 0;
                        while(cont< j.length()) {
                            JSONObject o = j.getJSONObject(cont);      //Prueba de recibir el JSON
                            String nom = (String)o.get("NOM");
                            ArrayList ing = convertidorArrays((JSONArray)o.get("ING"));
                            String type = (String)o.get("TYPE");
                            ArrayList steps = convertidorArrays((JSONArray)o.get("STEPS"));
                            ArrayList img = convertidorArrays((JSONArray)o.get("IMAGES"));
                            Receta recipe = new Receta(nom,ing,type,steps,img);
                            recetas.add(recipe);
                            cont++;
                        }

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
    public static class Receta{
        public String nombre;
        public ArrayList ingredientes;
        public String tipo;
        public ArrayList pasos;
        public ArrayList imagenes;
        Receta(){};
        Receta(String nom,ArrayList ing,String type,ArrayList steps,ArrayList img){
            this.nombre = nom;
            this.ingredientes = ing;
            this.tipo = type;
            this.pasos = steps;
            this.imagenes = img;
        }

    }

    public static ArrayList convertidorArrays(JSONArray arr){
        ArrayList<String> resul = new ArrayList<String>();
        int cont=0;
        while(cont < arr.length()){
            try {
                String x = (String)arr.get(cont);
                resul.add(x);
                cont++;

            }catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }

        }
        return resul;

    }
}
