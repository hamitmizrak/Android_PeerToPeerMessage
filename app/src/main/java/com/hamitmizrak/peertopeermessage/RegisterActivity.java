package com.hamitmizrak.peertopeermessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    //global variable
    private EditText register_editText_EmailAddress;
    private EditText register_editText_Password;
    private Button register_button_submit;

    //Firebase işlemleri
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    //Email,password
    private String userEmailAddress, userPassword;

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //start
        //id almak
        register_editText_EmailAddress = findViewById(R.id.register_editText_EmailAddress);
        register_editText_Password = findViewById(R.id.register_editText_Password);
        register_button_submit = findViewById(R.id.register_button_submit);

        //Firebase Instance
        firebaseAuth=FirebaseAuth.getInstance();

        //button onClick
        register_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kullanıcının girdiği email ve password almak
                userEmailAddress =register_editText_EmailAddress.getText().toString();
                userPassword =register_editText_Password.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(userEmailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //onComplete-1
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Toast
                        Toast.makeText(RegisterActivity.this, getString(R.string.login_account), Toast.LENGTH_SHORT).show();
                        firebaseAuth.signInWithEmailAndPassword(userEmailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            //onComplete-2
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Intent adminIntent = new Intent(getApplicationContext(), AdminActivity.class);
                                Toast.makeText(RegisterActivity.this, getString(R.string.admin_redirect), Toast.LENGTH_SHORT).show();
                                startActivity(adminIntent);
                            } //end onComplete
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.login_faile), Toast.LENGTH_SHORT).show();
                            } //end onFailure
                        });  //end addOnFailureListener
                    }// end onComplete
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.not_register), Toast.LENGTH_SHORT).show();
                    }
                }); //end createUserWithEmailAndPassword
            } //// end  onClick
        });

    }//end onCreate
} // end RegisterActivity