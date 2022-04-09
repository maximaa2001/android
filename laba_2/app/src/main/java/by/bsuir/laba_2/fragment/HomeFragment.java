package by.bsuir.laba_2.fragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.bsuir.laba_2.R;
import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.recycleView.ProductAdapter;
import by.bsuir.laba_2.service.ApiService;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        new ApiTask().execute();
        return view;
    }
    private Map<String, Object> to(List<ProductDto> productDtos){
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < productDtos.size(); i++) {
            map.put(String.valueOf(i), productDtos.get(i));
        }
        return map;
    }

    class ApiTask extends AsyncTask<Void, Void, Void>{

        private final ApiService apiService = ApiService.getInstance();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                List<ProductDto> allProducts = apiService.getAllProducts();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductAdapter productAdapter = new ProductAdapter(getContext(),to(allProducts));
                        recyclerView.setAdapter(productAdapter);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}