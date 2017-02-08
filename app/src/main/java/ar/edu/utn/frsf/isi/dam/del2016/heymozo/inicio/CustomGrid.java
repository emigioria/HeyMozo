package ar.edu.utn.frsf.isi.dam.del2016.heymozo.inicio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.del2016.heymozo.R;

class CustomGrid extends BaseAdapter {

    private final int[] text;
    private final int[] Imageid;
    private LayoutInflater inflater;

    CustomGrid(Context c, int[] text, int[] Imageid) {
        this.Imageid = Imageid;
        this.text = text;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_main, parent, false);
        }
        TextView textViewGrid = (TextView) convertView.findViewById(R.id.textViewGrid);
        ImageView imageViewGrid = (ImageView) convertView.findViewById(R.id.imageViewGrid);
        textViewGrid.setText(text[position]);
        imageViewGrid.setImageResource(Imageid[position]);

        return convertView;
    }
}