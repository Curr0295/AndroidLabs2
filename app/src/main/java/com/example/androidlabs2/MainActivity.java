package com.example.androidlabs2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;

        if (item.getItemId() == R.id.item1) {
            message = "the raspberry";
        } else if (item.getItemId() == R.id.item2) {
            message = "the watermelon!";
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        if (message != null) {
            Toast.makeText(this, "You clicked on: " + message, Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;

        if (item.getItemId() == R.id.home_button) {
            message = "Home";
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.dadjoke_button) {
            message = "Dad Joke";
            Intent dadJokeIntent = new Intent(this, dad_joke.class);
            startActivity(dadJokeIntent);
        } else if (item.getItemId() == R.id.exit_button) {
            message = "Exit";
            finishAffinity();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        if (message != null) {
            Toast.makeText(this, "You have clicked: " + message, Toast.LENGTH_LONG).show();
        }

        return true;
    }
}