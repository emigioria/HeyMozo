package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.ProductoDetallado;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ALTURA_EXTENDIDO = 200;
    private static final int ALTURA_CONTRAIDO = 100;
    private final Boolean noHacerPedidos;
    private Context context;
    private List<ProductoDetallado> productos;
    private Moneda moneda;
    private int posicionConBotones = -1;
    private SharedPreferences preferenciasAyuda;

    RecyclerAdapter(Context context, Moneda moneda, Boolean noHacerPedidos, List<ProductoDetallado> itemList) {
        this.context = context;
        if (itemList != null) {
            productos = itemList;
        } else {
            productos = new ArrayList<>();
        }
        this.moneda = moneda;
        this.noHacerPedidos = noHacerPedidos;
        preferenciasAyuda = context.getSharedPreferences("ayuda",Context.MODE_PRIVATE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolderProductoRecycler(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderProductoRecycler holder = (ViewHolderProductoRecycler) viewHolder;
        final ProductoDetallado producto = productos.get(position);
        float scale = holder.view.getContext().getResources().getDisplayMetrics().density;

        holder.moneda.setText(moneda.getSimbolo());
        holder.precio.setText(String.format(Locale.getDefault(), "%.2f", producto.getPrecio()));
        holder.mensajeAyudaInformacion.setVisibility(View.GONE);

        if (producto.getDescripcion() != null) {
            holder.descripcionCorta.setVisibility(View.VISIBLE);
            holder.descripcionCorta.setText(producto.getDescripcion());
        } else {
            holder.descripcionCorta.setVisibility(View.GONE);
        }

        if (producto.getDescripcionLarga() == null) {
            holder.imagenMasInformacion.setVisibility(View.GONE);
        } else {
            holder.imagenMasInformacion.setVisibility(View.VISIBLE);
        }

        holder.nombreProducto.setText(producto.getNombre());
        LinearLayout.LayoutParams paramsNombreProducto = (LinearLayout.LayoutParams) holder.nombreProducto.getLayoutParams();
        if (producto.getCantidad() > 0) {
            //Se muestra el campo cantidad si es mayor que 0
            holder.cantidad.setVisibility(View.VISIBLE);
            holder.cantidad.setText(String.valueOf(producto.getCantidad()));

            //Se modifica la distancia entre el campo cantidad y nombre del producto
            paramsNombreProducto.leftMargin = (int) (4 * scale + 0.5f);
        } else {
            holder.cantidad.setVisibility(View.GONE);

            paramsNombreProducto.leftMargin = 0;
        }
        holder.nombreProducto.setLayoutParams(paramsNombreProducto);

        ViewGroup.LayoutParams paramsImagenProducto = holder.imagenProducto.getLayoutParams();
        //Muestra u oculta los botones Agregar y Quitar si corresponde a la posicion en que se registró el click
        if (posicionConBotones == position) {
            if (!noHacerPedidos) {
                holder.secondLayout.setVisibility(LinearLayout.VISIBLE);
            } else {
                holder.secondLayout.setVisibility(LinearLayout.GONE);
            }

            //Muestra la imagen de producto de forma expandida
            paramsImagenProducto.height = (int) (ALTURA_EXTENDIDO * scale + 0.5f);

            //Muestra el mensaje de ayuda si corresponde
            if(producto.getDescripcionLarga() != null && preferenciasAyuda.getBoolean(context.getString(R.string.key_ayuda_item_mas_informacion),true)){
                holder.mensajeAyudaInformacion.setVisibility(View.VISIBLE);
            }
        } else {
            //Muestra la imagen de producto de forma contraida
            holder.secondLayout.setVisibility(LinearLayout.GONE);
            paramsImagenProducto.height = (int) (ALTURA_CONTRAIDO * scale + 0.5f);
        }
        holder.imagenProducto.setLayoutParams(paramsImagenProducto);

        //Carga la foto del producto
        if (producto.getImagen() != null && producto.getImagen().getUrlImagen(context) != null) {
            holder.imagenProducto.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(context).load(producto.getImagen().getUrlImagen(context))
                    .error(context.getDrawable(R.drawable.ic_broken_image_black_24dp))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                                  @Override
                                  public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                      holder.progressBar.setVisibility(View.GONE);
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                      holder.progressBar.setVisibility(View.GONE);
                                      return false;
                                  }
                              }
                    )
                    .into(new ImageViewTarget<GlideDrawable>(holder.imagenProducto) { //Necesario para dibujar bien el CenterCrop
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            holder.imagenProducto.setImageDrawable(resource);
                        }
                    });
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.imagenProducto.setVisibility(View.GONE);
        }

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

        holder.imagenMasInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Modificamos las preferencias para que el mensaje de ayuda no se muestre más
                SharedPreferences.Editor editorPreferencias = preferenciasAyuda.edit();
                editorPreferencias.putBoolean(context.getString(R.string.key_ayuda_item_mas_informacion),false);
                editorPreferencias.apply();

                Intent i = new Intent(context, DetalleProductoActivity.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, new Pair<View, String>(holder.imagenProducto, context.getString(R.string.transition_photo_producto)));
                i.putExtra("producto", producto.toJSONObject());
                context.startActivity(i, options.toBundle());
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