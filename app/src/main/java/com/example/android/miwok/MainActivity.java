package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // initialize the activity by calling superclass method
        super.onCreate(savedInstanceState);

        // set the content of the activity to use the activity_main.xml layout file
        // activity_main.xml is simply a linearlayout holding viewpager, all sizes match_parent
        setContentView(R.layout.activity_main);

        // get reference to viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create custom adapter that inflates the appropriate View (based on position) and returns it to the viewpager
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // associate adapter to viewpager
        viewPager.setAdapter(adapter);

        // get reference to tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

    }

}
