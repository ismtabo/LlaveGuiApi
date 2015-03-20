package boost.hackathon.byt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.R;
import boost.hackathon.byt.listAdapters.TagAdapter;


public class MostrarProyecto extends ActionBarActivity {

    private TextView nombreMostrarProyecto;
    private ImageView imagenProyecto;
    private TextView descripcionMostrarProyecto;
    private ListView listaCategoriaMostrarProyecto;
    private Button usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_proyecto);

        nombreMostrarProyecto = (TextView) findViewById(R.id.nombreMostrarProyecto);
        imagenProyecto = (ImageView) findViewById(R.id.imagenMostrarProyecto);
        descripcionMostrarProyecto = (TextView) findViewById(R.id.descripcionMostrarProyecto);
        listaCategoriaMostrarProyecto = (ListView) findViewById(R.id.listaCategoriasMostrarProyecto);
        usuarios = (Button) findViewById(R.id.usuarios);

        getSupportActionBar().setTitle(Datos.projectData.getName());

        nombreMostrarProyecto.setText(Datos.projectData.getOwner());
        descripcionMostrarProyecto.setText(Datos.projectData.getDescription());

        imagenProyecto.setImageResource(R.drawable.ic_launcher);

        ArrayList<String> asd = new ArrayList<String>();
        asd.add("Hackaton");

        listaCategoriaMostrarProyecto.setAdapter(new TagAdapter(this, asd));

        nombreMostrarProyecto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Datos.mostrarUserFromProject = Datos.projectData.getOwner();
                lanzarMostrarUsuario();
            }
        });

        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarListaUsuarios();
            }
        });

    }

    private void lanzarListaUsuarios(){
        Toast.makeText(this, "Por implementar", Toast.LENGTH_SHORT).show();
    }

    private void lanzarMostrarUsuario(){
        Intent i = new Intent(this,MostrarUsuario.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.pencil) {
            //TODO
            Log.d("ACTIONBAR", "EDITAR PROYECTO");
            Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
