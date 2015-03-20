package boost.hackathon.byt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.Project;
import boost.hackathon.byt.R;
import boost.hackathon.byt.listAdapters.ListAdapterSeleccionarProyecto;


public class SeleccionarProyecto extends ActionBarActivity {

    private ListView listaSeleccionarProyectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_proyecto);

        listaSeleccionarProyectos = (ListView) findViewById(R.id.listaSeleccionarProyectos);

        ArrayList<Project> arrayList = new ArrayList<>();


        JSONObject aux;
        Project project;

        try {
            for (int i = 0; i < Datos.waxaOwnProjects.size(); i++) {
                aux = Datos.waxaOwnProjects.get(i);
                project = new Project(aux.getString("nombre"), aux.getString("owner"));
                arrayList.add(project);
            }
            Log.d("WAXATAG2", "primer bucle");
            for (int i = 0; i < Datos.otherProjects.size(); i++) {
                aux = Datos.otherProjects.get(i);
                project = new Project(aux.getString("nombre"), aux.getString("owner"));
                arrayList.add(project);
            }
        }catch (Exception e) {
            Log.d("WAXATAG2", "problemas al cargar la lista");
        }

        listaSeleccionarProyectos.setAdapter(new ListAdapterSeleccionarProyecto(this, arrayList));



        listaSeleccionarProyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanzarMostrarProyecto(position);
            }
        });



    }

    private void lanzarMostrarProyecto(int posi){
        Intent i = new Intent(this, MostrarProyecto.class);
        startActivity(i);
    }

}
