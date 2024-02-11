package com.abhaykumar.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abhaykumar.whatsapp.adapters.FragmentsAdapter;
import com.abhaykumar.whatsapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // for hide tool bar
       // getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        //setViewPager Adapter and tabLayout
        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    // set menu in MainActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // use menu inflater
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);
    }
    // select all items from menu..
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
            {
               //Toast.makeText(MainActivity.this, "Setting Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                  startActivity(intent);
                break;
            }
            case R.id.logout:
            {
                // use auth for singOut..
                auth.signOut();
                // after logout  Activity go to SignInActivity (by passing intent)
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);  // use signInActivity for good practice
                startActivity(intent);
                break;
            }
            case R.id.groupChat:
            {
                Intent intent1 = new Intent(MainActivity.this , GroupChatActivity.class);
                startActivity(intent1);
                break;
            }
        }

        return super.onOptionsItemSelected(item);  // true
    }
}