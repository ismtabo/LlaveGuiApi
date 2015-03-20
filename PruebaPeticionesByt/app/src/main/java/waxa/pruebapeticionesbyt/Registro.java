package waxa.pruebapeticionesbyt;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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


public class Registro extends Activity implements View.OnClickListener {

    private EditText nick;
    private EditText passwd;
    private EditText correo;
    private EditText descripcion;
    private EditText pais;
    private EditText localidad;

    private Button entrar;

    private JSONObject res;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.context = this;

        nick = (EditText)findViewById(R.id.nick);
        passwd = (EditText)findViewById(R.id.passwd);
        correo = (EditText)findViewById(R.id.correo);
        descripcion = (EditText)findViewById(R.id.descripcion);
        pais = (EditText)findViewById(R.id.pais);
        localidad = (EditText)findViewById(R.id.localidad);

        entrar = (Button)findViewById(R.id.entrar);
        entrar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        RegistroAsync r = new RegistroAsync();
        r.execute();
    }

    private JSONObject crearJsonPrueba() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nick", "waxanick");
        jobj.put("passwd", "waxapasswd");
        jobj.put("correo", "waxacorreo");
        jobj.put("descripcion", "waxadescripcion");
        jobj.put("pais", "waxapais");
        jobj.put("localidad", "waxalocalidad");

        return jobj;
    }

    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nick", this.nick.getText().toString());
        jobj.put("passwd", this.passwd.getText().toString());
        jobj.put("correo", this.correo.getText().toString());
        jobj.put("descripcion", this.descripcion.getText().toString());
        jobj.put("pais", this.pais.getText().toString());
        jobj.put("localidad", this.localidad.getText().toString());

        return jobj;
    }

    private JSONObject realizarPeticion() throws Exception{
        BufferedReader in = null;

        JSONObject jobj = crearJsonReal();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();
        httpPost.setURI(new URI("http://gui.uva.es:22/registro/"));
        httpPost.setEntity(new StringEntity(jobj
                .toString(), "UTF-8"));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("content-type", "application/json");

        HttpResponse response = client.execute(httpPost);
        InputStreamReader lectura = new InputStreamReader(response
                .getEntity().getContent());
        in = new BufferedReader(lectura);
        StringBuffer sb = new StringBuffer("");
        String line = "";
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        Log.d(Datos.TAG, "line vale : " + sb.toString());
        return new JSONObject(sb.toString());
    }

    private class RegistroAsync extends AsyncTask <Void, Void, Boolean> {

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
                        Toast.makeText(context, "No ok : " + res.get("error"), Toast.LENGTH_SHORT).show();
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


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
