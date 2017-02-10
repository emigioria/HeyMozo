package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.PedidoActivity;

class PedidoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Pedido> pedidos;

    PedidoAdapter(Context context, List<Pedido> pedidos) {
        this.context = context;
        if (pedidos != null) {
            this.pedidos = pedidos;
        } else {
            this.pedidos = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolderPedido(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolderPedido holder = (ViewHolderPedido) viewHolder;
        final Pedido pedido = pedidos.get(position);

        String numPedido = String.format(Locale.getDefault(), context.getString(R.string.pedidoNum), getItemCount() - position);
        holder.textViewPedido.setText(numPedido);
        holder.textViewMoneda.setText(pedido.getRestaurante().getMoneda().getSimbolo());
        holder.textViewFecha.setText(DateFormat.getDateInstance().format(new Date(pedido.getFechaPedido())));
        holder.textViewEstado.setText(pedido.getEstado());
        holder.textViewTotal.setText(String.format(Locale.getDefault(), "%.2f", pedido.getTotal()));
        holder.textViewNombreRestaurante.setText(pedido.getRestaurante().getNombre());

        holder.buttonEvaluarExp.setVisibility(View.GONE);
        holder.buttonGracias.setVisibility(View.GONE);
        if (pedido.getCalificacion() != null) {
            holder.buttonGracias.setVisibility(View.VISIBLE);
        } else {
            if (pedido.getEstado().equals(context.getString(R.string.estado_pedido_terminado))) {
                holder.buttonEvaluarExp.setVisibility(View.VISIBLE);
            }
        }

        holder.row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(context, PedidoActivity.class);
                i.putExtra("pedido", pedido.toJSONObject());
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, new Pair<>(holder.row, context.getString(R.string.transition_card_pedido)));
                context.startActivity(i, options.toBundle());
            }
        });

        holder.buttonEvaluarExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedido.setCalificacion("Muy buena");
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }
}
