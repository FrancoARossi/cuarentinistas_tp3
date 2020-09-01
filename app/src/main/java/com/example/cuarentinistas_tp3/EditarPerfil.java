package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditarPerfil extends AppCompatActivity {

    String nombre, apellido, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        Intent myIntent = getIntent();
        nombre = myIntent.getStringExtra("nombre");
        apellido = myIntent.getStringExtra("apellido");
        direccion = myIntent.getStringExtra("direccion");

        ((TextView)findViewById (R.id.idNombre)).setText(nombre);
        ((TextView)findViewById (R.id.idApellido)).setText(apellido);
        ((EditText)findViewById (R.id.editDireccion)).setText(direccion);

    }

    public void actualizarDireccion(View view) {
        asyncCall putDireccion = new asyncCall();
        String nuevaDireccion = ((EditText)findViewById (R.id.editDireccion)).getText().toString();
        putDireccion.execute(nuevaDireccion);

    }

    private class asyncCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                JSONObject direccionPatch = new JSONObject();
                direccionPatch.put("direccion", args[0]);
            } catch (JSONException e) {
                Log.e("ERROR", "Error al agregar la direccion al objeto json");
            }
            //TODO: Solucionar FileNotFoundException en la llamda REST
            return RESTService.callREST(ServerAddress.value()+"/rest/clientes/1", "PATCH", direccionPatch);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, result.length());
        }
    }
}