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

    private final Boolean noHacerPedidos;
    private List<Producto> productos;
    private Moneda moneda;
    private int posicionConBotones = -1;
    private static final int ALTURA_EXTENDIDO = 200;
    private static final int ALTURA_CONTRAIDO = 75;

    RecyclerAdapter(Moneda moneda, Boolean noHacerPedidos, List<Producto> itemList) {
        if (itemList != null) {
            productos = itemList;
        } else {
            productos = new ArrayList<>();
        }
        this.moneda = moneda;
        this.noHacerPedidos = noHacerPedidos;
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

        ViewGroup.LayoutParams params = holder.imagenProducto.getLayoutParams();
        final float scale = holder.view.getContext().getResources().getDisplayMetrics().density;

        if (posicionConBotones == position) {
            if (!noHacerPedidos) {
                holder.secondLayout.setVisibility(LinearLayout.VISIBLE);
            } else {
                holder.secondLayout.setVisibility(LinearLayout.GONE);
            }

            params.height = (int) (ALTURA_EXTENDIDO * scale + 0.5f);
        } else {
            holder.secondLayout.setVisibility(LinearLayout.GONE);

            params.height = (int) (ALTURA_CONTRAIDO * scale + 0.5f);
        }

        holder.imagenProducto.setLayoutParams(params);

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
                Integer cantidad = producto.getCantidad() + 1;
                producto.setCantidad(cantidad);
                notifyItemChanged(position);
            }
        });

        holder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = producto.getCantidad() - 1;
                producto.setCantidad((cantidad > 0) ? (cantidad) : (0));
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

}