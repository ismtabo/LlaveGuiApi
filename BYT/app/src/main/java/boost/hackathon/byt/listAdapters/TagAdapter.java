package boost.hackathon.byt.listAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import boost.hackathon.byt.R;

/**
 * Created by astaldo on 5/03/15.
 */
public class TagAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> categorias;

    public TagAdapter (Activity activity, ArrayList<String> categorias){

        super();
        this.activity = activity;
        this.categorias = categorias;

    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View vista = inflater.inflate(R.layout.elemento_categoria, null, true);
        TextView tag = (TextView) vista.findViewById(R.id.categoriaCrearProyecto);
        tag.setText(categorias.get(position));

        return vista;
    }
}
