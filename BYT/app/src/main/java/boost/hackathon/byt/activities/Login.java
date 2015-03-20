package boost.hackathon.byt.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import boost.hackathon.byt.R;
import boost.hackathon.byt.peticiones.LoginAsync;


public class Login extends ActionBarActivity {

    private final String NICK = "nick";
    private final String PASSWD = "passwd";

    private LoginAsync async;

    private EditText nick;
    private EditText passwd;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nick=(EditText) findViewById(R.id.userLogin);
        passwd=(EditText) findViewById(R.id.passwordLogin);
        start=(Button)findViewById(R.id.botonLogin);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                async();
            }
        });
    }

    private void async (){

        async = new LoginAsync(this, (Activity)this, nick.getText().toString(), passwd.getText().toString());
        async.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.launch_register) {
            //TODO
            Log.d("ACTIONBAR", "LANZANDO REGISTRO");
            startActivity(new Intent(this, Registro.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
