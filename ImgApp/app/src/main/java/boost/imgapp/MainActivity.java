package boost.imgapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    ImageView photo;
    Button button;
    Button aux;
    JSONObject res;
    Bitmap photobmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Controles
        button = (Button)findViewById(R.id.btnUpload);
        photo = (ImageView)findViewById(R.id.photo);
        aux = (Button) findViewById(R.id.btnDownload);
        //evento click para cargar fotografia a la aplicación
        photo.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Complete la acción usando..."), 1);
                    }});
        //evento click para comenzar a subir la imagen al servidor
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //Codifica la imagen con Base64
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photobmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        //Se ejecuta en segundo plano para no colgar la aplicacion
                        new MyAsyncTask(MainActivity.this).execute(encodedImage);
                    }});

        aux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CogerImg().execute();
            }
        });
    }


    public void hacerCosas() throws Exception{
        byte[] resultado = Base64.decode(res.getString("image"), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(resultado, 0, resultado.length);
        photo.setImageBitmap(decodedByte);

        //Log.d("WAXATAG", res);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String aaa = getRealPathFromURI(selectedImageUri);
            photobmp = BitmapFactory.decodeFile(aaa);
            photo.setImageBitmap(photobmp);
        }
    }

    /**
     * Obtiene la ruta del archivo de imagen en el dispositivo
     * @return String
     * */
    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getApplicationContext().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class CogerImg extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ApiRest apiRest = new ApiRest();
            try {
                res = apiRest.dowloadPhoto();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                hacerCosas();
            }catch (Exception e){
                Log.d("WAXATAG", "Excepcion del post execute");
            }
        }
    }


}