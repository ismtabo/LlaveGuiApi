package boost.hackathon.byt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import boost.hackathon.byt.activities.Login;

/**
 * Created by Sparta on 05/03/15.
 */
public class FragUserPerfil  extends android.support.v4.app.Fragment {

    private ImageView captura;
    private Button salir;
    private Context context;

        public FragUserPerfil() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View aux = inflater.inflate(R.layout.fragment_tutorial_final, container, false);
            captura= (ImageView)aux.findViewById(R.id.capture);
            salir = (Button) aux.findViewById(R.id.salir);
            context = aux.getContext();
            captura.setImageResource(R.drawable.cuatro);
            salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lanzarNuevo();
                }
            });
            return aux;
        }

    private void lanzarNuevo(){
        Intent i = new Intent(context, Login.class);
        startActivity(i);
    }

    }

