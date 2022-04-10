package by.bsuir.laba_2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import by.bsuir.laba_2.R;
import by.bsuir.laba_2.constant.FilterConst;

public class ExploreFragment extends Fragment {

    private EditText from;
    private EditText to;
    private Button find;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        from = view.findViewById(R.id.from);
        to = view.findViewById(R.id.to);
        find = view.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double fromPrice = checkPrice(from.getText().toString());
                Double toPrice = checkPrice(to.getText().toString());
                if (fromPrice == null || toPrice == null) {
                    Toast toast = Toast.makeText(getContext(), "Введите коррентные цены", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble(FilterConst.FROM_PRICE, fromPrice);
                    bundle.putDouble(FilterConst.TO_PRICE, toPrice);
                    homeFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,homeFragment).commit();

                }
            }
        });
        return view;
    }

    private Double checkPrice(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}