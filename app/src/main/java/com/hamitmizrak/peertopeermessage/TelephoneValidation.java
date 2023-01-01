package com.hamitmizrak.peertopeermessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TelephoneValidation extends AppCompatActivity {
    //Global Varaible
    private EditText phoneNumberEditTextId;
    private EditText phoneCodeEditTextId;
    private Button confirmationButtonId;

    //call back data
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbackData;

    //Firebase Kimlik
    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    DatabaseReference databaseReference;

    private String recognationId;

    //Eğer Doğrulama yapılmamışsa bu metot çalışsın
    public void verificationTelephone() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumberEditTextId.getText().toString(), 60, TimeUnit.SECONDS, this, callbackData);
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_validation);
        //Start
        phoneNumberEditTextId = findViewById(R.id.phoneNumberEditTextId);
        phoneCodeEditTextId = findViewById(R.id.phoneCodeEditTextId);
        confirmationButtonId = findViewById(R.id.confirmationButtonId);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //callback
        callbackData = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Giriş başarılı ise
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("persons").child(firebaseUser.getUid());

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        //Kullanıcı sistemde varsa Admin Sayfasına yönlendir.
                                        Intent adminPage = new Intent(getApplicationContext(), AdminActivity.class);
                                        startActivity(adminPage);
                                    } else {
                                        HashMap<String, Object> userHashMap = new HashMap<>();
                                        userHashMap.put("Name", "Hamit");
                                        userHashMap.put("Surname", "Mizrak");
                                        userHashMap.put("Phone", "");
                                        userHashMap.put("Status", "HEllo");
                                        userHashMap.put("Phone", firebaseUser.getPhoneNumber());
                                        databaseReference.updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Kullanıcı sistemde varsa Admin Sayfasına yönlendir.
                                                    Intent adminPage = new Intent(getApplicationContext(), AdminActivity.class);
                                                    startActivity(adminPage);
                                                }//end if

                                            }//end complete
                                        }); // end databaseReference
                                    }// end else
                                } //end onDataChange

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("On Cancelled","Çıkış yapıldı");
                                    Toast.makeText(TelephoneValidation.this, "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
                                } //end
                            }); //end databaseReference

                        }
                    }
                });
            }

            //doğruılama hatası
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Doğrulama Hatası: ",e.getMessage());
                Toast.makeText(TelephoneValidation.this, "Doğrulama Hatası: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }// end onVerificationFailed

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                recognationId=s;
                confirmationButtonId.setText("Onaylama yapabilirsiniz");
                //Onaylama Sonrasında
                phoneNumberEditTextId.setVisibility(View.INVISIBLE);
                phoneCodeEditTextId.setVisibility(View.VISIBLE);
            } // end onCodeSent
        };//end callbackData

        // ++++++++++++++++++++++
        confirmationButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recognationId!=null){
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(recognationId,phoneCodeEditTextId.getText().toString());
                    //AYNISI
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Giriş başarılı ise
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("persons").child(firebaseUser.getUid());

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            //Kullanıcı sistemde varsa Admin Sayfasına yönlendir.
                                            Intent adminPage = new Intent(getApplicationContext(), AdminActivity.class);
                                            startActivity(adminPage);
                                        } else {
                                            HashMap<String, Object> userHashMap = new HashMap<>();
                                            userHashMap.put("Name", "Hamit");
                                            userHashMap.put("Surname", "Mizrak");
                                            userHashMap.put("Phone", "");
                                            userHashMap.put("Status", "HEllo");
                                            userHashMap.put("Phone", firebaseUser.getPhoneNumber());
                                            databaseReference.updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        //Kullanıcı sistemde varsa Admin Sayfasına yönlendir.
                                                        Intent adminPage = new Intent(getApplicationContext(), AdminActivity.class);
                                                        startActivity(adminPage);
                                                    }//end if

                                                }//end complete
                                            }); // end databaseReference
                                        }// end else
                                    } //end onDataChange

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("On Cancelled","Çıkış yapıldı");
                                        Toast.makeText(TelephoneValidation.this, "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
                                    } //end
                                }); //end databaseReference
                            } //end isSuccessful
                        }//end onComplete
                    }); //end signInWithCredential
                }else{
                    verificationTelephone();
                }
            }
        });

    } //end oncreate
} //end class