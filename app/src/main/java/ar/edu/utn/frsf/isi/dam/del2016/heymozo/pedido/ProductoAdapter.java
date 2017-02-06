package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

class ProductoAdapter extends ArrayAdapter<Producto> {

    private LayoutInflater inflater;
    private Moneda moneda;

    ProductoAdapter(Context context, Moneda moneda, ArrayList<Producto> productos) {
        super(context, R.layout.item_producto_flat, productos);
        inflater = LayoutInflater.from(context);
        this.moneda = moneda;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row == null){
            row = inflater.inflate(R.layout.item_producto_flat, parent, false);
        }

        ViewHolderProducto holder = (ViewHolderProducto) row.getTag();
        if(holder == null){
            holder = new ViewHolderProducto(row);
            row.setTag(holder);
        }

        String cantidad = this.getItem(position).getCantidad()+"";
        holder.cantidad.setText(cantidad);
        holder.nombreProducto.setText(this.getItem(position).getNombre());
        holder.moneda.setText(moneda.getSimbolo());
        holder.precio.setText(String.format(Locale.getDefault(),"%.2f",this.getItem(position).getPrecio()));

        return(row);
    }

    public Producto getItem(int position){
        return super.getItem(position);
    }
}
