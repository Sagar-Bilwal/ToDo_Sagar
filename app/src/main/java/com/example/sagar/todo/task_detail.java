package com.example.sagar.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class task_detail extends AppCompatActivity {
    EditText detail_task;
    EditText detail_time;
    int position;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        detail_task=findViewById(R.id.detail_task);
        detail_time=findViewById(R.id.detail_time);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if(bundle!=null)
        {
            String task_detail=bundle.getString(db_constants.todo.TASK,"");
            String time_detail=bundle.getString(db_constants.todo.TIME,"");
            position=bundle.getInt(db_constants.todo.POSITION);
            detail_task.setText(task_detail);
            detail_time.setText(time_detail);
        }
        else
        {
            bundle=new Bundle();
        }
    }
    public void save(View view)
    {
        String time =detail_time.getText().toString();
        String task =detail_task.getText().toString();
        if(isNullOrEmpty(task)){
            Toast.makeText(this,"Mention the task",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isNullOrEmpty(time)){
            Toast.makeText(this,"Specify a time",Toast.LENGTH_SHORT).show();
            return;
        }
        bundle.putString(db_constants.todo.TASK,task);
        bundle.putString(db_constants.todo.TIME,time);
        bundle.putInt(db_constants.todo.POSITION,position);
        Intent intent=new Intent();
        intent.putExtras(bundle);
        setResult(30,intent);
        finish();
    }

    private boolean isNullOrEmpty(String s)
    {
        return s == null || s.isEmpty();
    }


}
