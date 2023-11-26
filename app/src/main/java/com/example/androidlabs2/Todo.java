package com.example.androidlabs2;

import androidx.appcompat.app.AppCompatActivity;

public class Todo extends AppCompatActivity {
        private String itemText;
        private boolean urgent;

        public Todo(String itemText, boolean urgent) {
            this.itemText = itemText;
            this.urgent = urgent;
        }

        public String getItemText() {
            return itemText;
        }

        public boolean isUrgent() {
            return urgent;
        }
    }