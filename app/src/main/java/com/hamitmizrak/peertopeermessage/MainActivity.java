package com.hamitmizrak.peertopeermessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //global variable
    EditText main_editText_email;
    EditText main_editText_password;

    //login
    Button main_button_login;

    //register
    Button main_button_register;

    //user
    String userEmailAddress, userPassword;

    //Firebase User
    private FirebaseUser firebaseUser;

    //Firebase giriş
    private FirebaseAuth firebaseAuth;

    //Firebase kullanıcısının giriş/çıkış işlemlerinde kullanılır
    private FirebaseAuth.AuthStateListener authStateListener;

    //Firebase kullanıcı eklemek
    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth'a kullanıcı eklemek
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    //firebaseAuth kullanıcı çıkarmak
    @Override
    protected void onStop() {
        super.onStop();
        //Firebase kullanıcıyı çıkarmak
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start codes

        //id almak
        main_editText_email = findViewById(R.id.main_editText_email);
        main_editText_password = findViewById(R.id.main_editText_password);
        main_button_login = findViewById(R.id.main_button_login);
        main_button_register = findViewById(R.id.main_button_register);

        //Firebase Instance
        firebaseAuth = FirebaseAuth.getInstance();

        //Kullanıcı Sisteme giriş yapmış mı?
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Kullanıcı bilgileri getirmek
                firebaseUser = firebaseAuth.getCurrentUser();
                //eğer sistemde bir kullanıcı varsa
                if (firebaseUser != null) {
                    Intent adminIntent = new Intent(getApplicationContext(), AdminActivity.class);
                    //Toast'a String bir ifade göndermek istersek
                    Toast.makeText(MainActivity.this, getString(R.string.admin_redirect), Toast.LENGTH_SHORT).show();
                    startActivity(adminIntent);
                }//end if
            }// end onAuthStateChanged
        }; // end authStateListener

        //Login butona clicked
        main_button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login butonuna tıkladığımda userEmail ve password alalım.
                userEmailAddress = main_editText_email.getText().toString();
                userPassword = main_editText_password.getText().toString();

                //input validation
                if(userPassword==null || userPassword.equals("") || userEmailAddress==null || userEmailAddress.equals("")){
                    Toast.makeText(MainActivity.this, "Lütfen  boş geçmeyiniz", Toast.LENGTH_SHORT).show();
                }else{
                    //sisteme giriş dinliyoruz.
                    firebaseAuth.signInWithEmailAndPassword(userEmailAddress,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        //eğer sisteme giriş başarılıysa admin sayfasına yönlendir.
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Intent adminIntent = new Intent(getApplicationContext(), AdminActivity.class);
                            //Toast'a String bir ifade göndermek istersek
                            Toast.makeText(MainActivity.this, getString(R.string.admin_redirect), Toast.LENGTH_SHORT).show();
                            startActivity(adminIntent);
                        }
                        //eğer ssiteme giriş yaparken herhangi bir hata alırsam. örneğin: internet olmayabilir. sistemde kullanıcı olmayabilir
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, getString(R.string.admin_faile), Toast.LENGTH_SHORT).show();
                        }// end  onFailure
                    }); // end addOnFailureListener
                }// end else
            }// end onClick
        });// end setOnClickListener

        //Register Button
        main_button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Google sayfasına gitmesi için
                Intent registerIntent=new Intent(getApplicationContext(),RegisterActivity.class);
                Toast.makeText(MainActivity.this, getString(R.string.register_redirect), Toast.LENGTH_SHORT).show();
                startActivity(registerIntent);
            } //end onClick
        }); //end  main_button_register


    } //ends codes
}// class MainActivity