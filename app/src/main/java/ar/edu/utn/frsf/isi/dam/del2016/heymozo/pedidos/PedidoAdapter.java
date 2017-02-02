package ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedidos;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Pedido;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.pedido.PedidoActivity;

class PedidoAdapter extends ArrayAdapter<Pedido> {

    private LayoutInflater inflater;

    PedidoAdapter(Context context, ArrayList<Pedido> pedidos) {
        super(context, R.layout.item_producto, pedidos);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            row = inflater.inflate(R.layout.item_pedido, parent, false);
        }

        ViewHolderPedido holder = (ViewHolderPedido) row.getTag();
        if (holder == null) {
            holder = new ViewHolderPedido(row);
            row.setTag(holder);
        }
        final Pedido pedido = this.getItem(position);

        String numPedido = String.format(Locale.getDefault(), getContext().getString(R.string.pedidoNum), getCount() - position);
        holder.textviewPedido.setText(numPedido);
        holder.textviewMoneda.setText(pedido.getMoneda().getSimbolo());
        holder.textviewFecha.setText(DateFormat.getDateInstance().format(new Date(pedido.getFechaPedido())));
        holder.textviewEstado.setText(pedido.getEstado());
        holder.textviewTotal.setText(String.format(Locale.getDefault(), "%.2f", pedido.getTotal()));
        holder.textviewNombreRestaurante.setText(pedido.getRestaurante().getNombre());

        if (pedido.getCalificacion() != null) {
            holder.buttonGracias.setVisibility(View.VISIBLE);
            holder.buttonEvaluarExp.setVisibility(View.GONE);
        } else {
            holder.buttonGracias.setVisibility(View.GONE);
            holder.buttonEvaluarExp.setVisibility(View.VISIBLE);
        }

        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PedidoActivity.class);
                i.putExtra("pedido", pedido.toJSONObject());
                getContext().startActivity(i);
            }
        });

        holder.buttonEvaluarExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedido.setCalificacion("Muy buena");
                notifyDataSetChanged();
            }
        });

        return (row);
    }

    public Pedido getItem(int position) {
        return super.getItem(position);
    }
}
