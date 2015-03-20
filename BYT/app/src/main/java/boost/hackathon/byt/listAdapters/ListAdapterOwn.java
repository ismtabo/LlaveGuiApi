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
public class ListAdapterOwn extends ArrayAdapter {

    private Context context;
    private TextView name;
    private ImageView image;

    private ArrayList<Project> list;

    public ListAdapterOwn(Context context, ArrayList<Project> list){
        super(context, R.layout.project_list_elements_own, list);
        this.list = list;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.project_list_elements_own, null);

        name = (TextView)convertView.findViewById(R.id.project_name);
        image = (ImageView)convertView.findViewById(R.id.project_image);
        
        name.setText(list.get(position).getName().toString());
        image.setImageResource(R.drawable.ic_launcher);
        return convertView;
    }
}