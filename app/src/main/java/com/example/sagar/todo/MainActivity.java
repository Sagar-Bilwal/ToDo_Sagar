package com.example.sagar.todo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    todo_adapter modified_adapter;
    todo_db todo_database;
    ArrayList<todo_class> todo_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=findViewById(R.id.listview);
        todo_database=todo_db.getInstance(this);
        todo_list=fetchListFrom_db();
        modified_adapter=new todo_adapter(this,todo_list);
        listView.setAdapter(modified_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this,task_detail.class);
                Bundle bundle =new Bundle();
                todo_class helper=todo_list.get(position);
                bundle.putString(db_constants.todo.TASK,helper.getTask());
                bundle.putString(db_constants.todo.TIME,helper.getTime());
                bundle.putInt(db_constants.todo.POSITION,position);
                intent.putExtras(bundle);
                startActivityForResult(intent,101);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position,final long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Task");
                builder.setMessage("Are you sure you want to delete this task");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase ToDo_Db= todo_database.getWritableDatabase();
                        String[] ID={id+""};
                        ToDo_Db.delete(db_constants.todo.TABLE_NAME,db_constants.todo.ID+" = ?",ID);
                        todo_list.remove(position);
                        modified_adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private ArrayList<todo_class> fetchListFrom_db()
    {
        ArrayList<todo_class> Todo_List=new ArrayList<todo_class>();
        SQLiteDatabase database=todo_database.getReadableDatabase();
        Cursor cursor =database.query(db_constants.todo.TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            int taskColumnIndex = cursor.getColumnIndex(db_constants.todo.TASK);
            String task = cursor.getString(taskColumnIndex);
            String time = cursor.getString(cursor.getColumnIndex(db_constants.todo.TIME));
            long id=cursor.getLong(cursor.getColumnIndex(db_constants.todo.ID));
            todo_class todo_entry=new todo_class(time,task,id);
            Todo_List.add(todo_entry);
        }
        return Todo_List;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add)
        {
            Intent intent = new Intent(this,task_detail.class);
            startActivityForResult(intent,100);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null)
        {
            Bundle bundle=data.getExtras();
            if(requestCode==101)
            {
                if(resultCode==30)
                {
                    if(bundle != null) {
                        int position = bundle.getInt(db_constants.todo.POSITION, -1);
                        if (position >= 0) {
                            todo_class task_time = getToDoFromBundle(bundle);
                            todo_list.set(position,task_time);
                            modified_adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            if(requestCode==100)
            {
                if(resultCode==30)
                {
                    if(bundle!=null)
                    {
                        todo_class todo = getToDoFromBundle(bundle);

                        SQLiteDatabase database = todo_database.getWritableDatabase();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(db_constants.todo.TASK,todo.getTask());
                        contentValues.put(db_constants.todo.TIME,todo.getTime());

                        long id = database.insert(db_constants.todo.TABLE_NAME,null,contentValues);
                        todo.setId(id);
                        todo_list.add(todo);
                        modified_adapter.notifyDataSetChanged();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private todo_class getToDoFromBundle(Bundle bundle)
    {
        if(bundle != null){
            String task = bundle.getString(db_constants.todo.TASK,"");
            String time = bundle.getString(db_constants.todo.TIME,"");
            return new todo_class(time,task);
        }
        return null;
    }
}

