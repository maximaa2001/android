package by.bsuir.laba_2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import by.bsuir.laba_2.constant.FilterConst;
import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.fragment.HomeFragment;

public class AboutProductActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_product);

        Bundle args = getIntent().getExtras();
        ProductDto product = args.getParcelable(FilterConst.PRODUCT);

            imageView = findViewById(R.id.productImage);
            title = findViewById(R.id.productTitle);
            description = findViewById(R.id.productDescription);
            price = findViewById(R.id.productPrice);
            count = findViewById(R.id.productCount);

            Glide.with(this)
                    .load(product.getImage())
                    .into(imageView);

            title.setText(product.getTitle());
            description.setText("Description:\n".concat(product.getDescription()));
            price.setText("Price: ".concat(String.valueOf(product.getPrice())));
            count.setText("Count: ".concat(String.valueOf(product.getRating().getCount())));

    }
}