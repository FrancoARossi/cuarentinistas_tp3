package com.example.cuarentinistas_tp3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Perfil extends AppCompatActivity {

    String id;
    String nombre;
    String apellido;
    String direccion;
    String nuevaDireccion;
    int documento;
    String fechaNac;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent alPerfil = getIntent();
        id = alPerfil.getStringExtra("id");
        nombre = alPerfil.getStringExtra("nombre");
        apellido = alPerfil.getStringExtra("apellido");
        direccion = alPerfil.getStringExtra("direccion");
        documento = Integer.parseInt(Objects.requireNonNull(alPerfil.getStringExtra("documento")));
        fechaNac = alPerfil.getStringExtra("fechaNac");

        ((TextView)findViewById (R.id.idNombre)).setText(nombre);
        ((TextView)findViewById (R.id.idApellido)).setText(apellido);
        ((EditText)findViewById (R.id.editDireccion)).setText(direccion);
    }

    public void actualizarDireccion(View view) {
        asyncCall patchDireccion = new asyncCall();
        nuevaDireccion = ((EditText)findViewById (R.id.editDireccion)).getText().toString();
        patchDireccion.execute();

    }

    private class asyncCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                JSONObject datosActualizados = new JSONObject();
                datosActualizados.put("id", id)
                        .put("nombre", nombre)
                        .put("apellido", apellido)
                        .put("direccion", nuevaDireccion)
                        .put("documento", documento)
                        .put("fechaNac", fechaNac);
                RESTService.callREST(ServerAddress.value()+"/rest/clientes/1", "PUT", datosActualizados);
            } catch (JSONException e) {
                Log.e("ERROR", "Error al agregar la direccion al objeto json");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String mensaje = "Sus cambios se han guardado con exito";
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }
}