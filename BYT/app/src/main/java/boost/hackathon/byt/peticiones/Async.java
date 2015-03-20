package boost.hackathon.byt.peticiones;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import boost.hackathon.byt.Datos;
import boost.hackathon.byt.R;

/**
 * Created by Raikuro on 05/03/2015.
 */
public class Async extends AsyncTask<Void, Void, Boolean> {

    protected Context context;
    protected JSONObject res;
    protected Activity activity;

    public Async (Context context, Activity activity){

        this.context = context;
        this.activity = activity;

    }

    @Override
    protected Boolean doInBackground(Void... params) { return false; }

    @Override
    protected void onPostExecute(Boolean result) {}

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
