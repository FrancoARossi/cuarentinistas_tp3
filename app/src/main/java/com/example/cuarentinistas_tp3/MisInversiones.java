package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

//TODO: SOLUCIONAR LO DE LA LLAMADA REST DEL BONO (NECESITA HACERSE EN OTRA ASYNCTASK)

public class MisInversiones extends AppCompatActivity {

    ArrayList<Inversion> listaInversiones;
    RecyclerView recyclerInversiones;
    //    Intent myIntent = getIntent();
//    String cbuCuenta = myIntent.getStringExtra("cbuCuenta");
    String cbuCuenta = "400720010";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mis Inversiones");
        setContentView(R.layout.activity_mis_inversiones);

        recyclerInversiones = (RecyclerView) findViewById(R.id.idRecyclerInversiones);
        recyclerInversiones.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        MisInversiones.asyncCall getInversiones = new MisInversiones.asyncCall();
        getInversiones.execute();
    }

    private class asyncCall extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... args) {
            return RESTService.makeGetRequest(ServerAddress.value() + "/rest/inversiones/cuenta/" + cbuCuenta);
            //return RESTService.makeGetRequest("https://jsonplaceholder.typicode.com/todos");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                // Si el resultado es un string que contiene el formato de un unico elemento JSON
                // le agrego los corchetes para que cumpla con el formato de un JSONArray
                if (result.charAt(0) != '[') {
                    result = "[" + result + "]";
                }
                JSONArray inversiones = new JSONArray(result);
                listaInversiones = new ArrayList<>();

                for (int i = 0, size = inversiones.length(); i < size; i++) {
                    JSONObject inversion = inversiones.getJSONObject(i);
                    Iterator<String> iterInversion = inversion.keys();
                    String detalle = "";
                    String nombreBono = "";

                    while (iterInversion.hasNext()) {
                        String keyInversion = iterInversion.next();
                        String keyFormateada = "";

                        switch (keyInversion) {
                            case "bonoId":
                                MisInversiones.asyncCall getBono = new MisInversiones.asyncCallBono();
                                getBono.execute(inversion).get();
                            case "cantidad":
                                keyFormateada = "Cantidad";
                                break;
                            case "fecha":
                                keyFormateada = "Fecha y Hora de Compra";
                                SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                Date fechaParseada = dateParse.parse(inversion.get("vencimiento").toString());
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
                                String fecha = dateFormat.format(fechaParseada);
                                detalle += keyFormateada + ": " + fecha + "\n";
                                continue;
                            default:
                                continue;
                        }
                        if (!keyInversion.equals("bonoId")) {
                            detalle += keyFormateada + ": " + inversion.get(keyInversion) + "\n";
                        }
                    }
                    listaInversiones.add(new Inversion(nombreBono, detalle));
                }
                AdaptadorInversiones adapter = new AdaptadorInversiones(listaInversiones);
                recyclerInversiones.setAdapter(adapter);
            } catch (JSONException | ParseException | InterruptedException | ExecutionException e) {
                Log.e("ERROR", "Se produjo el siguiente error:", e);
            }
        }
    }

    private class asyncCallBono extends AsyncTask<JSONObject, Void, String> {
        JSONObject inversion;

        @Override
        protected String doInBackground(JSONObject... args) {
            inversion = args[0];
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                String resultado = RESTService.makeGetRequest(ServerAddress.value() + "/rest/bonos/" + inversion.get(keyInversion));
                Toast.makeText(getApplicationContext(), resultado, resultado.length()).show();
                JSONObject bono = new JSONObject(resultado);
                Iterator<String> iterBono = bono.keys();

                String keyFormateada;

                while (iterBono.hasNext()) {
                    String keyBono = iterBono.next();
                    switch (keyBono) {
                        case "nombre":
                            String nombreBono = bono.get(keyBono).toString();
                            break;
                        case "precioCobro":
                            keyFormateada = "Precio de Cobro";
                            break;
                        case "precioPago":
                            keyFormateada = "Precio de Pago";
                            break;
                        case "vencimiento":
                            keyFormateada = "Fecha y Hora de Vencimiento";
                            SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            Date fechaParseada = dateParse.parse(inversion.get("vencimiento").toString());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
                            String fecha = dateFormat.format(fechaParseada);
                            detalle += keyFormateada + ": " + fecha + "\n";
                            continue;
                        default:
                            continue;
                    }
                    detalle += keyFormateada + ": " + bono.get(keyBono) + "\n";
                }
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }
}