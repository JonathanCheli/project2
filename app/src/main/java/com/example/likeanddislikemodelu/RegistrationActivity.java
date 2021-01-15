package com.example.likeanddislikemodelu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import android.widget.Button;
import android.widget.Toast;
public class RegistrationActivity extends AppCompatActivity {

    private  FirebaseAuth myAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStatelistener;


    EditText editEmail,editPassword;

    Button btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editPassword = findViewById(R.id.password_id);
        editEmail = findViewById(R.id.email_id);

        btnReg = findViewById(R.id.btnReg_id);

        myAuth = FirebaseAuth.getInstance();
        firebaseAuthStatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        btnReg.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                final String email  = editEmail.getText().toString();
                final String password  = editPassword.getText().toString();
                myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegistrationActivity.this, "Sing up error.." + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                );}

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myAuth.addAuthStateListener(firebaseAuthStatelistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAuth.removeAuthStateListener(firebaseAuthStatelistener);
    }
}