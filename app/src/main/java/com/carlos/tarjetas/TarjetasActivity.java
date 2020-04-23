package com.carlos.tarjetas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.carlos.tarjetas.adapters.TarjetasAdapter;
import com.carlos.tarjetas.models.TarjetaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TarjetasActivity extends AppCompatActivity {

    private ListView lv_Tarjetas;
    private ArrayList<TarjetaModel> list;
    private String text_reference = "tarjeta";
    private TarjetaModel model;
    private FirebaseDatabase dbTarjeta = FirebaseDatabase.getInstance();
    private DatabaseReference dbReferencia = dbTarjeta.getReference(text_reference);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv_Tarjetas = findViewById(R.id.lv_tarjetas);
        list = new ArrayList<>();
        model = new TarjetaModel();

        dbReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    model = child.getValue(TarjetaModel.class);
                    list.add(model);
                }
                lv_Tarjetas.setAdapter(new TarjetasAdapter(TarjetasActivity.this, list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TarjetasActivity.this, "Error con FireBase", Toast.LENGTH_LONG).show();
            }
        });



        lv_Tarjetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model = (TarjetaModel) adapterView.getItemAtPosition(i);
                if (model.getId() != null && !model.getId().equals("")) {
                    Intent detalle = new Intent(TarjetasActivity.this, DetalleActivity.class);
                    detalle.putExtra("id", model.getId());
                    startActivity(detalle);
                }
            }
        });
    }
}
