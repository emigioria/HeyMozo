package ar.edu.utn.frsf.isi.dam.del2016.heymozo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomGrid extends BaseAdapter{
	private Context mContext;
	private final int[] text;
    private final int[] Imageid;

	public CustomGrid(Context c, int[] text,int[] Imageid ) {
            mContext = c;
            this.Imageid = Imageid;
            this.text = text;
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
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        if (convertView == null) {
			grid = inflater.inflate(R.layout.item_main, null);
	        TextView textViewGrid = (TextView) grid.findViewById(R.id.textViewGrid);
            ImageView imageViewGrid = (ImageView)grid.findViewById(R.id.imageViewGrid);
            textViewGrid.setText(text[position]);
            imageViewGrid.setImageResource(Imageid[position]);
        } else {
            grid = convertView;
        }

        return grid;
    }
}