package com.dollar.all.activities.admin;

import android.os.Bundle;

import com.dollar.all.adapters.AdminProductAdapter;
import com.dollar.all.interfaces.RequestInterface;
import com.dollar.all.models.Product;
import com.dollar.all.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.dollar.all.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Objects.requireNonNull;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList;
    private RecyclerView recyclerView;
    private AdminProductAdapter adminProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.product_list_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getProductList();

    }

    private void getProductList() {
        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<List<Product>> call = requestInterface.allProductByCategory();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                //Log.i(Utils.SUCCESS, String.valueOf(response.body()));

                productList = response.body();
                adminProductAdapter = new AdminProductAdapter(productList);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adminProductAdapter);

/*                for(int i=0;i<productList.size();i++) {
                    Log.i("ca id","id"+ productList.get(i).getCategoryId());
                }*/
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.i(Utils.FAILURE, t.getMessage());

            }
        });
    }

}
