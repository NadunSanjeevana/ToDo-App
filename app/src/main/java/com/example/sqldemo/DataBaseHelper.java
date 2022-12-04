package com.example.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    String customer_table = "CUSTOMER_TABLE";
    String column_customer_name = "CUSTOMER_NAME";
    String column_customer_age = "CUSTOMER_AGE";
    String column_id = "ID";
    String column_active_customer = "ACTIVE_CUSTOMER";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String createTableStatement = "CREATE TABLE " + customer_table + " (" + column_id + " INTEGER PRIMARY KEY AUTOINCREMENT," + column_customer_name + " TEXT," + column_customer_age + " INTEGER, " + column_active_customer + " BOOL)";
        sqLiteDatabase.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column_customer_name,customerModel.getName());
        cv.put(column_customer_age,customerModel.getAge());
        cv.put(column_active_customer,customerModel.isActive());

        long insert = db.insert(customer_table, null, cv);
        if(insert >= 0){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean deleteOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + customer_table + " WHRER " + column_id + " = " + customerModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + customer_table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean custermerActive = cursor.getInt(3) == 1;

                CustomerModel newCustomer = new CustomerModel(customerID,customerName,customerAge,custermerActive);
                returnList.add(newCustomer);
            }while (cursor.moveToNext());

        }
        else{
            //empty list

        }
        cursor.close();;
        db.close();
        return returnList;
    }
}
