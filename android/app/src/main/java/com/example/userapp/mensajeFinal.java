package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.userapp.objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mensajeFinal extends AppCompatActivity implements View.OnClickListener {

    private TextView name;
    private Button volver;
    private String id;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_final);

        id = getIntent().getExtras().getString("id");

        name = findViewById(R.id.usuarioNameCita4);

        volver = findViewById(R.id.goBackBtn);

        db = FirebaseDatabase.getInstance();

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

        volver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, perfil.class);
        startActivity(i);
        finish();
    }
}