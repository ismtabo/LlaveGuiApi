package boost.hackathon.byt.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.R;
import boost.hackathon.byt.listAdapters.TagAdapter;
import boost.hackathon.byt.peticiones.Async;

/**
 * Created by Sparta on 05/03/15.
 */
public class CrearProyecto extends ActionBarActivity{

    private Context context;
    private TagAdapter adapter;

    private JSONObject res;

    private EditText nombre;
    private EditText description;
    private EditText tags;
    private ArrayList <String> tagslist;
    private Button create;
    private ImageButton add;
    private ImageView perfil;
    private ListView lista;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proyecto);

        tagslist = new ArrayList<String>();

        context = getApplicationContext();

        nombre = (EditText) findViewById(R.id.nombreCrearProyecto);
        description = (EditText) findViewById(R.id.descripcionCrearProyecto);
        tags = (EditText) findViewById(R.id.categoriaCrearProyecto);
        create = (Button) findViewById(R.id.botonCrearProyecto);
        perfil = (ImageView)findViewById(R.id.imagenCrearProyecto);
        add = (ImageButton) findViewById(R.id.aceptarCategoriaCrearProyecto);
        lista = (ListView) findViewById(R.id.listaCategoriasCrearProyecto);

        adapter = new TagAdapter(this, tagslist);

        lista.setAdapter(adapter);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                init();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("CLICK", "Registro pulsación.");
                if(!tags.getText().equals("")) {

                    tagslist.add(tags.getText().toString());
                    adapter.notifyDataSetChanged();
                    tags.setText("");

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_only_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.perfil) {
            //TODO
            Log.d("ACTIONBAR", "LANZANDO PERFIL");
            startActivity(new Intent(this, MostrarUsuario.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void init (){

        //NuevoProAsync r = new NuevoProAsync(nombre.getText().toString(), description.getText().toString());
        NuevoProAsync r = new NuevoProAsync();
        r.execute();

    }

    private void terminar(){
        finish();
    }


    public class NuevoProAsync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                if (checkNetworkConnection()){
                    res = realizarPeticion();
                }else{
                    Log.d(Datos.TAG, "no hay conexion2 a internet");
                }
            }catch (Exception e){
                Log.d(Datos.TAG, "NO SE QUE COÑO PASA " + e.getMessage());
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                try {
                    if (res.getBoolean("resultado")) {

                        terminar();
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(context, "No ok", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.d(Datos.TAG, "error de JSON : " + e.getMessage());
                }
            }
            else
                Toast.makeText(context,"Error de peticion", Toast.LENGTH_SHORT).show();
        }




    }
    private JSONObject realizarPeticion() throws Exception{
        BufferedReader in = null;

        JSONObject jobj = crearJsonReal();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();

        httpPost.setURI(new URI("http://gui.uva.es:22/nuevoPro/"));
        httpPost.setEntity(new StringEntity(jobj.toString(), "UTF-8"));

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("content-type", "application/json");

        HttpResponse response = client.execute(httpPost);

        InputStreamReader lectura = new InputStreamReader(response.getEntity().getContent());
        in = new BufferedReader(lectura);
        StringBuffer sb = new StringBuffer("");
        String line = "";

        while ((line = in.readLine()) != null) sb.append(line);
        in.close();

        Log.d(Datos.TAG, "line vale : " + sb.toString());

        return new JSONObject(sb.toString());
    }


    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nick", Datos.user);
        jobj.put("nombre", this.nombre.getText().toString());
        jobj.put("descripcion", this.description.getText().toString());

        return jobj;
    }

    protected boolean checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            Datos.wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            Datos.mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(Datos.wifiConnected) {
                Log.i(Datos.TAG, context.getString(R.string.wifi_connection));
            } else if (Datos.mobileConnected){
                Log.i(Datos.TAG, context.getString(R.string.mobile_connection));
            }
            return true;
        } else {
            Log.i(Datos.TAG, context.getString(R.string.no_wifi_or_mobile));
            return false;
        }
    }
}
