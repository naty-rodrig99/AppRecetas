package com.example.apprecetas.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.apprecetas.R;

public class MenuActivity extends AppCompatActivity {

    private FloatingActionButton fl_btn_search1;
    private FloatingActionButton fl_btn_search2;
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

}
