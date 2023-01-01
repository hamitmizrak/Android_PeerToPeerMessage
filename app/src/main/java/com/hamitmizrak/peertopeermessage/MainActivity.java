package com.hamitmizrak.peertopeermessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.Inet4Address;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    //global variable
    EditText main_editText_email;
    EditText main_editText_password;

    //login
    Button main_button_login;

    //register
    TextView main_button_register;

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

    //Google Sign in
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    CircleImageView main_button_googlesign;

    //signInMEthod
    private void signInMethod() {
        Intent googleSignIntent = gsc.getSignInIntent();
        //requestCode eşleme
        startActivityForResult(googleSignIntent, 2344);
    }

    //navigateToAdminActivity
    private void navigateToAdminActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    //eğer google giriş başarılı ise AdminActict gitsin
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //start code
        if (requestCode == 2344) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToAdminActivity();
            } catch (ApiException apiException) {
                Toast.makeText(this, "Google Sign In went wrong", Toast.LENGTH_SHORT).show();
            }
        }
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
        // Eğer sistemde bir kullanıcı giriş yapmışsa ADmin sayfasına yönlendirsin
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
                if (userPassword == null || userPassword.equals("") || userEmailAddress == null || userEmailAddress.equals("")) {
                    Toast.makeText(MainActivity.this, "Lütfen  boş geçmeyiniz", Toast.LENGTH_SHORT).show();
                } else {
                    //sisteme giriş dinliyoruz.
                    firebaseAuth.signInWithEmailAndPassword(userEmailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                Toast.makeText(MainActivity.this, getString(R.string.register_redirect), Toast.LENGTH_SHORT).show();
                startActivity(registerIntent);
            } //end onClick
        }); //end  main_button_register

        //+++++++++++++
        //Google Login
        main_button_googlesign=findViewById(R.id.main_button_googlesign);
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc=GoogleSignIn.getClient(this,gso);

        //Eğer Google ile sisteme giriş yapılmışsa tekrar giriş yapmasını engellemek
        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount!=null){
            navigateToAdminActivity();
        }

        // main_button_googlesign setOnClick Listener
        main_button_googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInMethod();
            }//end click
        }); //end setOnClickListener

    } //ends codes
}// class MainActivity