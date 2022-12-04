package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    //reference to buttons and other controls on the layout
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;

    ArrayAdapter customerArrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add  = findViewById(R.id.btn_Add);
        btn_viewAll = findViewById(R.id.btn_ViewAll);
        et_age = findViewById(R.id.etAge);
        et_name = findViewById(R.id.et_Name);
        sw_activeCustomer = findViewById(R.id.sw_Active);
        lv_customerList = findViewById(R.id.lv_CustomerList);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        ShowCustomerOnListView(dataBaseHelper);

        //button listeners for each add and click buttons
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

               CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1, et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
                    Toast.makeText(MainActivity.this,customerModel.toString(),Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Add button",Toast.LENGTH_LONG).show();
                    customerModel = new CustomerModel(-1,"error",0,false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this,"Success " + success,Toast.LENGTH_LONG).show();

                ShowCustomerOnListView(dataBaseHelper);


            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(MainActivity.this,everyone.toString(),Toast.LENGTH_LONG).show();

                ShowCustomerOnListView(dataBaseHelper);

            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                CustomerModel clickedCustomer = (CustomerModel) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedCustomer);
                Toast.makeText(MainActivity.this,"DELETED " +clickedCustomer,Toast.LENGTH_LONG).show();


            }
        });


    }

    private void ShowCustomerOnListView(DataBaseHelper dataBaseHelper) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}