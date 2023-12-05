package com.example.androidlabs2;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<Todo> todoItemList;
    private MyListAdapter todoAdapter;
    private Opener dbOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpener = new Opener(this);

        SQLiteDatabase db = dbOpener.getReadableDatabase();
        Cursor cursor = db.query(Opener.Table_Name, null, null, null, null, null, null);
        dbOpener.printCursor(cursor);

        todoItemList = dbOpener.loadData();
        todoAdapter = new MyListAdapter(todoItemList);

        ListView todoList = findViewById(R.id.list_view);
        todoList.setAdapter(todoAdapter);

        dbOpener.loadData();
        todoAdapter.notifyDataSetChanged();

        EditText editText = findViewById(R.id.type_here_edit);
        Switch urgentSwitch = findViewById(R.id.urgent_switch);
        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            String itemText = editText.getText().toString();
            boolean isUrgent = urgentSwitch.isChecked();

            Todo todoItem = new Todo(itemText, isUrgent, 0);
            todoItemList.add(todoItem);

            dbOpener.addToDB(itemText, isUrgent);

            todoAdapter.notifyDataSetChanged();
            editText.setText("");
        });

        todoList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is:" + (position + 1))
                    .setPositiveButton("Yes", (click, arg) -> {
                        int itemId = todoItemList.get(position).getId();

                        dbOpener.deleteFromDB(itemId);

                        todoItemList.remove(position);
                        todoAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
    }

    class MyListAdapter extends BaseAdapter {
        private List<Todo> todoItemList;

        public MyListAdapter(List<Todo> todoItemList) {
            super();
            this.todoItemList = todoItemList;
        }
        public int getCount() {
            return todoItemList.size();
        }

        public Object getItem(int position) {
            return todoItemList.get(position);
        }

        public long getItemId(int position) {
            return position;
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