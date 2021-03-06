package ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

class RestauranteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context contexto;
    private MostrarCartaListener mostrarCartaListener;
    private List<Restaurante> restaurantes;

    RestauranteAdapter(MostrarCartaListener mostrarCartaListener, List<Restaurante> restaurantes) {
        this.mostrarCartaListener = mostrarCartaListener;
        if (restaurantes != null) {
            this.restaurantes = restaurantes;
        } else {
            this.restaurantes = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        contexto = parent.getContext();
        View view = LayoutInflater.from(contexto).inflate(R.layout.item_restaurante, parent, false);
        return new ViewHolderRestaurante(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderRestaurante holder = (ViewHolderRestaurante) viewHolder;
        final Restaurante restaurante = restaurantes.get(position);

        if (restaurante.getImagen() != null && restaurante.getImagen().getUrlImagen(contexto) != null) {
            holder.imageViewFotoRestaurante.setVisibility(View.VISIBLE);
            Glide.with(contexto).load(restaurante.getImagen().getUrlImagen(contexto))
                    .error(contexto.getDrawable(R.drawable.ic_broken_image_black_24dp))
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
                    .into(holder.imageViewFotoRestaurante);
        } else {
            holder.imageViewFotoRestaurante.setVisibility(View.GONE);
        }

        if (restaurante.getNombre() != null) {
            holder.textViewNombreRestaurante.setText(restaurante.getNombre());
        } else {
            holder.textViewNombreRestaurante.setVisibility(View.GONE);
        }
        if (restaurante.getDireccion() != null) {
            holder.textViewDireccion.setText(restaurante.getDireccion());
        } else {
            holder.textViewDireccion.setVisibility(View.GONE);
        }
        if (restaurante.getTelefono() != null) {
            holder.textViewTelefono.setText(restaurante.getTelefono());
        } else {
            holder.textViewTelefono.setVisibility(View.GONE);
        }
        if (restaurante.getPagina() != null) {
            holder.textViewPaginaWeb.setText(restaurante.getPagina());
        } else {
            holder.textViewPaginaWeb.setVisibility(View.GONE);
        }
        if (restaurante.getRating() != null) {
            holder.ratingBar.setRating(restaurante.getRating());
            holder.ratingBar.setVisibility(View.VISIBLE);
        } else {
            holder.ratingBar.setVisibility(View.GONE);
        }
        holder.buttonVerCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCartaListener.mostrarCarta(restaurante, holder.itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }
}
