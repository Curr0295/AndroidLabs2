package com.example.androidlabs2;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private List<Todo> todoItemList;
    private MyListAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItemList = new ArrayList<>();
        todoAdapter = new MyListAdapter();

        ListView todoList = findViewById(R.id.list_view);
        todoList.setAdapter(todoAdapter);

        EditText editText = findViewById(R.id.type_here_edit);
        Switch urgentSwitch = findViewById(R.id.urgent_switch);
        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = editText.getText().toString();
                boolean isUrgent = urgentSwitch.isChecked();

                Todo newItem = new Todo(itemText, isUrgent);
                todoItemList.add(newItem);

                todoAdapter.notifyDataSetChanged();

                editText.setText("");
            }
        });

        todoList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to delete this?")

                    .setMessage("The selected row is:" + position)

                    .setPositiveButton("Yes", (click, arg) -> {
                        todoItemList.remove(position);
                        todoAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
    }
        private class MyListAdapter extends BaseAdapter {
            public MyListAdapter() {
                super();
            }
            public int getCount() {
                return todoItemList.size();
            }

            public Object getItem(int position) {
                return todoItemList.get(position);
            }

            public long getItemId(int position) {
                return (long) position;
            }

            public View getView(int position, View old, ViewGroup parent) {
                View newView = old;
                LayoutInflater inflater = getLayoutInflater();

                if (newView == null) {
                    newView = inflater.inflate(R.layout.activity_todo, parent, false);
                }
                TextView tView = newView.findViewById(R.id.list_content);

                Todo todoItem = (Todo) getItem(position);

                tView.setText(todoItem.getItemText());

                if (todoItem.isUrgent()) {
                    newView.setBackgroundColor(Color.RED);
                    tView.setTextColor(Color.WHITE);
                } else {
                    newView.setBackgroundColor(Color.TRANSPARENT);
                    tView.setTextColor(Color.BLACK);
                }

                return newView;

        }
    }
}