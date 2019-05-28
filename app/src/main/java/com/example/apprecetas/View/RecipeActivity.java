package com.example.apprecetas.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.apprecetas.R;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    //int[] imagenes = {R.drawable.pizza,R.drawable.dishes,R.drawable.enano};
    private ArrayList imagenes = new ArrayList();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        String nombre = "";
        String tipo= "";
        String ingredientes="";
        String instrucciones="";
        //ArrayList imagenes ;
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            nombre = extras.getString("nombre");
            tipo = extras.getString("tipo");
            ingredientes = extras.getString("ingredientes");
            instrucciones = extras.getString("instrucciones");
            imagenes = extras.getStringArrayList("imagenes");
        }
        //ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        //Picasso.get().load(gettingImageUrl).into(imageView1);

        TextView tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvNombre.setText(nombre);

        TextView tvTipo = (TextView) findViewById(R.id.tvTipo);
        tvTipo.setText(tipo);

        TextView tvListaIngredinetes = (TextView) findViewById(R.id.tvListaIngredientes);
        tvListaIngredinetes.setText(ingredientes.replace("[","").replace("]",""));

        TextView tvListaInstrucciones = (TextView) findViewById(R.id.tvListaInstrucciones);
        tvListaInstrucciones.setText(instrucciones.replace("[","").replace("]",""));


        //Imagenes
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
            //imageSwitcher.setImageResource(Picasso.get().load(imagenes.get(index)));

            Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            imageSwitcher.setInAnimation(in);

            Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
            imageSwitcher.setOutAnimation(out);
        }
        /*Button button = findViewById(R.id.btnNext);
        if(button != null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = (++index < imagenes.size()) ? index : 0;
                    if(imageSwitcher != null){
                        imageSwitcher.setImageResource(imagenes.get(index));
                    }
                }
            });
        } */
    }
}
