package com.example.todoitemsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button add;
    EditText etItem;
    RecyclerView rvItems;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItem();
        items.add("Buy Milk");
        items.add("Go to the Gym");
        items.add("Hit Rishabh");

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //Notify the adaptor
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        itemAdapter = new ItemAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //add item to model
                items.add(todoItem);
                //notify adaptor that item is inserted
                itemAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //this function will load items by reading every line of the data file

    private void loadItem() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
           // e.printStackTrace();
            Log.e("MainActivity", "Error reading Items", e);
            items = new ArrayList<>();
        }
    }

    //this function saves items by writing them into the data file

    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e("MainActivity", "Error writing Items", e);
        }
    }
}