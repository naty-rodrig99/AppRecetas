package com.example.apprecetas.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apprecetas.R;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_newAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        btn_newAccount = (Button) findViewById(R.id.btn_new_ac);
        btn_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startMenuActivity(View v){
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }
    
    public void newAccountActivity(View v){
        Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
        startActivity(intent);
    }
    public void callListarRecetas(View v){
        startActivity(new Intent(LoginActivity.this, ListarRecetas.class));
    }

}
