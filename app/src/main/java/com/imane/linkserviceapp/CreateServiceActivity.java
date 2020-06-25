package com.imane.linkserviceapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.ServicesList.ServicesListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateServiceActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener dateSetListener, deadlineSetListener;
    EditText etDate, etDeadLine, newServiceName, newServiceDescription;
    Spinner newServiceTypeService;
    Button btn_create_service;

    User userConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        etDate = findViewById(R.id.etDate);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateServiceActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = year + "-" + month + "-" + day;
                etDate.setText(date);
            }
        };

        etDeadLine = findViewById(R.id.etDeadLine);

        etDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateServiceActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        deadlineSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        deadlineSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = year + "-" + month + "-" + day;
                etDeadLine.setText(date);
            }
        };

        newServiceTypeService = findViewById(R.id.newServiceTypeService);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("JAVA");
        arrayList.add("ANDROID");
        arrayList.add("C Language");
        arrayList.add("CPP Language");
        arrayList.add("Go Language");
        arrayList.add("AVN SYSTEMS");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newServiceTypeService.setAdapter(arrayAdapter);
        newServiceTypeService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        btn_create_service = findViewById(R.id.btn_create_service);
        btn_create_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFormCreate();
            }
        });

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, serviceMenuActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submitFormCreate(){
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        newServiceTypeService = findViewById(R.id.newServiceTypeService);
        newServiceName = findViewById(R.id.newServiceName);
        newServiceDescription = findViewById(R.id.newServiceDescription);
        etDate = findViewById(R.id.etDate);
        etDeadLine = findViewById(R.id.etDeadLine);

        try {
            jsonParamValues.put("name",newServiceName.getText());
            jsonParamValues.put("description",newServiceDescription.getText());
            jsonParamValues.put("id_type",4);
            jsonParamValues.put("date",etDate.getText());
            jsonParamValues.put("deadline",etDeadLine.getText());
            jsonParamValues.put("cost",1);
            jsonParamValues.put("profit",1);
            jsonParamValues.put("access","general");
            jsonParamValues.put("id_creator",userConnected.getId());

            jsonParam.put("table", "service");
            jsonParam.put("values",jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
             API.sendRequest(jsonParam.toString(), "create");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
