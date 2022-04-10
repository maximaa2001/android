package by.bsuir.laba_2.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import by.bsuir.laba_2.AboutProductActivity;
import by.bsuir.laba_2.Click;
import by.bsuir.laba_2.R;
import by.bsuir.laba_2.constant.FilterConst;
import by.bsuir.laba_2.db.Database;
import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.dto.RatingDto;
import by.bsuir.laba_2.recycleView.ProductAdapter;
import by.bsuir.laba_2.service.ApiService;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        Database database = new Database(getContext());
        dbWrite = database.getWritableDatabase();
        dbRead = database.getReadableDatabase();
        Map<String, Object> filter = getFilter(getArguments());
        new ApiTask(filter).execute();
        return view;
    }

    private Map<String, Object> getFilter(Bundle args) {
        Map<String, Object> filter = new HashMap<>();
        if(args != null){
            filter.put(FilterConst.FROM_PRICE,args.getDouble(FilterConst.FROM_PRICE));
            filter.put(FilterConst.TO_PRICE,args.getDouble(FilterConst.TO_PRICE));
        }
        return filter;
    }

    class ApiTask extends AsyncTask<Void, Void, Void> {

        private final ApiService apiService = ApiService.getInstance();

        private Map<String, Object> filter;

        public ApiTask(Map<String, Object> filter) {
            this.filter = filter;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                List<ProductDto> allProducts = apiService.getAllProducts();
                saveToDataBase(allProducts);
                List<ProductDto> filterProducts = applyFilter(allProducts);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductAdapter productAdapter = new ProductAdapter(getContext(), filterProducts, new ClickListener());
                        recyclerView.setAdapter(productAdapter);
                    }
                });
            } catch (IOException e) {
                List<ProductDto> allProducts = findFromDataBase();
                List<ProductDto> filterProducts = applyFilter(allProducts);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(getContext(), "Отсутствует соединение с интернетом", Toast.LENGTH_SHORT);
                        toast.show();
                        ProductAdapter productAdapter = new ProductAdapter(getContext(), filterProducts, new ClickListener());
                        recyclerView.setAdapter(productAdapter);
                    }
                });
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private List<ProductDto> applyFilter(List<ProductDto> products){
           return products.stream().filter(productDto -> {
                if(filter.get(FilterConst.FROM_PRICE) == null && filter.get(FilterConst.TO_PRICE) == null){
                    return true;
                }
                return productDto.getPrice() >= (Double) filter.get(FilterConst.FROM_PRICE) &&
                        productDto.getPrice() <= (Double) filter.get(FilterConst.TO_PRICE);
            }).collect(Collectors.toList());
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void saveToDataBase(List<ProductDto> products) {
            products
                    .forEach(productDto -> {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Database.COLUMN_ID, productDto.getId());
                        contentValues.put(Database.COLUMN_TITLE, productDto.getTitle());
                        contentValues.put(Database.COLUMN_PRICE, String.valueOf(productDto.getPrice()));
                        contentValues.put(Database.COLUMN_DESCRIPTION, productDto.getDescription());
                        contentValues.put(Database.COLUMN_CATEGORY, productDto.getCategory());
                        contentValues.put(Database.COLUMN_IMAGE, productDto.getImage());
                        contentValues.put(Database.COLUMN_RATE, String.valueOf(productDto.getRating().getRate()));
                        contentValues.put(Database.COLUMN_COUNT, String.valueOf(productDto.getRating().getCount()));
                        dbWrite.insert(Database.TABLE, null, contentValues);
                    });
        }

        private List<ProductDto> findFromDataBase() {
            List<ProductDto> products = new ArrayList<>();
            Cursor cursor = dbRead.rawQuery("SELECT * FROM " + Database.TABLE, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String description = cursor.getString(3);
                String category = cursor.getString(4);
                String image = cursor.getString(5);
                String rate = cursor.getString(6);
                String count = cursor.getString(7);
                ProductDto product = new ProductDto(Integer.valueOf(id), title, Double.valueOf(price), description,
                        category, image, new RatingDto(Double.valueOf(rate), Integer.valueOf(count)));
                products.add(product);
            }
            return products;
        }
    }

    class ClickListener implements Click {

        @Override
        public void click(ProductDto product) {
            Intent intent = new Intent(getContext(), AboutProductActivity.class);
            intent.putExtra(FilterConst.PRODUCT, product);
            startActivity(intent);
        }
    }
}