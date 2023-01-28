package com.hamitmizrak.peertopeermessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailSendActivity extends AppCompatActivity {

    //Global Varible
    private EditText editTextWhoEmailId;
    private EditText editTextEmailSubjectId;
    private EditText editTextContentId;
    private Button buttonSubmitEmailId;

    //Mail Get
   private  String getTextWhoEmailId;
   private  String getTextEmailSubjectId;
   private  String getTextContentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_send);
        //start

        editTextWhoEmailId=findViewById(R.id.editTextWhoEmailId);
        editTextEmailSubjectId=findViewById(R.id.editTextEmailSubjectId);
        editTextContentId=findViewById(R.id.editTextContentId);
        buttonSubmitEmailId=findViewById(R.id.buttonSubmitEmailId);

        //Button Click
        buttonSubmitEmailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextWhoEmailId=editTextWhoEmailId.getText().toString();
                getTextEmailSubjectId=editTextEmailSubjectId.getText().toString();
                getTextContentId=editTextContentId.getText().toString();
                Toast.makeText(EmailSendActivity.this, getTextWhoEmailId+" "+getTextEmailSubjectId+" "+getTextContentId, Toast.LENGTH_LONG).show();


                //Intent
                Intent intentMail=new Intent(Intent.ACTION_SEND);
                intentMail.putExtra(Intent.EXTRA_EMAIL,new String[]{getTextWhoEmailId});
                intentMail.putExtra(Intent.EXTRA_SUBJECT,getTextEmailSubjectId);
                intentMail.putExtra(Intent.EXTRA_TEXT,getTextContentId);

                //setType
                intentMail.setType("message/rfc822");
                startActivity(Intent.createChooser(intentMail,"Seçim: Email Client end"));
            }
        });


    }//end onCreate

}//end EmailSendActivity