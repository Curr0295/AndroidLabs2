package com.example.androidlabs2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
public class Opener extends SQLiteOpenHelper {
    protected final static String DB_NAME = "TO_DO";
    public static String Table_Name = "To_Do_List";
    public static String Item = " Item";
    public static String Item_id = "Item_Number";
    public static String Urgent = "Urgent";

    public Opener(Context ctx) {
        super(ctx, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_Name + "("
                + Item_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Item + " TEXT, "
                + Urgent + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }


    public List<Todo> loadData() {
        List<Todo> todoItemList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String[] columns = {Opener.Item_id, Opener.Item, Opener.Urgent};
        Cursor results = db.query(false, Opener.Table_Name, columns, null, null, null, null, null, null);

        int itemColumnIndex = results.getColumnIndex("Item");
        int itemIdColumnIndex = results.getColumnIndex("Item_Number");
        int urgentColumnIndex = results.getColumnIndex("Urgent");

        while (results.moveToNext()) {
            String item = results.getString(itemColumnIndex);
            int urgentInt = results.getInt(urgentColumnIndex);
            boolean urgent = (urgentInt == 1);
            int itemId = results.getInt(itemIdColumnIndex);

            Todo todoItem = new Todo(item, urgent, itemId);
            todoItemList.add(todoItem);
        }

        results.close();
        db.close();

        return todoItemList;
    }

    public void addToDB(String itemText, boolean isUrgent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Item, itemText);
        values.put(Urgent, isUrgent ? 1 : 0);

        db.insert(Table_Name, null, values);

        db.close();
    }

    public void deleteFromDB(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Table_Name, Item_id + "=?", new String[]{String.valueOf(itemId)});
        db.close();
    }
    public void printCursor(Cursor c) {
        SQLiteDatabase db = getWritableDatabase();
        Log.d("Opener", "Database Version: " + db.getVersion());

        Log.d("Opener", "Number of Columns: " + c.getColumnCount());
        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.d("Opener", "Column " + i + ": " + c.getColumnName(i));
        }

        Log.d("Opener", "Number of Results: " + c.getCount());

        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                Log.d("Opener", "Row " + c.getPosition() + ", Column " + i + ": " + c.getString(i));
            }
        }

        c.close();
        db.close();
    }
}

