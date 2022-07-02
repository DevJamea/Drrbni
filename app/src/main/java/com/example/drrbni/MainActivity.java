package com.example.drrbni;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drrbni.ViewModels.MyListener;
import com.example.drrbni.ViewModels.Repository;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        Repository repository = new Repository(getApplication());
        repository.getToken(new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {
                repository.updateToken(value);
            }
        });
    }
}