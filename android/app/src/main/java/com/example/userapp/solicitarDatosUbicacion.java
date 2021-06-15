package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.objects.CustomItem;
import com.example.userapp.objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class solicitarDatosUbicacion extends AppCompatActivity implements View.OnClickListener {

    private TextView name;
    private Button next;
    private EditText direccionCita, pisoCita, descripcionUbiCita;
    private String id, tipo, nombre, correo, telefono, descripcionProblema;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_datos_ubicacion);


        db = FirebaseDatabase.getInstance();

        id = getIntent().getExtras().getString("id");
        tipo = getIntent().getExtras().getString("tipo");
        correo = getIntent().getExtras().getString("correo");
        telefono = getIntent().getExtras().getString("telefono");
        descripcionProblema = getIntent().getExtras().getString("descripcion");
        nombre = getIntent().getExtras().getString("nombre");

        direccionCita = findViewById(R.id.direccionETCita);
        pisoCita = findViewById(R.id.pisoETCita);
        descripcionUbiCita = findViewById(R.id.descripcionUbiETCita);

        name = findViewById(R.id.usuarioNameCita2);

        next = findViewById(R.id.siguiente2);

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

        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.siguiente2:
                String direccion = direccionCita.getText().toString();
                String piso = pisoCita.getText().toString();
                String descripUbi = descripcionUbiCita.getText().toString();

                if (direccion.isEmpty() || direccion == "" || piso.isEmpty() || piso == "" || descripUbi.isEmpty() || descripUbi == "") {
                    Toast.makeText(this, "Complete todos los espacios, por favor", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(this, SolicitarDatosUbicacionFecha.class);
                    i.putExtra("id", id);
                    i.putExtra("nombre", nombre);
                    i.putExtra("correo", correo);
                    i.putExtra("telefono", telefono);
                    i.putExtra("descripcion", descripcionProblema);
                    i.putExtra("tipo", tipo);
                    i.putExtra("direccion", direccion);
                    i.putExtra("piso", piso);
                    i.putExtra("descripcionPiso", descripUbi);
                    startActivity(i);
                    finish();
                }
                break;
    }}
}