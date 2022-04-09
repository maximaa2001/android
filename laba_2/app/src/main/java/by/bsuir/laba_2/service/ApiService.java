package by.bsuir.laba_2.service;

import static by.bsuir.laba_2.constant.AppConst.GET_ALL_PRODUCTS;

import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.laba_2.dto.ProductDto;
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

    public List<ProductDto> getAllProducts() throws IOException {
        Response response = getRequest(GET_ALL_PRODUCTS);
        return parseFromJson(response.body().string(), ArrayList.class);
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
}
