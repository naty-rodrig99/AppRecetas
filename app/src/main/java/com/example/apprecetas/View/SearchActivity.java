package com.example.apprecetas.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class SearchActivity extends AppCompatActivity {
    RadioButton nombre,tipo,ingrediente;
    EditText eBuscar;
    public static ArrayList<Receta> resultado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        eBuscar = (EditText) findViewById(R.id.txt_buscar);

        nombre = (RadioButton) findViewById(R.id.nombre);
        tipo = (RadioButton) findViewById(R.id.tipo);
        ingrediente = (RadioButton) findViewById(R.id.ingrediente);
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "Selecciono: ";
                result += (nombre.isChecked())?"Nombre":(tipo.isChecked())?"Tipo":(ingrediente.isChecked())?"Ingrediente":"";
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }
        });
        resultadoBusqueda("tipico", "tipo"); //Prueba de busqueda

    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";
        switch (view.getId()){
            case R.id.nombre:
                if(checked)
                    str = "nombre";
                break;
            case R.id.tipo:
                if(checked)
                    str = "tipo";
                break;
            case R.id.ingrediente:
                if(checked)
                    str = "ingrediente";
                break;
        }
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }
    public void resultadoBusqueda(final String nombre, final String tipo){
        resultado.clear();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String api = "https://api-receta.herokuapp.com/";
                    URL url = new URL(api) ;
                    if(tipo == "nombre") {
                        String str=api + "buscarNombre?nombre="+nombre;
                        str = str.replace(" ","-");
                        url = new URL(str);

                    }
                    if(tipo == "tipo") {
                        String str=api + "buscarTipo?tipo="+nombre;
                        str = str.replace(" ","-");
                        url = new URL(str);
                    }
                    if(tipo == "ingrediente") {
                        String str=api + "buscarIngrediente?nombre="+nombre;
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
                            if (tipo != "nombre") {
                                nom = (String) o.get("NOM");

                            }
                            ArrayList ing = MenuActivity.convertidorArrays((JSONArray)o.get("ING"));

                            if(tipo != "tipo") {
                                type = (String) o.get("TYPE");
                            }
                            ArrayList steps = MenuActivity.convertidorArrays((JSONArray)o.get("STEPS"));
                            ArrayList img = MenuActivity.convertidorArrays((JSONArray)o.get("IMAGES"));
                            if(tipo =="ingrediente") {
                                recipe = new Receta(nom, ing, type, steps, img);
                                System.out.println(recipe);
                            }
                            if(tipo == "nombre" ) {
                                recipe = new Receta(nombre, ing, type, steps, img);

                            }
                            if(tipo == "tipo" ) {
                                recipe = new Receta(nom, ing, tipo, steps, img);

                            }

                            resultado.add(recipe);
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

}