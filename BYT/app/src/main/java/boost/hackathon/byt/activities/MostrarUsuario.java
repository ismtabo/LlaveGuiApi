package boost.hackathon.byt.activities;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.R;
import boost.hackathon.byt.listAdapters.TagAdapter;


public class MostrarUsuario extends ActionBarActivity {

    private ImageView imagenUsuario;
    private TextView nameUsuario;
    private TextView nickUsuario;
    private TextView descripcionUsuario;
    private ListView categoriasDerecha;
    private Button botonProyectosMostrarUsuario;

    private JSONObject res;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuario);

        context = getApplicationContext();

        UsrInfoAsync aux = new UsrInfoAsync();
        aux.execute();

        imagenUsuario = (ImageView)findViewById(R.id.imagenUsuario);
        nameUsuario = (TextView) findViewById(R.id.nameUsuario);
        nickUsuario = (TextView) findViewById(R.id.nickUsuario);
        descripcionUsuario = (TextView) findViewById(R.id.descripcionUsuario);
        categoriasDerecha = (ListView) findViewById(R.id.categoriasMostrarUsuario);

        botonProyectosMostrarUsuario = (Button) findViewById(R.id.botonProyectosMostrarUsuario);

        botonProyectosMostrarUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lanzarMostrarPro();

                startActivity(new Intent(getApplicationContext(), SeleccionarProyecto.class));

            }
        });

        imagenUsuario.setImageResource(R.drawable.ic_launcher);
        ArrayList<String> asd = new ArrayList<String>();
        asd.add("Hackaton");
        categoriasDerecha.setAdapter(new TagAdapter(this, asd));

    }

    private void lanzarMostrarPro(){
        Intent i = new Intent(this, SeleccionarProyecto.class);
        startActivity(i);
        finish();
    }

    private void mostrarDatos() throws Exception{
        getSupportActionBar().setTitle(res.getString("nick"));
        nameUsuario.setText(res.getString("name"));
        nickUsuario.setText(res.getString("nick"));
        descripcionUsuario.setText(res.getString("descripcion"));
    }

    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nick", Datos.mostrarUserFromProject);
        return jobj;
    }

    private void realizarPeticion() throws Exception{
        BufferedReader in = null;

        JSONObject jobj = crearJsonReal();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();

        httpPost.setURI(new URI("http://gui.uva.es:22/getUsuario/"));
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

        res = new JSONObject(sb.toString());

        Log.d(Datos.TAG, "line vale : " + sb.toString());
    }

    private class UsrInfoAsync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                if (checkNetworkConnection()){
                    realizarPeticion();
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
                try{
                    mostrarDatos();
                }catch (Exception e){
                    Log.d("WAXATAG3", "NO SE QUE PASARA LALALALALALA");
                }

                Toast.makeText(context, "ok : " + res.toString(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(context,"Error de peticion", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            Datos.wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            Datos.mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(Datos.wifiConnected) {
                Log.i(Datos.TAG, getString(R.string.wifi_connection));
            } else if (Datos.mobileConnected){
                Log.i(Datos.TAG, getString(R.string.mobile_connection));
            }
            return true;
        } else {
            Log.i(Datos.TAG, getString(R.string.no_wifi_or_mobile));
            return false;
        }
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
            Log.d("ACTIONBAR", "EDITAR USUARIO");
            Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
