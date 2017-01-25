package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

/**
 * Created by lucas on 24/01/17.
 */

class SeccionAdapter extends ArrayAdapter {

    private LayoutInflater inflater;

    SeccionAdapter(Context context, ArrayList<Carta.Producto> productos) {
        super(context, R.layout.item_carta, productos);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row == null){
            row = inflater.inflate(R.layout.item_carta, parent, false);
        }

        ViewHolder holder = (ViewHolder) row.getTag();
        if(holder == null){
            holder = new ViewHolder(row);
            row.setTag(holder);
        }

        holder.nombreProducto.setText(this.getItem(position).getNombre());
        holder.moneda.setText(this.getItem(position).getCarta().getMoneda());
        holder.precio.setText(String.valueOf(this.getItem(position).getPrecio()));
        return(row);
    }

    public Carta.Producto getItem(int position){
        return (Carta.Producto) super.getItem(position);
    }
}
