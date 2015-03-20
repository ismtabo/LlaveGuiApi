package boost.hackathon.byt.peticiones;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import boost.hackathon.byt.Datos;

public class ProInfAsync extends Async{

    String nombre;

    public ProInfAsync(Context context, Activity activity, String nombre) {
        super(context, activity);
        this.nombre = nombre;
    }

    private JSONObject crearJsonReal() throws Exception {

        JSONObject jobj = new JSONObject();
        jobj.put("nombre", nombre);

        return jobj;
    }

    synchronized private void realizarPeticion() throws Exception {

        BufferedReader in = null;

        JSONObject jobj = crearJsonReal();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();

        httpPost.setURI(new URI("http://gui.uva.es:22/getProjecto/"));
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

        this.notify();

        Log.d(Datos.TAG, "line vale : " + sb.toString());

    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            if (checkNetworkConnection()) {
                realizarPeticion();
            } else {
                Log.d(Datos.TAG, "no hay conexion2 a internet");
            }
        } catch (Exception e) {
            Log.d(Datos.TAG, "NO SE QUE COÑO PASA " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("FUNCIONA", "ASD");
        JSONArray arr = null;
        try {
            arr = res.getJSONArray("users");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arr.length(); i++) {

            JSONObject aux = null;
            try {
                aux = arr.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String nick = aux.getString("nick");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (result)
            Toast.makeText(context, "ok : " + res.toString(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Error de peticion", Toast.LENGTH_SHORT).show();

        Log.d("FUNCIONA", res.toString());
        getResponse();
    }

    synchronized public JSONObject getResponse (){

        try {
            this.wait();
        } catch (InterruptedException ie){}

        return res;

    }
}