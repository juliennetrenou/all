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
import com.dollar.all.activities.admin.AddProductActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    private Context context;
    private CardView addProductCardView;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        context = view.getContext();
        addProductCardView = view.findViewById(R.id.add_product_cardView);
        addProductCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddProductActivity.class));
            }
        });
        return view;
    }

}
