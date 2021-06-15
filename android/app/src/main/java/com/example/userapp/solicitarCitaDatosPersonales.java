package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.objects.CustomAdapter;
import com.example.userapp.objects.CustomItem;
import com.example.userapp.objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class solicitarCitaDatosPersonales extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner customSpinner;
    private ArrayList<CustomItem> customList;
    private TextView nombre;
    private Button next;
    private EditText nombreCita, correoCita, numeroCita, descripcionCita;
    private String id, tipo;
    private FirebaseDatabase db;
    int width = 264;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_cita_datos_personales);

        db = FirebaseDatabase.getInstance();

        nombre = findViewById(R.id.usuarioNameCita);
        nombreCita = findViewById(R.id.nombreETCita);
        correoCita = findViewById(R.id.correoETCita);
        numeroCita = findViewById(R.id.numeroETCita);
        next = findViewById(R.id.siguiente1);
        descripcionCita = findViewById(R.id.descripcionETCita);


        id = getIntent().getExtras().getString("id");
        db.getReference().child("clientes").child(id).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        User user = data.getValue(User.class);

                        nombre.setText(user.nombre);
                        nombreCita.setText(user.nombre);
                        correoCita.setText(user.correo);

                        if (user.numero != null) {
                            numeroCita.setText(user.numero);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


        customSpinner = findViewById(R.id.customIconSpinner);
        customList = getCustomList();
        CustomAdapter adapter = new CustomAdapter(this, customList);

        if (customSpinner != null) {
            customSpinner.setAdapter(adapter);
            customSpinner.setOnItemSelectedListener(this);
        }

        next.setOnClickListener(this);
    }

    private ArrayList<CustomItem> getCustomList() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Tipo de electrodomestico", R.drawable.lav));
        customList.add(new CustomItem("Lavadora", R.drawable.lav));
        customList.add(new CustomItem("Nevera", R.drawable.nev));
        customList.add(new CustomItem("Aire Acondicionado", R.drawable.air));
        customList.add(new CustomItem("Estufa/Horno", R.drawable.horno));
        return customList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            LinearLayout linearLayout = findViewById(R.id.customSpinnerItemLayout);
            width = linearLayout.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        customSpinner.setDropDownWidth(width);
        CustomItem item = (CustomItem) parent.getSelectedItem();
        tipo = item.getSpinnerItemName();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.siguiente1:
                String nombre = nombreCita.getText().toString();
                String correo = correoCita.getText().toString();
                String telefono = numeroCita.getText().toString();
                String descrip = descripcionCita.getText().toString();

                if (nombre.isEmpty() || nombre == "" || correo.isEmpty() || correo == "" || telefono.isEmpty() || telefono == "" || descrip.isEmpty() || descrip == "" || tipo.equals("Tipo de electrodomestico")) {
                    Toast.makeText(this, "Complete todos los espacios, por favor", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(this, solicitarDatosUbicacion.class);
                    i.putExtra("id", id);
                    i.putExtra("nombre", nombre);
                    i.putExtra("correo", correo);
                    i.putExtra("telefono", telefono);
                    i.putExtra("descripcion", descrip);
                    i.putExtra("tipo", tipo);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }
}