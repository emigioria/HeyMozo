package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.imagenes.CargarImagenListener;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.imagenes.ImageLoader;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CargarImagenListener {

    private final Boolean noHacerPedidos;
    private Context context;
    private List<Producto> productos;
    private Moneda moneda;
    private int posicionConBotones = -1;
    private static final int ALTURA_EXTENDIDO = 200;
    private static final int ALTURA_CONTRAIDO = 100;

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
        context = parent.getContext();
        CardView view = (CardView) LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolderProductoRecycler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderProductoRecycler holder = (ViewHolderProductoRecycler) viewHolder;
        ImageLoader cargadorImagenes = new ImageLoader(context);
        final Producto producto = productos.get(position);

        if(producto.getImagen() != null && producto.getImagen().getImagen() != null) {
            holder.imagenProducto.setImageBitmap(producto.getImagen().getImagen());
        }
        else if (producto.getImagen() != null && producto.getImagen().getImagenId() != null){
            String url = "http://"+context.getString(R.string.ip_server)+":"+context.getString(R.string.port_server_db)+"/imagenes/"+producto.getImagen().getImagenId(); //TODO ponerlo en otro lado
            cargadorImagenes.cargarImagen(url, producto, position, this);
        }

        holder.nombreProducto.setText(producto.getNombre());
        holder.precio.setText(String.format(Locale.getDefault(), "%.2f", producto.getPrecio()));

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

        holder.moneda.setText(moneda.getSimbolo());

        final ViewGroup.LayoutParams paramsImagenProducto = holder.imagenProducto.getLayoutParams();
        final float scale = holder.view.getContext().getResources().getDisplayMetrics().density;

        //Muestra u oculta los botones Agregar y Quitar si corresponde a la posicion en que se registró el click
        if (posicionConBotones == position) {
            if (!noHacerPedidos) {
                holder.secondLayout.setVisibility(LinearLayout.VISIBLE);
            } else {
                holder.secondLayout.setVisibility(LinearLayout.GONE);
            }

            params.height = (int) (ALTURA_EXTENDIDO * scale + 0.5f);
            holder.secondLayout.setVisibility(LinearLayout.VISIBLE);
            paramsImagenProducto.height = (int) (ALTURA_EXTENDIDO * scale + 0.5f);
        } else {
            holder.secondLayout.setVisibility(LinearLayout.GONE);
            paramsImagenProducto.height = (int) (ALTURA_CONTRAIDO * scale + 0.5f);
        }
        holder.imagenProducto.setLayoutParams(paramsImagenProducto);

        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Comprueba si el click se realizó por primera o segunda vez

                //Si se realiza sobre un item por segunda vez consecutiva, se ocultan los botones
                if((posicionConBotones == position)&&(holder.secondLayout.getVisibility()==LinearLayout.VISIBLE)){
                    holder.secondLayout.setVisibility(LinearLayout.GONE);
                    paramsImagenProducto.height = (int) (ALTURA_CONTRAIDO * scale + 0.5f);
                    holder.imagenProducto.setLayoutParams(paramsImagenProducto);
                    posicionConBotones = -1;
                }else {
                    //Sino se muestran los botones del item clickeado y se ocultan los del item anterior
                    int posicionConBotonesAnterior = posicionConBotones;
                    posicionConBotones = position;
                    notifyItemChanged(posicionConBotonesAnterior);
                    notifyItemChanged(posicionConBotones);
                }
            }
        });

        //TODO ver como mostrar mas detalles
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(context,DetalleProductoActivity.class);
                i.putExtra("producto", producto);
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

    public void imagenCargada(Producto producto, int position, int status) {
        notifyItemChanged(position);
    }
}