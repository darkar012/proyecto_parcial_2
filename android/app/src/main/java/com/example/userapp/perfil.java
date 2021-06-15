package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.objects.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class perfil extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Button logOut, newCita, verCitas;
    private User user;
    private TextView usuarioN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        logOut = findViewById(R.id.logOutBtn);

        usuarioN = findViewById(R.id.usuarioName);
        newCita = findViewById(R.id.soliCitaBtn);
        verCitas = findViewById(R.id.verCitas);


        logOut.setOnClickListener(this);
        newCita.setOnClickListener(this);
        verCitas.setOnClickListener(this);

        if (auth.getCurrentUser() == null) {

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            db.getReference().child("clientes").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot data) {
                            user = data.getValue(User.class);

                            usuarioN.setText(user.nombre);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    }
            );
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logOutBtn:
                auth.signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.soliCitaBtn:
                Intent intent = new Intent(this, solicitarCitaDatosPersonales.class);
                intent.putExtra("id",user.id);
                startActivity(intent);
                finish();
                break;
            case R.id.verCitas:
                Intent m = new Intent(this, citas.class);
                m.putExtra("id",user.id);
                startActivity(m);
                finish();
                break;
        }
    }
}