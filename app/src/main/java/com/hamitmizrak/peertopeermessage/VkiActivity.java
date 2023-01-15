package com.hamitmizrak.peertopeermessage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class VkiActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    //global variable
    private SeekBar seekBarVkiId;
    private RadioGroup radioGroupVkiId;
    private EditText editTextVki;
    private TextView textViewVkiKiloId;
    private TextView textViewVkiBoyId;
    private TextView textViewVkiIdealId;
    private TextView textViewVkiDurumId;

    private double height;
    private double weight = 60;
    private double idealWeightMale;
    private double idealWeightFemale;
    private double vki;
    private boolean isFemale = true;

    //my special method
    private void updateHeightWeightGender() {
        textViewVkiBoyId.setText(String.valueOf(height)+" CM");
        textViewVkiKiloId.setText(String.valueOf(weight)+" KG");

        if(height>0){
            //erkek
            idealWeightMale=(50+2.3*(height*100*0.4-60));

            //bayan
            idealWeightFemale=(45.5+2.3*(height*100*0.4-60));
        }
        //Vki formülü
        vki=weight/(height*height);

        //Bayansa
        if(isFemale){
            //zayıf
            if(vki<=19.1){
                //zayıf
                textViewVkiDurumId.setBackgroundResource(R.color.zayif);
                textViewVkiDurumId.setText(R.string.zayif);
            }else if(19.1<vki && vki<=25.5){
                //normal
                textViewVkiDurumId.setBackgroundResource(R.color.normal);
                textViewVkiDurumId.setText(R.string.normal);
            }else if(25.5<vki && vki<=27.4){
                //normaldan fazla
                textViewVkiDurumId.setBackgroundResource(R.color.normalden_fazla);
                textViewVkiDurumId.setText(R.string.normalden_fazla);
            }else if(27.4<vki && vki<=32.4){
                // fazla
                textViewVkiDurumId.setBackgroundResource(R.color.fazla);
                textViewVkiDurumId.setText(R.string.fazla);
            }else if(32.4<vki && vki<=35){
                // obez
                textViewVkiDurumId.setBackgroundResource(R.color.obez);
                textViewVkiDurumId.setText(R.string.obez);
            }else{
                // obez üstü
                textViewVkiDurumId.setBackgroundResource(R.color.obez_ustu);
                textViewVkiDurumId.setText(R.string.obez_ustu);
            }
        }else{ // Erkekse 1099
            //zayıf
            if(vki<=20.7){
                //zayıf
                textViewVkiDurumId.setBackgroundResource(R.color.zayif);
                textViewVkiDurumId.setText(R.string.zayif);
            }else if(20.7<vki && vki<=26.4){
                //normal
                textViewVkiDurumId.setBackgroundResource(R.color.normal);
                textViewVkiDurumId.setText(R.string.normal);
            }else if(26.4<vki && vki<=27.7){
                //normaldan fazla
                textViewVkiDurumId.setBackgroundResource(R.color.normalden_fazla);
                textViewVkiDurumId.setText(R.string.normalden_fazla);
            }else if(27.7<vki && vki<=31){
                // fazla
                textViewVkiDurumId.setBackgroundResource(R.color.fazla);
                textViewVkiDurumId.setText(R.string.fazla);
            }else if(31<vki && vki<=35){
                // obez
                textViewVkiDurumId.setBackgroundResource(R.color.obez);
                textViewVkiDurumId.setText(R.string.obez);
            }else{
                // obez üstü
                textViewVkiDurumId.setBackgroundResource(R.color.obez_ustu);
                textViewVkiDurumId.setText(R.string.obez_ustu);
            }
        }
    }

    //SeekBar
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        weight = i + 50;
        updateHeightWeightGender();
    }

    //TextWatcher
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            height = Double.valueOf(charSequence.toString()) / 100.0;
        } catch (NumberFormatException numberFormatException) {
            height = 0.0;
        }
        updateHeightWeightGender();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //Radio Group
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.bayan) {
            isFemale = true;
        } else {
            isFemale = false;
        }
        updateHeightWeightGender();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vki);
        //
        seekBarVkiId=findViewById(R.id.seekBarVkiId);
        radioGroupVkiId=findViewById(R.id.radioGroupVkiId);
        editTextVki=findViewById(R.id.editTextVki);
        textViewVkiKiloId=findViewById(R.id.textViewVkiKiloId);
        textViewVkiBoyId=findViewById(R.id.textViewVkiBoyId);
        textViewVkiIdealId=findViewById(R.id.textVkiIdealId);
        textViewVkiDurumId=findViewById(R.id.textViewVkiDurumId);

        editTextVki.addTextChangedListener(this);
        seekBarVkiId.setOnSeekBarChangeListener(this);
        radioGroupVkiId.setOnCheckedChangeListener(this);

        updateHeightWeightGender();
    }
}