package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // initialize the activity using the superclass method
        super.onCreate(savedInstanceState);

        // temporary holder layout, just a LinearLayout
        setContentView(R.layout.activity_category);

        // replace the LinearLayout (width and height as match parent) with the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new NumbersFragment()).commit();
    }


}
