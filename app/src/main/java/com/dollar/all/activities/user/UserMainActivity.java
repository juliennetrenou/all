package com.dollar.all.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dollar.all.R;
import com.dollar.all.utils.Utils;

import static java.util.Objects.requireNonNull;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        // get user name
        String name = getString(R.string.welcome_text) + " " +
                Utils.readData(this, Utils.PREFERENCES_USER_FIRST_NAME, "") + " " +
                Utils.readData(this, Utils.PREFERENCES_USER_NAME,"").toUpperCase();

        requireNonNull(getSupportActionBar()).setTitle(name);
    }
}
