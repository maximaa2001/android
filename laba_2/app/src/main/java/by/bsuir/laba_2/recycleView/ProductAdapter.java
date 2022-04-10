package by.bsuir.laba_2.recycleView;

import android.content.Context;
import android.content.Intent;
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

import by.bsuir.laba_2.AboutProductActivity;
import by.bsuir.laba_2.Click;
import by.bsuir.laba_2.R;
import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.dto.RatingDto;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ProductDto> products;
    private Click click;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ProductAdapter(Context context, List<ProductDto> products, Click click) {
        this.click = click;
        this.products = products;
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
                    click.click(productDto);
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
