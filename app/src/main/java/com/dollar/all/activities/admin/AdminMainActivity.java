package com.dollar.all.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.dollar.all.R;
import com.dollar.all.fragments.admin.AddFragment;
import com.dollar.all.fragments.admin.DashboardFragment;
import com.dollar.all.fragments.admin.MoreFragment;
import com.dollar.all.fragments.admin.ListFragment;
import com.dollar.all.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static java.util.Objects.requireNonNull;

public class AdminMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get user name
        String name = getString(R.string.welcome_text) + " " +
                Utils.readData(this, Utils.PREFERENCES_USER_FIRST_NAME, "") + " " +
                Utils.readData(this, Utils.PREFERENCES_USER_NAME,"").toUpperCase();

        requireNonNull(getSupportActionBar()).setTitle(name);

        loadFragment(new DashboardFragment());

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard:
                        loadFragment(new DashboardFragment());
                        requireNonNull(getSupportActionBar()).setTitle(getString(R.string.dashboard_text));
                        break;
                    case R.id.add:
                        loadFragment(new AddFragment());
                        requireNonNull(getSupportActionBar()).setTitle(getString(R.string.add_text));
                        break;
                    case R.id.list:
                        loadFragment(new ListFragment());
                        requireNonNull(getSupportActionBar()).setTitle(getString(R.string.list_text));
                        break;
                    case R.id.more:
                        requireNonNull(getSupportActionBar()).setTitle(getString(R.string.other_text));
                        loadFragment(new MoreFragment());
                        break;

                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(String.valueOf(fragment));
        transaction.commit();
    }

}
