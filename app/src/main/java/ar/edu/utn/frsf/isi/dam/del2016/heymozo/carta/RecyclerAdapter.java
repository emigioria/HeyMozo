package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Producto> productos;
    private Moneda moneda;
    private int posicionConBotones = -1;

    RecyclerAdapter(Moneda moneda, List<Producto> itemList) {
        if (itemList != null) {
            productos = itemList;
        } else {
            productos = new ArrayList<>();
        }
        this.moneda = moneda;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolderProductoRecycler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderProductoRecycler holder = (ViewHolderProductoRecycler) viewHolder;
        final Producto producto = productos.get(position);

        holder.nombreProducto.setText(producto.getNombre());
        holder.precio.setText(String.format(Locale.getDefault(), "%.2f", producto.getPrecio()));
        if (producto.getCantidad() > 0) {
            holder.cantidad.setVisibility(View.VISIBLE); //Se muestra el campo cantidad si es mayor que 0
            holder.cantidad.setText(String.valueOf(producto.getCantidad()));
        } else {
            holder.cantidad.setVisibility(View.GONE);
        }
        holder.moneda.setText(moneda.getSimbolo());

        if (posicionConBotones == position) {
            holder.secondLayout.setVisibility(LinearLayout.VISIBLE);
        }else{
            holder.secondLayout.setVisibility(LinearLayout.GONE);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int posicionConBotonesAnterior = posicionConBotones;
                posicionConBotones = position;
                notifyItemChanged(posicionConBotonesAnterior);
                notifyItemChanged(posicionConBotones);
            }
        });

        holder.agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = producto.getCantidad()+1;
                producto.setCantidad(cantidad);
                notifyItemChanged(position);
            }
        });

        holder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = producto.getCantidad()-1;
                if(cantidad>0) {
                    producto.setCantidad(cantidad);
                }
                else{
                    producto.setCantidad(0);
                }
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

}