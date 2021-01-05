package com.dollar.all.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.dollar.all.R;
import com.dollar.all.activities.admin.AdminMainActivity;
import com.dollar.all.interfaces.RequestInterface;
import com.dollar.all.models.User;
import com.dollar.all.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private TextView signInTextView;
    private ProgressBar progressBar;
    private EditText userPhoneNumberEditText, userNameEditText, userFistNameEditText,
            userEmailEditText, userPasswordEditText, userConfirmedPasswordEditText;

    Button signUpButton;
    private LinearLayout progressBarLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userPhoneNumberEditText = findViewById(R.id.edit_text_enter_customer_phone);
        userNameEditText = findViewById(R.id.edit_text_enter_customer_name);
        userFistNameEditText = findViewById(R.id.edit_text_enter_first_name);
        userEmailEditText = findViewById(R.id.edit_text_enter_customer_email);
        userPasswordEditText = findViewById(R.id.edit_text_enter_customer_password);
        userConfirmedPasswordEditText = findViewById(R.id.edit_text_enter_confirm_password);

        signInTextView = findViewById(R.id.sign_in_text_view);
        progressBarLinearLayout = findViewById(R.id.progress_bar_layout);

        signUpButton = findViewById(R.id.user_create_account_button);

        AndroidNetworking.initialize(getApplicationContext());

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Utils.isInternetAvailable(getApplicationContext())) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.root_reg_layout), "aucune connetion internet", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {
                    Log.i("save user", "start ");
                    checkAndSaveUserRegistrationFields();
                }
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                signInTextView.startAnimation(animFadeIn);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                RegistrationActivity.this.finish();
            }
        });


    }

    private void checkAndSaveUserRegistrationFields() {

        if (userPhoneNumberEditText.getText().toString().isEmpty() || userPasswordEditText.getText().toString().isEmpty() ||
                userConfirmedPasswordEditText.getText().toString().isEmpty()
                || userEmailEditText.getText().toString().isEmpty() || userNameEditText.getText().toString().isEmpty()
                || userFistNameEditText.getText().toString().isEmpty()) {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.root_reg_layout), "Veuillez renseigner les champs", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            // check password

            if (!userPasswordEditText.getText().toString().equals(userConfirmedPasswordEditText.getText().toString())) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.root_reg_layout), "Mot de passe non conform", Snackbar.LENGTH_LONG);
                snackbar.show();

            } else if (!isEmailValid(userEmailEditText.getText().toString())) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.root_reg_layout), "email invalid", Snackbar.LENGTH_LONG);
                snackbar.show();

            } else {

                Log.i("fields are not empty", "ok start send data ");

                saveUser(new User().setPhoneNumber(userPhoneNumberEditText.getText().toString())
                        .setName(userNameEditText.getText().toString().toUpperCase())
                        .setFirstName(userFistNameEditText.getText().toString())
                        .setEmail(userEmailEditText.getText().toString())
                        .setPassword(userPasswordEditText.getText().toString())
                );

                Utils.saveData(getApplicationContext(), Utils.PREFERENCES_USER_NAME,userNameEditText.getText().toString());
                Utils.saveData(getApplicationContext(), Utils.PREFERENCES_USER_FIRST_NAME, userFistNameEditText.getText().toString());
            }
        }

    }


/*
    private void load(final User user) {

        progressBarLinearLayout.setVisibility(View.VISIBLE);

        Thread th = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i <= 100; ) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);
                    i = i + 70;
                }

                saveUser(user);
            }
        };

        th.start();
    }
*/

    // save user data in base

    private void saveUser(User user) {

        Log.i("enter save user methods", "start ");

        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<User> call = requestInterface.saveUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.i("enter save user methods", "start ");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                RegistrationActivity.this.finish();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.root_reg_layout), "Veuillez-vous connecter", Snackbar.LENGTH_LONG);
                snackbar.show();
                //Log.i(Utils.SUCCESS, String.valueOf(response));

                User user1  = response.body();
                Log.i(Utils.SUCCESS, String.valueOf(user1));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(Utils.FAILURE, t.getMessage());
            }
        });

    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
