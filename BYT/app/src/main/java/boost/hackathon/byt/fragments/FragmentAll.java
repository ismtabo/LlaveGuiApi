package boost.hackathon.byt.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import boost.hackathon.byt.activities.MostrarProyecto;
import boost.hackathon.byt.listAdapters.ListAdapterOwn;
import boost.hackathon.byt.Project;
import boost.hackathon.byt.R;
import boost.hackathon.byt.peticiones.ProInfAsync;


public class FragmentAll extends android.support.v4.app.Fragment {

    private final String PROJECT = "nombre";

    private ListView list;
    private View aux;

    private Context context;

    private String nombreMostrar;

    public FragmentAll() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        aux = inflater.inflate(R.layout.fragment_projects, container, false);
        context = aux.getContext();
        list = (ListView)aux.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ASD","MOSTRAR");
                try {
                    getDatos(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ArrayList<Project> arrayList = getArrayList();

        list.setAdapter(new ListAdapterOwn(aux.getContext(),arrayList));
        return aux;
    }

    private void getDatos(int position) throws JSONException {
        //TODO
        Log.d("FUNCIONA","FUNCIONA");

        nombreMostrar = getArrayList().get(position).getName();

        ProInfoAsync tarea = new ProInfoAsync();
        tarea.execute();


        //ProInfAsync async = new ProInfAsync(aux.getContext(), (Activity)(aux.getContext()),Datos.ownProjects.get(position).getString("nombre"));
        //async.execute();
        Log.d("FUNCIONA","FUNCIONAFINAL");
    }

    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("nombre", nombreMostrar);

        return jobj;
    }

    private void realizarPeticion() throws Exception{
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

        jobj = new JSONObject(sb.toString());

        JSONArray arr = jobj.getJSONArray("users");

        String[] usr = new String[arr.length()];
        JSONObject asd;
        for (int i = 0; i< usr.length; i++){
            asd = arr.getJSONObject(i);
            usr[i] = asd.getString("user");
        }

        Project p = new Project(jobj.getString("nombre"),
                jobj.getString("owner"), jobj.getString("descripcion"), new String[0], usr );

        Datos.projectData = p;
        Log.d("WAXATAG2", "projecto : " + jobj.toString());

        //res = new JSONObject(sb.toString());

        Log.d(Datos.TAG, "line vale : " + sb.toString());

    }

    private class ProInfoAsync extends AsyncTask<Void, Void, Boolean> {

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
                Toast.makeText(context, "ok :", Toast.LENGTH_SHORT).show();
                lanzarMostrar();
            }else
                Toast.makeText(context,"Error de peticion", Toast.LENGTH_SHORT).show();
        }
    }

    private void lanzarMostrar() {
        Intent i = new Intent(context, MostrarProyecto.class);
        startActivity(i);
    }


    private boolean checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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



    private Project JSONtoProject(JSONObject response) {

        Project project = null;

        try {

            JSONArray tags = response.getJSONArray("tags");
            JSONArray users = response.getJSONArray("users");

            String categorias [] = new String [tags.length()];
            String usuarios [] = new String [users.length()];

            for(int i=0;i<tags.length();i++)
                categorias[i] = tags.getJSONObject(i).getString("tag");

            for(int i=0;i<users.length();i++)
                categorias[i] = users.getJSONObject(i).getString("user");

            project = new Project(response.getString("nombre"), response.getString("owner"), response.getString("descripcion"), categorias, usuarios);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  project;

    }

    public ArrayList<Project> getArrayList() {
        ArrayList<Project> aL = new ArrayList<>();

        /*for(int i=0;i<100;i++)
            aL.add(new Project(Datos.ownProjects.get));
        return aL;*/

        try {

            for (int i = 0; i < Datos.allProjects.size(); i++)
                aL.add(new Project(Datos.allProjects.get(i).getString(PROJECT), Datos.user));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aL;
    }
}
