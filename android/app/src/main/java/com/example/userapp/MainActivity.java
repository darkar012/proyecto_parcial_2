package com.example.userapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.objects.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Button loginBtn;
    private ImageButton googleBtn;
    private EditText correoET;
    private EditText passwordET;
    private TextView singupLink;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            Intent i = new Intent(getApplicationContext(), perfil.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        createRequest();

        googleBtn = findViewById(R.id.googleBtn);
        loginBtn = findViewById(R.id.loginBtn);
        correoET = findViewById(R.id.correoET);
        passwordET = findViewById(R.id.passwordET);
        singupLink = findViewById(R.id.signUpLink);

        loginBtn.setOnClickListener(this);
        googleBtn.setOnClickListener(this);
        singupLink.setOnClickListener(this);


    }

    public void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                handleSignInResult(task);
            }
        }
    });

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    User u = new User(user.getUid(), user.getDisplayName(),user.getPhoneNumber(), user.getEmail());

                    db.getReference().child("clientes").child(user.getUid()).setValue(u).addOnSuccessListener(
                            dbresponse -> {
                                Intent i = new Intent(getApplicationContext(), perfil.class);
                                startActivity(i);
                            }
                    );

                } else {
                    Toast.makeText(MainActivity.this, "Sorry auth Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                auth.signInWithEmailAndPassword(correoET.getText().toString(), passwordET.getText().toString()).addOnSuccessListener(
                        command -> {
                            Intent i = new Intent(this, perfil.class);
                            startActivity(i);
                        }
                ).addOnFailureListener(
                        error ->{
                            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                );
                break;

            case R.id.signUpLink:
                Intent intent = new Intent(this, register.class);
                startActivity(intent);
                break;
            case R.id.googleBtn:
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));

break;
        }
    }
}