package by.bsuir.laba_2;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import by.bsuir.laba_2.dto.ProductDto;
import by.bsuir.laba_2.service.ApiService;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private ApiService apiService = ApiService.getInstance();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("by.bsuir.laba_2", appContext.getPackageName());
    }

    @Test
    public void getAllProducts(){
        try {
            List<ProductDto> productListDto = apiService.getAllProducts();
            Log.i("API", "getAllProducts: " + productListDto.size());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}