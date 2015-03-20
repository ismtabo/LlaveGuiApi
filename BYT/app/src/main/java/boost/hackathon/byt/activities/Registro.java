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

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.R;


public class Registro extends ActionBarActivity {

    private EditText name;
    private EditText nick;
    private EditText passwd;
    private EditText passwd2;
    private EditText email;
    private EditText nation;
    private EditText province;

    private Button register;

    private JSONObject res;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
        name=(EditText) findViewById(R.id.nameReg);
        nick=(EditText) findViewById(R.id.nickReg);
        passwd=(EditText) findViewById(R.id.passwordReg);
        passwd2=(EditText) findViewById(R.id.passwordReg2);
        email=(EditText) findViewById(R.id.emailReg);
        nation=(EditText) findViewById(R.id.nation);
        province=(EditText) findViewById(R.id.province);
        register=(Button) findViewById(R.id.btnRegister);
        
        context=getApplicationContext();
        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private  void register(){
        try {
            equalspasswd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void equalspasswd() throws Exception{
        //Hay que ponerlo bonito
        if(passwd.getText().toString().equals(passwd2.getText().toString())){
            RegistroAsync r = new RegistroAsync();
            r.execute();
        }
    }


    public void launchCloud(View view){
        Intent i= new Intent(this, ProjectList.class);
        try {
            i.putExtra("nick",res.getString("nick"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(i);
    }

    private JSONObject crearJsonReal() throws Exception{
        JSONObject jobj = new JSONObject();
        jobj.put("name", this.name.getText().toString());
        jobj.put("nick", this.nick.getText().toString());
        jobj.put("passwd", this.passwd.getText().toString());
        jobj.put("correo", this.email.getText().toString());
        jobj.put("pais", this.nation.getText().toString());
        jobj.put("localidad", this.province.getText().toString());

        return jobj;
    }

    private JSONObject apirequest() throws Exception{
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

    private class RegistroAsync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                if (checkNetworkConnection()){
                    res = apirequest();
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
}
