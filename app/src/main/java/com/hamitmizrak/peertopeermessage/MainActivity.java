package com.hamitmizrak.peertopeermessage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


}

//firebaseAuth kullanıcı çıkarmak
@Override
protected void onStop() {
       super.onStop();
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


} //ends codes
}// class MainActivity