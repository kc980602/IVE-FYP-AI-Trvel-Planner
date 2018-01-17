package com.triple.triple;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Mytrip extends AppCompatActivity {
    private BottomNavigationViewEx bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrip);

        bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent home = new Intent(Mytrip.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.action_search:
                        //Intent intent1 = new Intent(MainActivity.this, ActivityOne.class);
                        //startActivity(intent1);
                        break;
                    case R.id.action_mytrip:
                        break;
                    case R.id.action_profile:
                        //Intent intent3 = new Intent(MainActivity.this, ActivityThree.class);
                        //startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mytrip_create, menu);
        return true;
    }
}
