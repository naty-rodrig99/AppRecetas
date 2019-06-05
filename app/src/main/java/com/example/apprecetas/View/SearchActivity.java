package com.example.apprecetas.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.apprecetas.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RadioButton nombre,tipo,ingrediente;
    EditText eBuscar;
    public static ArrayList<Receta> resultado = new ArrayList<>();
    public String palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        eBuscar = findViewById(R.id.txt_buscar);

        nombre =  findViewById(R.id.nombre);
        tipo =  findViewById(R.id.tipo);
        ingrediente =  findViewById(R.id.ingrediente);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palabra = eBuscar.getText().toString();
                String result = "";
                result += (nombre.isChecked())?"nombre":(tipo.isChecked())?"tipo":(ingrediente.isChecked())?"ingrediente":"";
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                System.out.println("Palabra:"+ palabra);
                System.out.println("Tipo:"+result);
                intent.putExtra("nombre", palabra);
                intent.putExtra("tipo", result);
                startActivity(intent);

            }
        });

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


}