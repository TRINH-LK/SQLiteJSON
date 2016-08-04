package com.example.trinhle.sqlitejson.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.trinhle.sqlitejson.Category;
import com.example.trinhle.sqlitejson.CategoryListener;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 02/08/2016.
 */
public class DBHandler extends SQLiteOpenHelper implements CategoryListener {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "category.db";
    private static final String TABLE_NAME = "category";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NUM_COL = "num_col";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + KEY_CATEGORY_ID  + " INTEGER PRIMARY KEY, " + KEY_CATEGORY_NAME + " TEXT,"
            + KEY_DESCRIPTION + " TEXT," + KEY_NUM_COL + " TEXT)";

    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(KEY_CATEGORY_ID,category.getCategoryId());
            values.put(KEY_CATEGORY_NAME,category.getCategoryName());
            values.put(KEY_DESCRIPTION,category.getDescription());
            values.put(KEY_NUM_COL,category.getNumCol());
            db.insert(TABLE_NAME,null,values);
            db.close();
        } catch (Exception e) {
            Log.e("Error",e+ "");
        }
    }

    @Override
    public ArrayList<Category> getAllCategory() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Category> categories = null;

        try {
            categories = new ArrayList<Category>();
            String query = " SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query,null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Category category = new Category();
                    category.setCategoryId(cursor.getString(0));
                    category.setCategoryName(cursor.getString(1));
                    category.setDescription(cursor.getString(2));
                    category.setNumCol(cursor.getInt(3));
                    categories.add(category);
                }
            }
            db.close();
        } catch (Exception e){
            Log.e("Error", e+ "");
        }
        return categories;
    }

    @Override
    public int getCategoryCount() {
        int num = 0;
        SQLiteDatabase db = getReadableDatabase();
        try {
            String query = " SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            num = cursor.getCount();
            db.close();
            return num;
        } catch (Exception e) {
            Log.e("Error", e+ "");
        }
        return num;
    }
}
