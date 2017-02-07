package ar.edu.utn.frsf.isi.dam.del2016.heymozo.restaurantes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Restaurante;

class RestauranteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context contexto;
    private MostrarCartaListener mostrarCartaListener;
    private List<Restaurante> restaurantes;

    RestauranteAdapter(Context contexto, MostrarCartaListener mostrarCartaListener, List<Restaurante> restaurantes) {
        this.contexto = contexto;
        this.mostrarCartaListener = mostrarCartaListener;
        if (restaurantes != null) {
            this.restaurantes = restaurantes;
        } else {
            this.restaurantes = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurante, parent, false);
        return new ViewHolderRestaurante(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderRestaurante holder = (ViewHolderRestaurante) viewHolder;
        final Restaurante restaurante = restaurantes.get(position);

        byte[] bytes = Base64.decode(restaurante.getImagen64(), Base64.DEFAULT);
        Bitmap bMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imageViewFotoRestaurante.setImageBitmap(bMap);

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
        holder.ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Cambiar por actividad para ver calificaciones en el futurooooo
                Toast.makeText(contexto, "Caificaciones: Muy buenas!", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        holder.buttonVerCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCartaListener.mostrarCarta(restaurante);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }
}
