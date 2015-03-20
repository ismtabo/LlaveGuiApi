package waxa.pruebapeticionesbyt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button registro;
    private Button login;
    private Button nuevoPro;
    private Button unirsePro;
    private Button borrarPro;
    private Button usrInfo;
    private Button proInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registro = (Button)findViewById(R.id.registro);
        login = (Button)findViewById(R.id.login);
        nuevoPro = (Button)findViewById(R.id.nuevoPro);
        unirsePro = (Button)findViewById(R.id.unirsePro);
        borrarPro = (Button)findViewById(R.id.borrarPro);
        usrInfo = (Button)findViewById(R.id.usrInfo);
        proInfo = (Button)findViewById(R.id.proInfo);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarRegistro();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarLogin();
            }
        });

        nuevoPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarNuevoPro();
            }
        });

        unirsePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarUnirsePro();
            }
        });

        borrarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarBorrarPro();
            }
        });

        usrInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarUsrInfo();
            }
        });

        proInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarProInfo();
            }
        });
    }

    private void lanzarRegistro(){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    private void lanzarLogin(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    private void lanzarNuevoPro(){
        Intent i = new Intent(this, NuevoPro.class);
        startActivity(i);
    }

    private void lanzarUnirsePro(){
        Intent i = new Intent(this, UnirsePro.class);
        startActivity(i);
    }

    private void lanzarBorrarPro(){
        Intent i = new Intent(this, BorrarPro.class);
        startActivity(i);
    }

    private void lanzarUsrInfo(){
        Intent i = new Intent(this, UsrInfo.class);
        startActivity(i);
    }

    private void lanzarProInfo(){
        Intent i = new Intent(this, ProInfo.class);
        startActivity(i);
    }


}
