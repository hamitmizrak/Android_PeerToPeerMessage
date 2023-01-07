package com.hamitmizrak.peertopeermessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class AdminActivity extends AppCompatActivity {
    //global variable
    private Toolbar myToolBarId;

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu ıtem
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuChooiseItem=item.getItemId();
        switch (menuChooiseItem){

            case R.id.adminMenuRefleshId:
                Toast.makeText(this, "Reflesh Tıklandı", Toast.LENGTH_SHORT).show();
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


            case R.id.adminMenuLogoutId:
                Toast.makeText(this, "Logout Tıklandı", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "Seçili alanlar Tıklanmadı", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //start
        myToolBarId=findViewById(R.id.myToolBarId);
        myToolBarId.setTitle("Admin Page");
        //myToolBarId.setSubtitle("Admin App");
        myToolBarId.setLogo(R.drawable.logo);
        setSupportActionBar(myToolBarId);


    }//end
} // AdminActivity