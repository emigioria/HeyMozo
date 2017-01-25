package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

import static ar.edu.utn.frsf.isi.dam.del2016.heymozo.R.color.blue;
import static ar.edu.utn.frsf.isi.dam.del2016.heymozo.R.color.productoSeleccionado;
import static ar.edu.utn.frsf.isi.dam.del2016.heymozo.R.color.white;
import static com.google.android.gms.R.color.cast_expanded_controller_progress_text_color;

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

        holder.cantidad.setText(String.valueOf(this.getItem(position).getCantidad()));
        holder.cantidad.setVisibility(this.getItem(position).getCantidad() != 0 ? View.VISIBLE : View.GONE); //Se muestra el campo cantidad si es mayor que 0
        holder.borrarImagenBtn.setVisibility(this.getItem(position).getCantidad() != 0 ? View.VISIBLE : View.GONE); //Se muestra la opcion de borrar el pedido del producto si la cantidad es mayor que 0

        holder.nombreProducto.setText(this.getItem(position).getNombre());
        holder.moneda.setText(this.getItem(position).getCarta().getMoneda());
        holder.precio.setText(String.valueOf(this.getItem(position).getPrecio()));

        final ViewHolder finalHolder = holder;
        final View finalRow = row;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = getItem(position).getCantidad()+1;
                finalRow.setBackgroundColor(productoSeleccionado);
                getItem(position).setCantidad(cantidad);
                finalHolder.cantidad.setText(String.valueOf(cantidad));
                finalHolder.cantidad.setVisibility(View.VISIBLE);
                finalHolder.borrarImagenBtn.setVisibility(View.VISIBLE);
            }
        });

        holder.borrarImagenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalHolder.cantidad.setVisibility(View.GONE);
                getItem(position).setCantidad(0);
                finalRow.setBackgroundColor(white);
                finalHolder.borrarImagenBtn.setVisibility(View.GONE);
            }
        });
        return(row);
    }

    public Carta.Producto getItem(int position){
        return (Carta.Producto) super.getItem(position);
    }
}
