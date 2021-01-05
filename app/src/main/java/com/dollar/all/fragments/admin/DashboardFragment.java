package com.dollar.all.fragments.admin;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dollar.all.R;
import com.dollar.all.activities.RegistrationActivity;
import com.dollar.all.interfaces.RequestInterface;
import com.dollar.all.models.Product;
import com.dollar.all.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private Context context;
    private TextView userNbTextView;
    private TextView productNbByFruitTextView;
    private TextView productNbByVegetableTextView;
    private TextView productNb;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        context=view.getContext();
        userNbTextView = view.findViewById(R.id.nb);
        productNbByFruitTextView = view.findViewById(R.id.product_nb_ca1);
        productNbByVegetableTextView = view.findViewById(R.id.product_nb_ca2);
        productNb = view.findViewById(R.id.product_nb_total);

        getUserNb();

        getProductNbByFruit();

        getProductNbByVegetable();

        getProductNb();

        return view;
    }

    private void getUserNb(){

        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<Long> call = requestInterface.nbUser();
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Long nb = response.body();

                userNbTextView.setText(String.valueOf(nb));

                Log.i(Utils.SUCCESS, String.valueOf(nb));

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

                Log.i(Utils.FAILURE, t.getMessage());

            }
        });
    }

    private void getProductNbByFruit(){
        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<Long> call = requestInterface.nbProductByFruit();
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Long nb = response.body();
                productNbByFruitTextView.setText("Fruits: "+String.valueOf(nb));
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.i(Utils.FAILURE, t.getMessage());

            }
        });
    }

    private void getProductNbByVegetable(){
        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<Long> call = requestInterface.nbProductByVegetable();
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Long nb = response.body();
                productNbByVegetableTextView.setText("Légumes : "+String.valueOf(nb));
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.i(Utils.FAILURE, t.getMessage());

            }
        });
    }

    private void getProductNb(){

        RequestInterface requestInterface = Utils.createService(RequestInterface.class);
        Call<Long> call = requestInterface.nbProduct();
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                productNb.setText("Total : "+response.body());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.i(Utils.FAILURE, t.getMessage());

            }
        });
    }

}
