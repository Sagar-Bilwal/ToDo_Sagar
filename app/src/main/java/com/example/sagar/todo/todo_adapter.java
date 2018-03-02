package com.example.sagar.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAGAR on 01-03-2018.
 */

public class todo_adapter extends BaseAdapter
{
    private Context context;
    private ArrayList<todo_class> todo_list;

    public todo_adapter(Context context, ArrayList<todo_class> todo)
    {

        this.context = context;
        this.todo_list = todo;
    }
    @Override
    public int getCount() {
        return todo_list.size();
    }

    @Override
    public todo_class getItem(int position) {
        return todo_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View current_view=convertView;
        if(current_view==null)
        {
            current_view = layoutInflater.inflate(R.layout.todo_layout,parent,false);
            ToDoViewHolder viewHolder=new ToDoViewHolder(current_view);
            current_view.setTag(viewHolder);
        }
        ToDoViewHolder viewHolder=(ToDoViewHolder) current_view.getTag();
        todo_class task_detail=getItem(position);
        viewHolder.task.setText(task_detail.getTask());
        viewHolder.time.setText(task_detail.getTime());
        return current_view;
    }
    class ToDoViewHolder
    {
        TextView time;
        TextView task;
        ToDoViewHolder(View current_view)
        {
            task=current_view.findViewById(R.id.task);
            time=current_view.findViewById(R.id.time);
        }
    }

}
