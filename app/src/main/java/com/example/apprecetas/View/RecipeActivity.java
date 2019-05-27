package com.example.apprecetas.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.apprecetas.R;

public class RecipeActivity extends AppCompatActivity {

    int[] imagenes = {R.drawable.pizza,R.drawable.dishes,R.drawable.enano};
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        final ImageSwitcher imageSwitcher = findViewById(R.id.imageSwitcher);
        if(imageSwitcher != null){
            imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setLayoutParams(new
                            ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    return imageView;
                }
            });
            imageSwitcher.setImageResource(imagenes[index]);

            Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            imageSwitcher.setInAnimation(in);

            Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
            imageSwitcher.setOutAnimation(out);
        }
        Button button = findViewById(R.id.btnNext);
        if(button != null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = (++index < imagenes.length) ? index : 0;
                    if(imageSwitcher != null){
                        imageSwitcher.setImageResource(imagenes[index]);
                    }
                }
            });
        }
    }
}
