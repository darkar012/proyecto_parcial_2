package com.example.userapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.objects.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {
    private EditText nameET, numberET, emailET, passwordET, repasswordET;
    private Button signupBtn;

    private TextView loginLink;
    private FirebaseDatabase db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        nameET = findViewById(R.id.nombreET);
        numberET = findViewById(R.id.numeroET);
        emailET = findViewById(R.id.correoETRegister);
        passwordET = findViewById(R.id.passwordETregister);
        repasswordET = findViewById(R.id.repasswordETregister);
        signupBtn = findViewById(R.id.registerBtn);
        loginLink = findViewById(R.id.loginLink);

        loginLink.setOnClickListener(this);
        signupBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginLink:
                finish();
                break;

            case R.id.registerBtn:

                String nombre = nameET.getText().toString();
                String numero = numberET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String repassword = repasswordET.getText().toString();

                Log.e(">>>", password +", "+ repassword);

                if (nombre.isEmpty() || nombre == "" || numero.isEmpty() || numero == "" || email.isEmpty() || email == "" || password.isEmpty() || password == "" || repassword.isEmpty() || repassword == "") {
                    Toast.makeText(this, "Tiene que llenar todos los campos", Toast.LENGTH_LONG).show();

                } else if (password.equals(repassword)) {
                    auth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnSuccessListener(
                        response -> {
                            String uid = auth.getCurrentUser().getUid();
                            User user = new User(uid, nombre,numero, email);
                            db.getReference().child("clientes").child(user.id).setValue(user).addOnSuccessListener(
                                    dbresponse -> {
                                        Intent i = new Intent(this, perfil.class);
                                        startActivity(i);
                                        finish();
                                    }
                            );
                        }
                ).addOnFailureListener(
                        error -> {
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                );

                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}