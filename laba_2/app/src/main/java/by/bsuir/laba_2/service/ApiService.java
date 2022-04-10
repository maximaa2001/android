package by.bsuir.laba_2.service;

import static by.bsuir.laba_2.constant.AppConst.GET_ALL_PRODUCTS;

import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.dto.RatingDto;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {
    private static ApiService instance;
    private static ObjectMapper objectMapper;
    private OkHttpClient client;

    private ApiService() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    public static ApiService getInstance() {
        if (instance == null) {
            return new ApiService();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ProductDto> getAllProducts() throws IOException {
        Response response = getRequest(GET_ALL_PRODUCTS);
        List<LinkedHashMap<String,Object>> list = parseFromJson(response.body().string(), ArrayList.class);
        List<ProductDto> products = new ArrayList<>();
        list.forEach(map ->{
            ProductDto product = of(map);
            products.add(product);
        });

        return products;
    }

    private Response getRequest(String uri) throws IOException{
        Request request = new Request.Builder()
                .get().url(uri).build();
        Call call = client.newCall(request);
        return call.execute();
    }

    private <T> T parseFromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
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
}
