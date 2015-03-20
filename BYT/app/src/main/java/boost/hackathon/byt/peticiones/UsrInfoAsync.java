package boost.hackathon.byt.peticiones;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

import boost.hackathon.byt.Datos;

/**
 * Created by Raikuro on 05/03/2015.
 */
public class UsrInfoAsync extends Async {

    private final String NICK = "nick";

    private String nick;

    public UsrInfoAsync (Context context, Activity activity, String nick){

        super(context, activity);
        this.nick = nick;

    }

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
        if (result)
            Toast.makeText(context, "ok : " + res.toString(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context,"Error de peticion", Toast.LENGTH_SHORT).show();
    }


    synchronized private void realizarPeticion() throws Exception{
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

        this.notify();

        Log.d(Datos.TAG, "line vale : " + sb.toString());

    }

    private JSONObject crearJsonReal() throws Exception{

        JSONObject jobj = new JSONObject();
        jobj.put(NICK, nick);

        return jobj;
    }

    synchronized public JSONObject getResponse (){

        try {
            this.wait();
        } catch (InterruptedException ie){}

        return res;

    }
}
