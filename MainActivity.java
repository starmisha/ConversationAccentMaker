package com.example.phone_zipper;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class MainActivity extends AppCompatActivity {
    private TextView textTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    private void init()
    {
        textTest = findViewById(R.id.textTest);
    }
    public void onClickMic(View view)

    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 10);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null)
        {
            switch (requestCode)
            {
                case 10:
                    ArrayList<String> text =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert text != null;
                    analise(text.get(0));
                    break;

            }
        }
    }
    private void analise (String text) {
        List<String> msg = new ArrayList<String>();
        String regx = "^[А-Я][а-я]*(-[А-Я][а-я]*)?$";
        String phone_regx = "\\d{3}-\\d{2}-\\d{7}";
        Pattern pattern = Pattern.compile(regx);
        String[] arrSplit = text.split(" ");

        Pattern pattern_2 = Pattern.compile(phone_regx);
        Matcher matcher = pattern_2.matcher(text);

        for (int t = 0; t < arrSplit.length; t++) {

            if (arrSplit[t].matches(regx))   {
                msg.add(arrSplit[t]);



            }
            if (arrSplit[t].matches(phone_regx)) {
                msg.add(arrSplit[t]);


            }}

        String str = TextUtils.join(",", msg);
        textTest.setText(str);
        intentMessage(str, "phone", "lisovoy555mikhail@gmail.com");
        intent_date(str);
    }



    void intentMessage(String msg, String sub, String e_mail) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, e_mail);
        email.putExtra(Intent.EXTRA_EMAIL, e_mail);
        email.putExtra(Intent.EXTRA_SUBJECT, sub);
        email.putExtra(Intent.EXTRA_TEXT, msg);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }
    void intent_date(String text) {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "note");
        intent.putExtra("description", text);
        startActivity(intent);}
    public void Transform(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");


        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKey key = kgen.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] bytes = cipher.doFinal(str.getBytes());
        for (byte byt : bytes); {
            System.out.println(b);
        }




    }
    }



