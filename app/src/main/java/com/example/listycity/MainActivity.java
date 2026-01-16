package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
// AndroidStudio suggested the fix "implements OnClickListener"
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String []cities = {"Rome","London","Moscow","Berlin","Changsha","Tokyo","Shanghai","New York","Osaka","Paris"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRemove = findViewById(R.id.btnRemove);
        // from Lab demo
        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        /*The following lines is adapted from OpenAI, ChatGPT,
        "How do I use onclickListener on a Listview to select an item from a dataList?"*/
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPos = position;
            String clicked = dataList.get(position);
            Toast.makeText(this , "SELECTED: " + clicked.toUpperCase(), Toast.LENGTH_SHORT).show();
        });
    }

    // followed the LAB demo and instructions from "Build Your First Android App in Java"
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.btnAdd){
            addingCity();
        } else if (id == R.id.btnRemove){
            removeSelectedCity();
        }
    }

    /*The following method is adapted from OpenAI, ChatGPT, "how to build a text input alert dialog,
    I can later put the input into a list in android studio (Java)"*/
    public void addingCity(){
        EditText input = new EditText(this);
        input.setHint("ENTER A CITY BY NAME");

        new AlertDialog.Builder(this)
                .setTitle("ADDING CITY")
                .setView(input)
                .setPositiveButton("CONFIRM", (d, w) -> {
                    String newCity = input.getText().toString().trim();
                    // Check for empty input
                    if (newCity.isEmpty()){
                        Toast.makeText(this, "CITY NAME CAN NOT BE EMPTY.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Check for duplicate in the List
                    for (String cityName : dataList){
                        if (cityName.equalsIgnoreCase(newCity)){
                            Toast.makeText(this, "CITY ALREADY EXIST IN LIST", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    dataList.add(newCity);
                    /*The following method is from OpenAI ChatGPT,"if I update a datalist in the Java studio(java),
                     why isn't my Listview changes?"*/
                    cityAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("CANCEL", null).show();
    }

    public void removeSelectedCity(){
        // Check for empty dataList
        if (dataList.isEmpty()) {
            Toast.makeText(this, "CANNOT REMOVE FROM EMPTY LIST", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check for out of bound access
        if (selectedPos < 0 || selectedPos > dataList.size()){
            Toast.makeText(this, "TAP ON A CITY TO SELECT IT", Toast.LENGTH_SHORT).show();
            return;
        }
        dataList.remove(selectedPos);
        // Remembered to notify adapter this time LOL.
        cityAdapter.notifyDataSetChanged();
        // Reset the pointer to -1
        selectedPos = -1;
    }

    // this is from AndroidStudio fixing "implements OnClickListener"
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

