package ar.edu.utn.frsf.isi.dam.del2016.heymozo.carta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.del2016.heymozo.modelo.Producto;

import static java.lang.String.valueOf;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Producto> productos;
    private Moneda moneda;
    private LinearLayout secondLayoutAnterior;
    private Integer positionAnterior;
    private HashMap<Integer,Integer> selectedRows;

    RecyclerAdapter(Context context, Moneda moneda, List<Producto> itemList) {
        productos = itemList;
        this.moneda = moneda;
        secondLayoutAnterior = new LinearLayout(context);
        positionAnterior = 0;
        selectedRows = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        final Producto producto = productos.get(position);
        holder.llenarItem(producto);

        holder.moneda.setText(moneda.getSimbolo());

        if(selectedRows.get(position)!=null){
            holder.secondLayout.setVisibility(selectedRows.get(position));
        }else{
            holder.secondLayout.setVisibility(LinearLayout.GONE);
        }

        final RecyclerItemViewHolder finalHolder = holder;
        final View finalRow = holder.view;

        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                secondLayoutAnterior.setVisibility(LinearLayout.GONE);
                selectedRows.put(positionAnterior,LinearLayout.GONE);
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
                Integer cantidad = producto.getCantidad()+1;
                producto.setCantidad(cantidad);
                finalHolder.cantidad.setText(valueOf(cantidad));
                finalHolder.cantidad.setVisibility(View.VISIBLE);
            }
        });

        holder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad = producto.getCantidad()-1;

                if(cantidad>0) {
                    producto.setCantidad(cantidad);
                    finalHolder.cantidad.setText(valueOf(cantidad));
                    finalHolder.cantidad.setVisibility(View.VISIBLE);
                }
                else{
                    producto.setCantidad(0);
                    finalHolder.cantidad.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos == null ? 0 : productos.size();
    }

}