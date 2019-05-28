package com.example.apprecetas.View;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apprecetas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Receta[] recetas;

    public ListAdapter(Receta[] myRecetas) {
        recetas = myRecetas;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private ImageView imgView;
        private TextView textView1;
        private TextView textView2;

        public ViewHolder(View v) {
            super(v);
            view = v;
            textView1 = v.findViewById(R.id.item_text1);
            textView2 = v.findViewById(R.id.item_text2);
            imgView = v.findViewById(R.id.item_img);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_list, parent,false);
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Receta receta = recetas[position];
        holder.textView1.setText(receta.getNombre());
        holder.textView2.setText(receta.getTipo());
        String imagen = MenuActivity.conversorDirecciones(receta.getImagenes().get(0).toString());
        Picasso.get().load(imagen).into(holder.imgView);
        //Si selecciona una receta:
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                intent.putExtra("nombre", receta.getNombre());
                intent.putExtra("tipo", "Tipo: "+ receta.getTipo());
                intent.putExtra("ingredientes", receta.getIngredientes().toString());
                intent.putExtra("instrucciones", receta.getPasos().toString());
                intent.putExtra("imagenes",(ArrayList)receta.getImagenes());
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recetas == null ?  0 : recetas.length;
    }

}