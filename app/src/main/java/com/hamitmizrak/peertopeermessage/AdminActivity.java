package com.hamitmizrak.peertopeermessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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