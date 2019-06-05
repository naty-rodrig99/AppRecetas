package com.example.apprecetas.View;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.apprecetas.R;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private ArrayList<String> img = new ArrayList();

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        String nombre = "";
        String tipo = "";
        String ingredientes = "";
        String instrucciones = "";
        String imagenes = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getString("nombre");
            tipo = extras.getString("tipo");
            ingredientes = extras.getString("ingredientes");
            instrucciones = extras.getString("instrucciones");
            imagenes = extras.getString("imagenes");
        }

        img = convierteImagenes(imagenes);
        String[] finalImagenes = new String[img.size()];
        finalImagenes = img.toArray(finalImagenes);

        TextView tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvNombre.setText(nombre);

        TextView tvTipo = (TextView) findViewById(R.id.tvTipo);
        tvTipo.setText(tipo);

        TextView tvListaIngredinetes = (TextView) findViewById(R.id.tvListaIngredientes);
        tvListaIngredinetes.setText(ingredientes.replace("[", "").replace("]", ""));

        TextView tvListaInstrucciones = (TextView) findViewById(R.id.tvListaInstrucciones);
        tvListaInstrucciones.setText(instrucciones.replace("[", "").replace("]", ""));


        viewPager = (ViewPager)findViewById(R.id.viewPager1);
        adapter = new ViewPagerAdapter(RecipeActivity.this, finalImagenes);
        viewPager.setAdapter(adapter);
    }

    public ArrayList convierteImagenes(String imagenes){
        String palabra = "";
        int a = 1;
        for (int n = 0; n < imagenes.length (); n ++){
            if(imagenes.substring(n,a).equals(",")){
                img.add(palabra);
                palabra = "";
                a += 1;
            } else{
                if (imagenes.substring(n,a).equals("[") || imagenes.substring(n,a).equals("]") || imagenes.substring(n,a).equals(" ")) {
                    a += 1;
                } else {
                    palabra += imagenes.substring(n, a);
                    a += 1;
                }
            }
        }
        img.add(palabra);
        return img;
    }

}
