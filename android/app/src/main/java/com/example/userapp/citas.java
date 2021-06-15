package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.userapp.objects.Cita;
import com.example.userapp.objects.User;
import com.example.userapp.objects.baseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class citas extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase db;
    private ListView listV;
    private TextView name;
    private Button volver;
    private baseAdapter alv;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        id = getIntent().getExtras().getString("id");

        volver = findViewById(R.id.volver);
        listV = findViewById(R.id.listaCitas);
        name = findViewById(R.id.usuarioNameCita5);

        alv = new baseAdapter();

        listV.setAdapter(alv);

        volver.setOnClickListener(this);

        db = FirebaseDatabase.getInstance();

        loadDatabase();


        db.getReference().child("clientes").child(id).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        User user = data.getValue(User.class);

                        name.setText(user.nombre);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    private void loadDatabase() {
        DatabaseReference ref = db.getReference().child("citasConfirmadas").child(id);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        alv.clear();
                        for (DataSnapshot child : data.getChildren()){
                            Cita cita = child.getValue(Cita.class);
                            alv.addCitas(cita);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, perfil.class);
        startActivity(i);
        finish();
    }
}