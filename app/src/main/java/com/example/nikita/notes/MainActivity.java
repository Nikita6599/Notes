package com.example.nikita.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

      ListView listView;
      static ArrayList<String> notes= new ArrayList<String>();
      static ArrayAdapter<String> arrayAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.addNote) {
           Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
            startActivity(intent);
            return true;

        }
        return false;
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            listView = (ListView) findViewById(R.id.listView);
            SharedPreferences sp=getApplicationContext().getSharedPreferences("com.example.nikita.notes", Context.MODE_PRIVATE);
            HashSet<String> set=(HashSet<String>) sp.getStringSet("notes",null);
            if(set==null){
                notes.add("Example Note");
            }
            else{
                notes=new ArrayList(set);
            }



            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);


            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    intent.putExtra("noteId",position);
                    startActivity(intent);
                }

            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){


                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    final int itemToDelete=position;

                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Delete the note?")
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notes.remove(itemToDelete);
                                    arrayAdapter.notifyDataSetChanged();
                                    SharedPreferences sp=getApplicationContext().getSharedPreferences("com.example.nikita.notes", Context.MODE_PRIVATE);
                                    HashSet<String> set=new HashSet<String>(MainActivity.notes);
                                    sp.edit().putStringSet("notes",set).apply();



                                }
                            })
                            .setNegativeButton("No",null)
                            .show();




                    return true;
                }
            });


        }


    }