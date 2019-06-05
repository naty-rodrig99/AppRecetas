package com.example.apprecetas.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class SearchResultActivity extends AppCompatActivity {
    private FloatingActionButton fl_btn_search1;
    private FloatingActionButton fl_btn_search2;
    public static ArrayList<Receta> resultado = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView = findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Botones
        fl_btn_search1 = (FloatingActionButton) findViewById(R.id.search_fab2);
        fl_btn_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        fl_btn_search2 = (FloatingActionButton) findViewById(R.id.search_fab1);
        fl_btn_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        String nombre = "";
        String tipo = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getString("nombre");
            tipo = extras.getString("tipo");
            resultadoBusqueda(nombre,tipo);
        }
    }

    public void resultadoBusqueda(final String nombre, final String tipo){
        resultado.clear();

        try {
            String api = "https://api-receta.herokuapp.com/";
            URL url = new URL(api) ;
            if(tipo.equals("nombre")) {
                String str=api + "buscarNombre?nombre="+nombre+"&auth="+LoginActivity.authKey;
                str = str.replace(" ","-");
                url = new URL(str);

            }
            if(tipo.equals("tipo")) {
                String str=api + "buscarTipo?tipo="+nombre+"&auth="+LoginActivity.authKey;
                str = str.replace(" ","-");
                url = new URL(str);
            }
            if(tipo.equals("ingrediente")) {
                String str=api + "buscarIngrediente?nombre="+nombre+"&auth="+LoginActivity.authKey;
                str = str.replace(" ","-");
                url = new URL(str);
            }

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
                String nom ="";
                String type="";
                Receta recipe = new Receta();
                while(cont< j.length()) {
                    JSONObject o = j.getJSONObject(cont);      //Prueba de recibir el JSON
                    if (!tipo.equals("nombre")) {
                        nom = (String) o.get("NOM");

                    }
                    ArrayList ing = MenuActivity.convertidorArrays((JSONArray)o.get("ING"));

                    if(!tipo.equals("tipo")) {
                        type = (String) o.get("TYPE");
                    }
                    ArrayList steps = MenuActivity.convertidorArrays((JSONArray)o.get("STEPS"));
                    ArrayList img = MenuActivity.convertidorArrays((JSONArray)o.get("IMAGES"));
                    if(tipo.equals("ingrediente")) {
                        recipe = new Receta(nom, ing, type, steps, img);
                        System.out.println(recipe);
                    }
                    if(tipo.equals("nombre")) {
                        recipe = new Receta(nombre, ing, type, steps, img);

                    }
                    if(tipo.equals("tipo")) {
                        recipe = new Receta(nom, ing, tipo, steps, img);

                    }

                    resultado.add(recipe);
                    cont++;
                }
                System.out.println(resultado.get(0));
                RecyclerView.Adapter mAdapter = new ListAdapter(resultado.toArray(new Receta[resultado.size()]));
                recyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }
            br.close();
            urlConnection.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
