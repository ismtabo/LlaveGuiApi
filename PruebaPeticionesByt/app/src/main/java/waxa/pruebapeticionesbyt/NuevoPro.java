package waxa.pruebapeticionesbyt;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class NuevoPro extends Activity {

    private EditText nick;
    private EditText nombre;
    private EditText descripcion;
    private Button entrar;

    private JSONObject res;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pro);

        this.context = this;

        nick = (EditText)findViewById(R.id.nick);
        nombre = (EditText)findViewById(R.id.nombre);
        descripcion = (EditText)findViewById(R.id.descripcion);

        entrar = (Button)findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevoProAsync r = new NuevoProAsync();
                r.execute();
            }
        });
    }

    private JSONObject crearJsonPrueba() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nick", "waxanick");
        jobj.put("nombre", "projectNombre");
        jobj.put("descripcion", "projectDescripcion");

        return jobj;
    }

    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nick", this.nick.getText().toString());
        jobj.put("nombre", this.nombre.getText().toString());
        jobj.put("descripcion", this.descripcion.getText().toString());

        return jobj;
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

    private class NuevoProAsync extends AsyncTask<Void, Void, Boolean> {

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
                    if (res.getBoolean("resultado"))
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
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

}
