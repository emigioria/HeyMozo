package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.Producto;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.producto.ViewHolderProducto;

import static java.lang.String.valueOf;

/**
 * Created by lucas on 24/01/17.
 */

class ProductoAdapter extends ArrayAdapter<Producto> {

    private LayoutInflater inflater;
    private LinearLayout secondLayoutAnterior;
    private Integer positionAnterior;
    private HashMap<Integer,Integer> selectedRows;

    ProductoAdapter(Context context, ArrayList<Producto> productos) {
        super(context, R.layout.item_carta, productos);
        inflater = LayoutInflater.from(context);
        secondLayoutAnterior = new LinearLayout(context);
        positionAnterior = 0;
        selectedRows = new HashMap();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row == null){
            row = inflater.inflate(R.layout.item_carta, parent, false);
        }

        ViewHolderProducto holder = (ViewHolderProducto) row.getTag();
        if(holder == null){
            holder = new ViewHolderProducto(row);
            row.setTag(holder);
        }

        Integer cantidad = this.getItem(position).getCantidad();
        if(cantidad>0){
            holder.cantidad.setVisibility(View.VISIBLE); //Se muestra el campo cantidad si es mayor que 0
            row.setBackgroundColor(0x66FF7C00);
        }else{
            holder.cantidad.setVisibility(View.GONE);
            row.setBackgroundColor(0x00FFFFFF);
        }

        holder.cantidad.setText(valueOf(cantidad));
        holder.nombreProducto.setText(this.getItem(position).getNombre());
        holder.moneda.setText(this.getItem(position).getMoneda());
        holder.precio.setText(valueOf(this.getItem(position).getPrecio()));

        if(selectedRows.get(position)!=null){
            holder.secondLayout.setVisibility(selectedRows.get(position));
        }else{
            holder.secondLayout.setVisibility(LinearLayout.GONE);
        }


        final ViewHolderProducto finalHolder = holder;
        final View finalRow = row;

        Log.v("Position:",positionAnterior+" - "+position);
        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                secondLayoutAnterior.setVisibility(LinearLayout.GONE);
                selectedRows.put(positionAnterior,LinearLayout.GONE);
                Log.v("ClickPosition:",positionAnterior+" - "+position);
                secondLayoutAnterior = finalHolder.secondLayout;
                positionAnterior = position;

                finalHolder.secondLayout.setVisibility(LinearLayout.VISIBLE);
                selectedRows.put(position,LinearLayout.VISIBLE);

                notifyDataSetChanged();
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

    public Producto getItem(int position){
        return super.getItem(position);
    }
}
