package com.example.apprecetas.View;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.apprecetas.R;
import com.squareup.picasso.Picasso;

public class AddRecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("AQUIIIIII");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);
        ImageView img = findViewById(R.id.imgV);
        String url="https://s3.us-east-2.amazonaws.com/progralenguajes/pollo.jpg";
        Picasso.get().load(url).into(img);

        System.out.println("AQUI2");

    }



    public static void uploadImageToAWS(String selectedImagePath) {





    }
}
