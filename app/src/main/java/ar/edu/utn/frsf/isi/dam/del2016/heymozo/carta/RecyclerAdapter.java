package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.ProductoDetallado;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Boolean noHacerPedidos;
    private Context context;
    private List<ProductoDetallado> productos;
    private Moneda moneda;
    private int posicionConBotones = -1;
    private static final int ALTURA_EXTENDIDO = 200;
    private static final int ALTURA_CONTRAIDO = 100;

    RecyclerAdapter(Moneda moneda, Boolean noHacerPedidos, List<ProductoDetallado> itemList) {
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
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolderProductoRecycler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderProductoRecycler holder = (ViewHolderProductoRecycler) viewHolder;
        final ProductoDetallado producto = productos.get(position);

        if (producto.getImagen() != null && producto.getImagen().getUrlImagen(context) != null) {
            holder.imagenProducto.setVisibility(View.VISIBLE);
            Glide.with(context).load(producto.getImagen().getUrlImagen(context))
                    .error(context.getDrawable(R.drawable.ic_broken_image_black_24dp))
                    .placeholder(context.getDrawable(R.drawable.ic_loading))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imagenProducto);
        } else {
            holder.imagenProducto.setVisibility(View.GONE);
        }

        holder.moneda.setText(moneda.getSimbolo());
        holder.precio.setText(String.format(Locale.getDefault(), "%.2f", producto.getPrecio()));

        holder.nombreProducto.setText(producto.getNombre());
        LinearLayout.LayoutParams paramsNombreProducto = (LinearLayout.LayoutParams) holder.nombreProducto.getLayoutParams();
        if (producto.getCantidad() > 0) {
            //Se muestra el campo cantidad si es mayor que 0
            holder.cantidad.setVisibility(View.VISIBLE);
            holder.cantidad.setText(String.valueOf(producto.getCantidad()));

            //Se modifica la distancia entre el campo cantidad y nombre del producto
            paramsNombreProducto.leftMargin = 4;
        } else {
            holder.cantidad.setVisibility(View.GONE);

            paramsNombreProducto.leftMargin = 0;
        }
        holder.nombreProducto.setLayoutParams(paramsNombreProducto);

        ViewGroup.LayoutParams paramsImagenProducto = holder.imagenProducto.getLayoutParams();
        float scale = holder.view.getContext().getResources().getDisplayMetrics().density;
        //Muestra u oculta los botones Agregar y Quitar si corresponde a la posicion en que se registró el click
        if (posicionConBotones == position) {
            if (!noHacerPedidos) {
                holder.secondLayout.setVisibility(LinearLayout.VISIBLE);
            } else {
                holder.secondLayout.setVisibility(LinearLayout.GONE);
            }

            paramsImagenProducto.height = (int) (ALTURA_EXTENDIDO * scale + 0.5f);
        } else {
            holder.secondLayout.setVisibility(LinearLayout.GONE);
            paramsImagenProducto.height = (int) (ALTURA_CONTRAIDO * scale + 0.5f);
        }
        holder.imagenProducto.setLayoutParams(paramsImagenProducto);

        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int posicionConBotonesAnterior = posicionConBotones;
                //Comprueba si el click se realizó por primera o segunda vez
                //Si se realiza sobre un item por segunda vez consecutiva, se ocultan los botones
                if (posicionConBotones == position) {
                    posicionConBotones = -1;
                } else {
                    //Sino se muestran los botones del item clickeado y se ocultan los del item anterior
                    posicionConBotones = position;
                    notifyItemChanged(posicionConBotones);
                }
                notifyItemChanged(posicionConBotonesAnterior);
            }
        });

        //TODO ver como mostrar mas detalles
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(context, DetalleProductoActivity.class);
                i.putExtra("producto", producto.toJSONObject());
                context.startActivity(i);
                return true;
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