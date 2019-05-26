package com.example.apprecetas.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.apprecetas.R;

public class SearchActivity extends AppCompatActivity {
    RadioButton nombre,tipo,ingrediente;
    EditText eBuscar;

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
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";
        switch (view.getId()){
            case R.id.nombre:
                if(checked)
                    str = "Nombre seleccionado";
                break;
            case R.id.tipo:
                if(checked)
                    str = "Tipo seleccionado";
                break;
            case R.id.ingrediente:
                if(checked)
                    str = "Ingrediente seleccionado";
                break;
        }
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

}
