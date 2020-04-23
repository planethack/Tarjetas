package com.carlos.tarjetas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.carlos.tarjetas.models.TarjetaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class NuevoActivity extends AppCompatActivity {

    private String id;
    private EditText txNumero;
    private EditText txMes;
    private EditText txAnio;
    private EditText txCupo;
    private EditText txSaldo;
    private EditText txDeuda;
    private EditText txFranquicia;
    private FloatingActionButton fab_nuevo;
    private ImageView iv;
    private String text_reference = "tarjeta";
    private TarjetaModel model;
    private FirebaseDatabase dbReceta = FirebaseDatabase.getInstance();
    private DatabaseReference dbReferencia = dbReceta.getReference(text_reference);

    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = findViewById(R.id.tb_nuevo);
        setSupportActionBar(toolbar);

        fab_nuevo = findViewById(R.id.fab_nuevo);
        txNumero = findViewById(R.id.txNumero);
        txMes = findViewById(R.id.txMes);
        txAnio = findViewById(R.id.txAnio);
        txCupo = findViewById(R.id.txCupo);
        txSaldo = findViewById(R.id.txSaldo);
        txDeuda = findViewById(R.id.txDeuda);
        txFranquicia = findViewById(R.id.txFranquicia);

        txNumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                              @Override
                                              public void onFocusChange(View v, boolean hasFocus) {
                                                  if (!hasFocus) {
                                                      String s = txNumero.getText().toString();
                                                      switch(s.substring(0, 1)) {
                                                          case "3":
                                                              if(s.substring(0, 2).equals("36"))
                                                                  txFranquicia.setText("Dinners");
                                                              else
                                                                  txFranquicia.setText("American Express");
                                                              break;
                                                          case "4":
                                                              txFranquicia.setText("Visa");
                                                              break;
                                                          case "5":
                                                              txFranquicia.setText("Master Card");
                                                              break;
                                                          case "6":
                                                              txFranquicia.setText("Discover");
                                                              break;
                                                          default:
                                                              Toast.makeText(NuevoActivity.this, "Número de tarjeta invalido", Toast.LENGTH_LONG).show();
                                                              txNumero.setText("");
                                                              v.clearFocus();
                                                              txFranquicia.setText("");

                                                      }
                                                  }
                                              }
                                          });

        txAnio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(txAnio.getText().toString().equals("") || Integer.valueOf(txAnio.getText().toString()) < 2021) {
                        Toast.makeText(NuevoActivity.this, "El año debe ser mayor al actual", Toast.LENGTH_LONG).show();
                        txAnio.setText("");
                    }
                }
            }
        });

        txSaldo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(Double.valueOf(txSaldo.getText().toString()) > Double.valueOf(txCupo.getText().toString())) {
                        Toast.makeText(NuevoActivity.this, "El saldo no puede ser mayor al cupo", Toast.LENGTH_LONG).show();
                        txAnio.setText("");
                    }
                }
            }
        });

        fab_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = txNumero.getText().toString();
                Integer mes = Integer.valueOf(txMes.getText().toString());
                Integer anio = Integer.valueOf(txAnio.getText().toString());
                Double cupo = Double.valueOf(txCupo.getText().toString());
                Double saldo = Double.valueOf(txSaldo.getText().toString());
                Double deuda = Double.valueOf(txDeuda.getText().toString());
                String franquicia = txFranquicia.getText().toString();


                if(!numero.equals("")) {
                     id = dbReferencia.push().getKey();
                    if (id != null) {
                        model = new TarjetaModel(id, numero, mes, anio, cupo, saldo, deuda, franquicia);
                        dbReferencia.child(id).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(NuevoActivity.this, "Elemento guardado satisfactoriamente", Toast.LENGTH_LONG).show();
                                        Intent lista = new Intent(NuevoActivity.this, MainActivity.class);
                                        startActivity(lista);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NuevoActivity.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(NuevoActivity.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
