package com.example.nikita.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

public class SecondActivity extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        EditText editText=(EditText)findViewById(R.id.editText);
        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        if(noteId!=-1) {
            editText.setText(MainActivity.notes.get(noteId));
        }else{
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();


        }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   MainActivity.notes.set(noteId,String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sp=getApplicationContext().getSharedPreferences("com.example.nikita.notes", Context.MODE_PRIVATE);
                    HashSet<String> set=new HashSet<String>(MainActivity.notes);
                    sp.edit().putStringSet("notes",set).apply();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }



    }

