package com.example.expertportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    TextView register;
    EditText editTextName;
    EditText editTextPassword;
    Button login;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName=findViewById(R.id.editTextName);

        editTextPassword=findViewById(R.id.editTextPassword);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent i = new Intent(getApplicationContext(),HomePage.class);
            startActivity(i);
        }

        login=findViewById(R.id.login);


        register=findViewById(R.id.register);

        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=editTextName.getText().toString().trim();

                String password=editTextPassword.getText().toString().trim();

                if(email.isEmpty()){
                    editTextName.setError("Enter name");
                    editTextName.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextName.setError("Email is not correct");
                    editTextName.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    editTextPassword.setError("Enter password");
                    editTextPassword.requestFocus();
                    return;
                }

                if(password.length()<6){
                    editTextPassword.setError("password is too short ");
                    editTextPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()){
                                Intent i = new Intent(getApplicationContext(),HomePage.class);
                                startActivity(i);
                            }
                            else{
                                user.sendEmailVerification();
                                Toast.makeText(getApplicationContext(), "Check your email to verify", Toast.LENGTH_SHORT).show();
                            }



                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });



    }
}