package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

import static java.lang.String.valueOf;

/**
 * Created by lucas on 24/01/17.
 */

class SeccionAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private LinearLayout secondLayoutAnterior;

    SeccionAdapter(Context context, ArrayList<Carta.Producto> productos) {
        super(context, R.layout.item_carta, productos);
        inflater = LayoutInflater.from(context);
        secondLayoutAnterior = new LinearLayout(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row == null){
            row = inflater.inflate(R.layout.item_carta, parent, false);
        }

        ViewHolder holder = (ViewHolder) row.getTag();
        if(holder == null){
            holder = new ViewHolder(row);
            row.setTag(holder);
        }

        holder.cantidad.setText(valueOf(this.getItem(position).getCantidad()));
        holder.cantidad.setVisibility(this.getItem(position).getCantidad() != 0 ? View.VISIBLE : View.GONE); //Se muestra el campo cantidad si es mayor que 0

        holder.nombreProducto.setText(this.getItem(position).getNombre());
        holder.moneda.setText(this.getItem(position).getCarta().getMoneda());
        holder.precio.setText(valueOf(this.getItem(position).getPrecio()));

        final ViewHolder finalHolder = holder;
        final View finalRow = row;
        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                secondLayoutAnterior.setVisibility(LinearLayout.GONE);
                secondLayoutAnterior = finalHolder.secondLayout;

                finalHolder.secondLayout.setVisibility(LinearLayout.VISIBLE);
            }
        });

        holder.agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = getItem(position).getCantidad()+1;
                getItem(position).setCantidad(cantidad);
                finalHolder.cantidad.setText(valueOf(cantidad));
                finalHolder.cantidad.setVisibility(View.VISIBLE);
                finalRow.setBackgroundColor(0x66FF7C00);
            }
        });

        holder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = getItem(position).getCantidad()-1;

                if(cantidad>0) {
                    getItem(position).setCantidad(cantidad);
                    finalHolder.cantidad.setText(valueOf(cantidad));
                    finalHolder.cantidad.setVisibility(View.VISIBLE);
                    finalRow.setBackgroundColor(0x66FF7C00);
                }
                else{
                    getItem(position).setCantidad(0);
                    finalHolder.cantidad.setVisibility(View.GONE);
                    finalRow.setBackgroundColor(0x00FFFFFF);
                }
            }
        });

        return(row);
    }

    public Carta.Producto getItem(int position){
        return (Carta.Producto) super.getItem(position);
    }
}
