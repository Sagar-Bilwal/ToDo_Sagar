package com.example.sagar.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SAGAR on 01-03-2018.
 */

public class todo_db extends SQLiteOpenHelper
{
    private static todo_db db_helper;

    public todo_db(Context context) {
        super(context,db_constants.DB_NAME, null, db_constants.VERSION);
    }
    public static todo_db getInstance(Context context)
    {
        if(db_helper==null)
        {
            db_helper=new todo_db(context.getApplicationContext());
        }
        return db_helper;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String todoSQL="CREATE TABLE "+db_constants.todo.TABLE_NAME+" ( "+
                db_constants.todo.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                db_constants.todo.TASK+" TEXT, "+
                db_constants.todo.TIME+" TEXT) ";
        db.execSQL(todoSQL);
    }











    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
