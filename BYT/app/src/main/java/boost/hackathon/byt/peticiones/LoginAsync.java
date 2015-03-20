package boost.hackathon.byt.peticiones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.activities.ProjectList;
import boost.hackathon.byt.activities.SeleccionarProyecto;

/**
 * Created by Raikuro on 05/03/2015.
 */
public class LoginAsync extends Async {

    private static final String NICK = "nick";
    private static final String PASSWD = "passwd";
    private static final String OWN = "propios";
    private static final String ALL = "projectos";

    private JSONObject res;
    private JSONObject res2;
    private JSONObject res3;

    private String nick;
    private String passwd;

    public LoginAsync(Context context, Activity activity, String nick, String passwd){

        super(context, activity);
        this.nick = nick;
        this.passwd = passwd;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try{
            if (checkNetworkConnection() && !emptyFields()){

                //CODIGO DE WAXA

                try {
                    Log.d("WAXATAG2", "lanzando res");
                    res = apirequest();
                    Log.d("WAXATAG2", "res : " + res.toString());


                    if (res.getBoolean("resultado")){
                        Log.d("WAXATAG2", "lanzando res2");
                        res2 = apirequest3();
                        Datos.USUARIO = res2;
                        lanzarRes3();
               			res2 = apirequest2();
                        Log.d("WAXATAG2", "res2 : " + res2.toString());
                    }
                    else
                        Toast.makeText(context, "No ok", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.d(Datos.TAG, "error de JSON : " + e.getMessage());
                }
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
                if (res.getBoolean("resultado")){
                    Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    launchCloud();
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

    private void lanzarRes3() throws Exception{
        ArrayList<JSONObject> arrayList = new ArrayList<>();

        JSONArray aux;

        aux = Datos.USUARIO.getJSONArray("propios");
        for (int i=0; i< aux.length(); i++){
            arrayList.add(apirequest4(aux.getJSONObject(i).getString("nombre")));
        }

        Datos.waxaOwnProjects = arrayList;
        Log.d("WAXATAG2", "owns : " + Datos.waxaOwnProjects.toString());

        arrayList = new ArrayList<>();

        aux = Datos.USUARIO.getJSONArray("otros");
        for (int i=0; i< aux.length(); i++){
            arrayList.add(apirequest4(aux.getJSONObject(i).getString("nombre")));
        }

        Datos.otherProjects = arrayList;
        Log.d("WAXATAG2", "others : " + Datos.otherProjects.toString());

    }

    private JSONObject apirequest() throws Exception {
        BufferedReader in = null;

        JSONObject jobj = crearJsonReal();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();

        httpPost.setURI(new URI("http://gui.uva.es:22/login/"));
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

        Log.d(Datos.TAG, "line2 vale : " + sb.toString());

        return new JSONObject(sb.toString());
    }

    private JSONObject apirequest2() throws Exception {
        BufferedReader in = null;

        JSONObject jobj = new JSONObject();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();

        httpPost.setURI(new URI("http://gui.uva.es:22/getAllPro/"));
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

    private JSONObject apirequest3() throws Exception {
        BufferedReader in = null;

        JSONObject jobj = new JSONObject();

        jobj.put("nick",this.nick);

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

        Log.d(Datos.TAG, "line vale : " + sb.toString());

        return new JSONObject(sb.toString());
    }

    private JSONObject apirequest4(String nombre) throws Exception {
        BufferedReader in = null;

        JSONObject jobj = new JSONObject();

        jobj.put("nombre",nombre);

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

        Log.d(Datos.TAG, "line vale : " + sb.toString());

        return new JSONObject(sb.toString());
    }

    public void launchCloud(){
        Intent i= new Intent(context, ProjectList.class);
        Datos.user = nick;
        getUserProjects(Datos.user);
        getAllProjects();
        activity.startActivity(i);

    }

    private void getUserProjects (String user) {

        UsrInfoAsync r = new UsrInfoAsync(context, activity, user);
        r.execute();
        JSONObject res = r.getResponse();
        Log.d("ASD", res.toString());
        Log.d("WAXATAG", "res : " + res.toString());

        Datos.ownProjects = jsonToArrayList(res, OWN);

    }

    private void getAllProjects (){

        Log.d("ASDres2", res2.toString());
        Datos.allProjects = jsonToArrayList(res2, ALL);

    }

    private ArrayList<JSONObject> jsonToArrayList (JSONObject res, String nombre){

        ArrayList<JSONObject> projects = new ArrayList<JSONObject>();
        JSONArray array = null;

        try {
            array = res.getJSONArray(nombre);

        for (int i = 0; i < array.length(); i++)
            projects.add(array.getJSONObject(i));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return projects;

    }

    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put(NICK, nick);
        Log.d(Datos.TAG, nick);
        jobj.put(PASSWD, passwd);

        return jobj;
    }

    public boolean emptyFields() {
        return (nick.isEmpty() || passwd.isEmpty());
    }
}
