package com.example.confidante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private ArrayList<String> notes;
    private ArrayAdapter<String> notesAdapter;
    private ListView listView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        listView = findViewById(R.id.notes_list_view);
        button = findViewById(R.id.add_note_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote(v);
            }
        });

        notes = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(notesAdapter);
        setListViewListener();
        notes.add("1st Note");
        notes.add("2nd Note");
        notes.add("3rd Note");
        notes.add("4th Note");
        notes.add("5th Note");

    }

    private void setListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Note removed", Toast.LENGTH_SHORT).show();

                notes.remove(position);
                notesAdapter.notifyDataSetChanged();
                return true;

            }
        });
    }

    private void addNote(View view){
        EditText input = findViewById(R.id.notes_add_editText);
        String noteText = input.getText().toString();

        if(!(noteText.equals(""))){
            notesAdapter.add(noteText);
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please add text", Toast.LENGTH_SHORT).show();
        }
    }
}