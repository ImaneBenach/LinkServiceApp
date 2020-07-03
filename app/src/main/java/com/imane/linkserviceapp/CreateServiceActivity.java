package com.imane.linkserviceapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.VersionInfo;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.ServiceInfos.ServiceEvaluationActivity;
import com.imane.linkserviceapp.ServicesList.ServicesListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CreateServiceActivity extends AppCompatActivity {

    private final Gson gson = new Gson();

    DatePickerDialog.OnDateSetListener dateSetListener, deadlineSetListener;
    EditText etDate, etDeadLine, newServiceName, newServiceDescription;
    Spinner newServiceTypeService;
    Button btn_create_service;

    User userConnected;
    ArrayList<TypeService> typeServices;

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
                        year, month, day);
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
                        year, month, day);
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

        getAllTypesService();
        ArrayList<String> arrayList = getStringListTypeSerice();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newServiceTypeService.setAdapter(arrayAdapter);
        newServiceTypeService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeServiceSelected = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + typeServiceSelected, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_create_service = findViewById(R.id.btn_create_service);
        btn_create_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFormCreate(view);
            }
        });

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private ArrayList<String> getStringListTypeSerice() {
        int counter = 0;
        ArrayList<String> typesServicesString = new ArrayList<>();
        for (counter = 0; counter < typeServices.size(); counter++) {
            typesServicesString.add(typeServices.get(counter).getName());
        }
        return typesServicesString;
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

    private void submitFormCreate(View view) {
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        newServiceTypeService = findViewById(R.id.newServiceTypeService);
        newServiceName = findViewById(R.id.newServiceName);
        newServiceDescription = findViewById(R.id.newServiceDescription);
        etDate = findViewById(R.id.etDate);
        etDeadLine = findViewById(R.id.etDeadLine);
        String typeService = newServiceTypeService.getSelectedItem().toString();

        int idTypeService = getIdTypeService(typeService);

        if (idTypeService < 0) {
            newServiceTypeService.setBackgroundResource(R.drawable.field_border_error);
        } else {
            newServiceTypeService.setBackgroundColor(Color.argb(200, 255, 255, 255));
        }

        if (checkFieldTextEdit(newServiceName) && checkFieldTextEdit(newServiceDescription) && checkFieldTextEdit(etDate) && checkFieldTextEdit(etDeadLine)) {

            if(userConnected.buyService(1)){
                try {
                    jsonParamValues.put("name", newServiceName.getText());
                    jsonParamValues.put("description", newServiceDescription.getText());
                    jsonParamValues.put("id_type", idTypeService);
                    jsonParamValues.put("date", etDate.getText());
                    jsonParamValues.put("deadline", etDeadLine.getText());
                    jsonParamValues.put("cost", 1);
                    jsonParamValues.put("profit", 1);
                    jsonParamValues.put("access", "general");
                    jsonParamValues.put("id_creator", userConnected.getId());

                    jsonParam.put("table", "service");
                    jsonParam.put("values", jsonParamValues);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    API.sendRequest(jsonParam.toString(), "create");
                    displayPopUpServiceCreated(view);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                displayPopUpError(view);
            }
        }
    }

    private void getAllTypesService() {
        typeServices = new ArrayList<>();
        HashMap<String, TypeService> typeData = new HashMap<>();
        JSONObject jsonParam = new JSONObject();
        String typeServicesListAsString = "";

        int counter;

        TypeService undefinedType = new TypeService(-1, "--TypeService--", "", "", 1);
        typeServices.add(undefinedType);

        try {
            jsonParam.put("table", "type_service");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            typeServicesListAsString = API.sendRequest(jsonParam.toString(), "readAll");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        if (!typeServicesListAsString.equals("")) {
            if (typeServicesListAsString.startsWith("i", 2)) {
                typeData.put("0", gson.fromJson(typeServicesListAsString, TypeService.class));
            } else {
                typeData = API.decodeResponseMultipleAsTypeService(typeServicesListAsString);
            }
            for (counter = 0; counter < typeData.size(); counter++) {
                TypeService type = typeData.get(Integer.toString(counter));
                typeServices.add(type);
            }
        }
    }

    private int getIdTypeService(String nameTypeService) {
        int counter;
        for (counter = 0; counter < typeServices.size(); counter++) {
            if (typeServices.get(counter).getName() == nameTypeService) {
                return typeServices.get(counter).getId();
            }
        }
        return -1;
    }

    private boolean checkFieldTextEdit(TextView field) {
        if (field.length() <= 0) {
            field.setBackgroundResource(R.drawable.field_border_error);
            return false;
        }
        field.setBackgroundColor(Color.argb(200, 255, 255, 255));
        return true;

    }

    private void displayPopUpError(View view){

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_error_creation_service, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            Button btnCancel = popupView.findViewById(R.id.btn_cancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    intent.putExtra("userConnected", userConnected);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });
    }

    private void displayPopUpServiceCreated(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_service_created, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btnCancel = popupView.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
