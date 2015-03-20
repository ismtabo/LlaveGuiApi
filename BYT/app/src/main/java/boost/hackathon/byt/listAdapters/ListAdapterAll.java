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
public class ListAdapterAll extends ArrayAdapter {

    private Context context;
    private TextView name;
    private TextView userName;
    private ImageView image;

    private ArrayList<Project> list;

    public ListAdapterAll(Context context, ArrayList<Project> list){
        super(context, R.layout.project_list_elements_all, list);
        this.list = list;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    //TODO
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.project_list_elements_all, null);

        name = (TextView)convertView.findViewById(R.id.project_name);
        userName = (TextView)convertView.findViewById(R.id.project_owner_name);
        image = (ImageView)convertView.findViewById(R.id.project_image);
        
        name.setText(list.get(position).getName().toString());
        userName.setText(list.get(position).getOwner().toString());
        image.setImageResource(R.drawable.ic_launcher);
        return convertView;
    }
}