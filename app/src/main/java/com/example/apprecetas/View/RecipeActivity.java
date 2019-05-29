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
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.apprecetas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private ArrayList<String> img = new ArrayList();
    private int index = 0;
    private ArrayList xx = new ArrayList();
    private int[] prueba = {R.drawable.pizza, R.drawable.dishes, R.drawable.enano};
    private String[] prueba1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        String nombre = "";
        String tipo= "";
        String ingredientes="";
        String instrucciones="";
        String imagenes = "" ;
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            nombre = extras.getString("nombre");
            tipo = extras.getString("tipo");
            ingredientes = extras.getString("ingredientes");
            instrucciones = extras.getString("instrucciones");
            imagenes = extras.getString("imagenes");
        }
        img = convierteImagenes (imagenes);
        prueba1 = new String[img.size()];

        String[] stockArr = new String[img.size()];
        stockArr = img.toArray(stockArr);

        System.out.println("XX:" + xx);
        System.out.println("AA"+ xx.get(0));
        //System.out.println(xx.get(1));
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
            //String imagen = MenuActivity.conversorDirecciones(img.get(index).toString());
            //Picasso.get().load(imagen).into(holder.imgView);

            //imageSwitcher.setImageURI(imagen);

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
                    index = (++index < img.size()) ? index : 0;
                    if(imageSwitcher != null){
                        //imageSwitcher.setImageResource(prueba[index]);
                        Picasso.get().load(MenuActivity.conversorDirecciones(img.get(index).toString()));
                    }
                }
            });
        }
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
