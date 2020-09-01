package com.example.cuarentinistas_tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RealizarTransferencia extends AppCompatActivity {

    String cbuCuenta;
    EditText descripcion;
    EditText cbuDestino;
    EditText importe;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Realizar Transferencia");
        setContentView(R.layout.activity_realizar_transferencia);

        Intent intent = getIntent();
        cbuCuenta = intent.getStringExtra("cbuCuenta");

        descripcion = findViewById(R.id.descripcion);
        cbuDestino = findViewById(R.id.cbuDestino);
        importe = findViewById(R.id.importe);
        enviar = findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new RealizarPago().execute();
            }
        });
    }


    public class RealizarPago extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result;
            try {
                JSONObject pago = new JSONObject();
                pago.put("cbuSalida", cbuCuenta)
                        .put("fecha", "2020-07-15T03:00:00Z[UTC]")
                        .put("descripcion", descripcion.getText())
                        .put("cbuDestino", cbuDestino.getText())
                        .put("importe", importe.getText());
                result = RESTService.callREST(ServerAddress.value() + "/rest/movimientos", "POST", pago);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Transferencia realizada", Toast.LENGTH_SHORT).show();
            cbuDestino.setText("");
            descripcion.setText("");
            importe.setText("");
            cbuDestino.requestFocus();
        }
    }


}