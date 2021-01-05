package com.dollar.all.activities.admin;

import android.content.Intent;
import android.os.Bundle;

import com.dollar.all.interfaces.RequestInterface;
import com.dollar.all.models.Category;
import com.dollar.all.models.Product;
import com.dollar.all.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dollar.all.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Objects.requireNonNull;

public class AddProductActivity extends AppCompatActivity {

    private EditText productNameEditText, productPriceEditText, productQteEditText;
    private Spinner categorySpinner;
    private List<Category> categories;
    private Button addProductButton;
    Long categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        productNameEditText = findViewById(R.id.edit_text_enter_product_name);
        productPriceEditText = findViewById(R.id.edit_text_enter_product_price);
        productQteEditText = findViewById(R.id.edit_text_enter_product_qte);
        categorySpinner = findViewById(R.id.product_category_spinner);
        addProductButton = findViewById(R.id.add_product_button);

        getUserCategory();

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = categories.get(position).getId();
                Log.i("categoryId value", String.valueOf(categoryId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                addProductButton.startAnimation(animFadeIn);

                if (productNameEditText.getText().toString().isEmpty() || productPriceEditText.getText().toString().isEmpty()
                        || productQteEditText.getText().toString().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.id_add_product_root), "Veuillez renseigner les champs", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    saveProduct(new Product().setName(productNameEditText.getText().toString())
                            .setPrice(Double.parseDouble(productPriceEditText.getText().toString()))
                            .setQte(Integer.parseInt(productQteEditText.getText().toString()))
                            .setCategoryId(categoryId)
                    );

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.id_add_product_root), "Enregistrement réussi avec succès", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    initEditText();
                }
            }
        });

    }

    private void initEditText(){
        productNameEditText.setText("");
        productQteEditText.setText("");
        productPriceEditText.setText("");
    }

    private void saveProduct(Product product) {
        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<Product> call = requestInterface.saveProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Log.i(Utils.SUCCESS, response.message());

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    private void getUserCategory() {

        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<List<Category>> call = requestInterface.allCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.i(Utils.SUCCESS, response.message());

                categories = response.body();
                showCategoryList();

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.i(Utils.FAILURE, String.valueOf(t.getCause()));

            }
        });


    }

    private void showCategoryList() {

        String[] items = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            items[i] = categories.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        categorySpinner.setAdapter(adapter);
    }

}
