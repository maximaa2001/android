package by.bsuir.laba_2.recycleView;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import by.bsuir.laba_2.R;
import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.dto.RatingDto;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ProductDto> products;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ProductAdapter(Context context, Map<String, Object> map) {
        products = new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            ProductDto productDto = of((Map<String, Object>) iterator.next().getValue());
            products.add(productDto);
        }

        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductDto product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ProductDto of(Map<String, Object> map) {
        Class<ProductDto> productDtoClass = ProductDto.class;
        Field[] declaredFields = productDtoClass.getDeclaredFields();
        List<String> fieldNames = Arrays.stream(declaredFields).map(field -> field.getName()).collect(Collectors.toList());
        ProductDto productDto = new ProductDto();
        for (int i = 0; i < fieldNames.size(); i++) {
            switch (fieldNames.get(i)) {
                case "id":
                    productDto.setId((Integer) map.get(fieldNames.get(i)));
                    break;
                case "title":
                    productDto.setTitle((String) map.get(fieldNames.get(i)));
                    break;
                case "price":
                    productDto.setPrice(Double.valueOf(String.valueOf(map.get(fieldNames.get(i)))));
                    break;
                case "description":
                    productDto.setDescription(String.valueOf(map.get(fieldNames.get(i))));
                    break;
                case "category":
                    productDto.setCategory(String.valueOf(map.get(fieldNames.get(i))));
                    break;
                case "image":
                    productDto.setImage(String.valueOf(map.get(fieldNames.get(i))));
                    break;
                case "rating":
                    RatingDto ratingDto = new RatingDto();
                    LinkedHashMap<String, Object> o = (LinkedHashMap<String, Object>) map.get(fieldNames.get(i));
                    ratingDto.setRate(Double.valueOf(String.valueOf(o.get("rate"))));
                    ratingDto.setCount(Integer.valueOf(String.valueOf(o.get("count"))));
                    productDto.setRating(ratingDto);
                    break;
            }
        }
        return productDto;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView category;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_product);
            category = itemView.findViewById(R.id.category_product);
            price = itemView.findViewById(R.id.price_product);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductDto productDto = products.get(getAdapterPosition());
                    Toast toast = Toast.makeText(inflater.getContext(), String.valueOf(productDto.getId()),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

        public void bind(ProductDto product) {
            Glide.with(inflater.getContext())
                    .load(product.getImage())
                    .into(image);
            category.setText(product.getCategory());
            price.setText(String.valueOf(product.getPrice()));
        }
    }
}
