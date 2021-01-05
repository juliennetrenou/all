package com.dollar.all.fragments.admin;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dollar.all.R;
import com.dollar.all.activities.admin.ProductListActivity;
import com.dollar.all.activities.admin.UserListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private Context context;
    private CardView userCardView, productCardView;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        context = view.getContext();
        userCardView = view.findViewById(R.id.user_cardView);
        productCardView = view.findViewById(R.id.product_cardView);

        userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,UserListActivity.class));
            }
        });

        productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ProductListActivity.class));
            }
        });
        return view;
    }

}
