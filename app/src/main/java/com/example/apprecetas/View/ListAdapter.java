package com.example.apprecetas.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprecetas.R;

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
        //private View view;
        //private ImageView imgView;
        private TextView textView1;
        private TextView textView2;

        public ViewHolder(View v) {
            super(v);
            //view = v;
            textView1 = v.findViewById(R.id.item_text1);
            textView2 = v.findViewById(R.id.item_text2);
            //imgView = v.findViewById(R.id.item_img);
        }
    }


   /* public ListAdapter(String[] items) {
        this.items = items;
    } */

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_list, parent,false);
        return new ViewHolder(v);
        // create a new view
        /* View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list, parent, false);
        return new ViewHolder(v); */
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receta receta = recetas[position];

        holder.textView1.setText(receta.getNombre());
        System.out.println(receta.getNombre());
        holder.textView2.setText(receta.getTipo());
        System.out.println(receta.getTipo());
        /*final Receta receta = recetas[position];
        //Picasso.get().load(receta.getImagenes()).into(holder.imgView);
        holder.textView1.setText(receta.getNombre());
        final String s = "Tipo: " + receta.getTipo();
        holder.textView2.setText(s);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                intent.putExtra("nombre", receta.getNombre());
                //intent.putExtra("image", receta.getImgURL());
                //intent.putExtra("instruccion", "Instrucciones: " + receta.getInstruccion());
                intent.putExtra("tipo", s);
                v.getContext().startActivity(intent);
            }
        }); */
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //return recetas.length;
        return recetas == null ?  0 : recetas.length;
    }

}