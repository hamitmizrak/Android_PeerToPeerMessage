package com.hamitmizrak.peertopeermessage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminActivity extends AppCompatActivity {
    //global variable
    private Toolbar myToolBarId;

    //Firebase User
    private FirebaseUser firebaseUser;

    //Firebase giriş
    private FirebaseAuth firebaseAuth;

    //Storage Resim
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference userReferances;
    private DatabaseReference mailReferances;
    private DatabaseReference imageReferances;

    private TextView userEmailAddressId;
    private final static int PICTURE_CONST = 44;

    //Firebase kullanıcısının giriş/çıkış işlemlerinde kullanılır
    private FirebaseAuth.AuthStateListener authStateListener;

    //Firebase kullanıcı eklemek
    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth'a kullanıcı eklemek
        //firebaseAuth.addAuthStateListener(authStateListener);
    }

    //firebaseAuth kullanıcı çıkarmak
    @Override
    protected void onStop() {
        super.onStop();
        //Firebase kullanıcıyı çıkarmak
        //firebaseAuth.removeAuthStateListener(authStateListener);
    }

    //Resim için
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CONST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference picturePath = storageReference.child("pictures").child(firebaseAuth.getCurrentUser().getEmail());
            picturePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminActivity.this, "Resminiz Firebase eklendi", Toast.LENGTH_SHORT).show();
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            imageReferances.setValue(imageUrl);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminActivity.this, "Resim yüklenirken sıkıntı çıktı", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu ıtem
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //sistemde bir kullanıcı var mı
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        int menuChooiseItem = item.getItemId();
        switch (menuChooiseItem) {

            case R.id.adminMenuRefleshId:
                if(firebaseUser!=null){
                    Toast.makeText(this, "Reflesh Tıklandı", Toast.LENGTH_SHORT).show();
                    Intent intentReflesh=new Intent(AdminActivity.this,AdminActivity.class);
                    startActivity(intentReflesh);
                }
                break;

            case R.id.adminMenuSettingId:
                Toast.makeText(this, "Ayarlar Tıklandı", Toast.LENGTH_SHORT).show();
                break;


            case R.id.adminMenuChronometreId:
                Toast.makeText(this, "Kronometre Tıklandı", Toast.LENGTH_SHORT).show();
                Intent chronometreIntent = new Intent(getApplicationContext(), Chronometre.class);
                //Toast'a String bir ifade göndermek istersek
                Toast.makeText(AdminActivity.this, "Kronometre", Toast.LENGTH_SHORT).show();
                startActivity(chronometreIntent);
                break;

            case R.id.adminMenuVkiId:
                Toast.makeText(this, "VKI Tıklandı", Toast.LENGTH_SHORT).show();
                Intent vkiIntent = new Intent(getApplicationContext(), VkiActivity.class);
                //Toast'a String bir ifade göndermek istersek
                Toast.makeText(AdminActivity.this, "VKI", Toast.LENGTH_SHORT).show();
                startActivity(vkiIntent);
                break;


            case R.id.adminMenuInfoId:

                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder
                        .append("Model: "+ Build.MODEL)
                        .append(" Üretici Firma: "+Build.MANUFACTURER)
                        .append(" Mac Address:  "+Build.VERSION.SDK_INT);
                String data=stringBuilder.toString();
                Toast.makeText(this, "Info: "+data, Toast.LENGTH_LONG).show();
                break;


            case R.id.adminMenuLogoutId:
                Toast.makeText(this, "Logout Tıklandı", Toast.LENGTH_SHORT).show();
                if (firebaseUser != null) {
                    firebaseAuth.signOut();
                    Toast.makeText(this, "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
                    Intent intentHome = new Intent(AdminActivity.this, MainActivity.class);
                    startActivity(intentHome);
                }
                break;

            default:
                Toast.makeText(this, "Seçili alanlar Tıklanmadı", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Google SignIn
    public void signOutMethod(){
        //
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //start
        myToolBarId = findViewById(R.id.myToolBarId);
        myToolBarId.setTitle("Admin Page");
        //myToolBarId.setSubtitle("Admin App");
        myToolBarId.setLogo(R.drawable.logo);
        setSupportActionBar(myToolBarId);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");

        userEmailAddressId=findViewById(R.id.userEmailAddressId);

        //anasayfada kullanıcı mailini göstermek
        if(firebaseUser!=null){
            String email=firebaseAuth.getCurrentUser().getEmail();
            String name=firebaseAuth.getCurrentUser().getDisplayName();
            userEmailAddressId.setText(email);
        }
        storageReference= FirebaseStorage.getInstance().getReference();

    }//end
} // AdminActivity