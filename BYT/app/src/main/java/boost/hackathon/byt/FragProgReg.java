package boost.hackathon.byt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import boost.hackathon.byt.R;

/**
 * Created by Sparta on 05/03/15.
 */
public class FragProgReg extends android.support.v4.app.Fragment{

    private ImageView captura;

    public FragProgReg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View aux = inflater.inflate(R.layout.fragment_tutorial, container, false);
        captura= (ImageView)aux.findViewById(R.id.capture);
        captura.setImageResource(R.drawable.tres);
        return aux;
    }

}
