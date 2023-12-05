package com.example.androidlabs2;

import androidx.appcompat.app.AppCompatActivity;

public class Todo extends AppCompatActivity {
        private String itemText;
        private boolean Urgent;
         private int Item_Id;

    public Todo(String itemText, boolean urgent, int Item_Id) {
        this.itemText = itemText;
        this.Urgent = urgent;
        this.Item_Id = Item_Id;
    }


        public String getItemText() {
            return itemText;
        }

        public boolean isUrgent() {
            return Urgent;
        }
            public int getId() {
                return Item_Id;
            }
    }