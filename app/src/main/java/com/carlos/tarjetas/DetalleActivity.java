package com.carlos.tarjetas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.carlos.tarjetas.models.TarjetaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends AppCompatActivity {

    private TextView txNumero;
    private TextView txMes;
    private TextView txAnio;
    private TextView txCupo;
    private TextView txSaldo;
    private TextView txDeuda;
    private TextView txFranquicia;
    private String text_reference = "tarjeta";
    private String id;
    private TarjetaModel model;
    private FirebaseDatabase dbReceta = FirebaseDatabase.getInstance();
    private DatabaseReference dbReferencia = dbReceta.getReference(text_reference);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        txNumero = findViewById(R.id.txNumero);
        txMes = findViewById(R.id.txMes);
        txAnio = findViewById(R.id.txAnio);
        txCupo = findViewById(R.id.txCupo);
        txSaldo = findViewById(R.id.txSaldo);
        txDeuda = findViewById(R.id.txDeuda);
        txFranquicia = findViewById(R.id.txFranquicia);

        model = new TarjetaModel();
        id = getIntent().getStringExtra("id");
        if(id != null && !id.equals(""))
        {
            dbReferencia.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model = dataSnapshot.getValue(TarjetaModel.class);
                    if(model != null)
                    {
                        txNumero.setText(model.getNumero().toString());
                        txMes.setText(model.getMes().toString());
                        txAnio.setText(model.getAnio().toString());
                        txCupo.setText(model.getCupo().toString());
                        txSaldo.setText(model.getSaldo().toString());
                        txDeuda.setText(model.getDeuda().toString());
                        txFranquicia.setText(model.getFranquicia().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DetalleActivity.this, "Error con FireBase", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
