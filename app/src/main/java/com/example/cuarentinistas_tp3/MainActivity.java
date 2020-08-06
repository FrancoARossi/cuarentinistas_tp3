package com.example.cuarentinistas_tp3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnEjecutar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MiAsyncTask().execute();
            }
        });
    }

    private class MiAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return RESTService.makeGetRequest(
                    "https://reqres.in/api/users/2");
        }

        @Override
        protected void onPostExecute(String result) {
            Toast notificacion = Toast.makeText(
                    getApplicationContext(), result, Toast.LENGTH_LONG);
            notificacion.show();
        }
    }
}
