package boost.hackathon.byt.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import boost.hackathon.byt.Project;
import boost.hackathon.byt.R;

/**
 * Created by waxa2 on 28/12/14.
 */
public class ListAdapterSeleccionarProyecto extends ArrayAdapter {

    private Context context;
    private TextView name;
    private TextView userName;
    private ImageView image;
    private ImageView image2;

    private ArrayList<Project> list;

    public ListAdapterSeleccionarProyecto(Context context, ArrayList<Project> list){
        super(context, R.layout.elemento_proyecto, list);
        this.list = list;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.elemento_proyecto, null);

        name = (TextView)convertView.findViewById(R.id.nombreElementoProyecto);
        userName = (TextView)convertView.findViewById(R.id.creadorElementoProyecto);
        image = (ImageView)convertView.findViewById(R.id.terminadoElementoProyecto);
        image2 = (ImageView)convertView.findViewById(R.id.imagenElementoProyecto);
        
        name.setText(list.get(position).getName().toString());
        userName.setText(list.get(position).getOwner().toString());
        image.setImageResource(R.drawable.ic_launcher);
        image2.setImageResource(R.drawable.ic_launcher);

        return convertView;
    }
}