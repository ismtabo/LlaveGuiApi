package boost.hackathon.byt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sparta on 05/03/15.
 */
public class FragLoginTut extends android.support.v4.app.Fragment {

    private ImageView captura;

    public FragLoginTut() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View aux = inflater.inflate(R.layout.fragment_tutorial, container, false);
        captura= (ImageView)aux.findViewById(R.id.capture);
        captura.setImageResource(R.drawable.uno);
        return aux;
    }

}
