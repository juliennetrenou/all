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
import android.widget.TextView;

import com.dollar.all.R;
import com.dollar.all.activities.admin.AdminMainActivity;
import com.dollar.all.activities.user.UserMainActivity;
import com.dollar.all.interfaces.RequestInterface;
import com.dollar.all.models.User;
import com.dollar.all.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView signUpTextView;
    private Button userLoginButton;

    private EditText userEmailEditText, userPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpTextView = findViewById(R.id.sign_up_text_view);
        userLoginButton = findViewById(R.id.login_button);

        userEmailEditText = findViewById(R.id.edit_text_enter_login_email);
        userPasswordEditText = findViewById(R.id.edit_text_enter_login_password);

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                userLoginButton.startAnimation(animFadeIn);

                if (userEmailEditText.getText().toString().isEmpty() || userPasswordEditText.getText().toString().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.id_ro), "Veuillez renseigner les champs", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    login(userEmailEditText.getText().toString(), userPasswordEditText.getText().toString());

                }

            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                signUpTextView.startAnimation(animFadeIn);
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

    }

    private void login(String email, String password) {

        Log.i("enter login user method", "start ");

        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<User> call = requestInterface.login(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    //login start main activity

                    // get user information
                    User user = response.body();
                    Utils.saveData(getApplicationContext(), Utils.PREFERENCES_USER_NAME, user.getName());
                    Utils.saveData(getApplicationContext(), Utils.PREFERENCES_USER_PROFILE, user.getProfile());
                    Utils.saveData(getApplicationContext(), Utils.PREFERENCES_USER_FIRST_NAME, user.getFirstName());
                    Utils.saveData(getApplicationContext(), Utils.PREFERENCES_USER_ID, String.valueOf(user.getId()));

                    if(user.getProfile().equals("user")){
                        startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                        LoginActivity.this.finish();

                    }else if (user.getProfile().equals("admin")){
                        startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                        LoginActivity.this.finish();
                    }

                }else{
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.id_ro), "Email ou password incorrect", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(Utils.FAILURE, t.getMessage());

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
