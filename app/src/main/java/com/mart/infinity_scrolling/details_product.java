package com.mart.infinity_scrolling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class details_product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        // Use the RetrofitClient to get the Retrofit instance
        Retrofit retrofit = RetrofitClient.getClient();
        // Retrieve data from Intent extras
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String productPrice = getIntent().getStringExtra("PRODUCT_PRICE");
        String productImageUrl = getIntent().getStringExtra("PRODUCT_IMAGE_URL");
        String productId = getIntent().getStringExtra("PRODUCT_ID");
        String seller_sku = getIntent().getStringExtra("SELLER_SKU");

        // Create an instance of the ApiService interface
        ApiService apiService = retrofit.create(ApiService.class);

        // Get parameters from your API input method
        String productID = productId;
        String sellerSKU = seller_sku;


        // Make the Retrofit call
        Call<List<SingleProductModel>> call = apiService.getData(productID, sellerSKU);
        call.enqueue(new Callback<List<SingleProductModel>>() {
            @Override
            public void onResponse(Call<List<SingleProductModel>> call, Response<List<SingleProductModel>> response) {
                if (response.isSuccessful()) {
                    List<SingleProductModel> data = response.body();
                    // Process the data as needed
                    String itemName = data.get(0).getItemname();
                    String itemimagename = data.get(0).getMainimageurl();
                    Toast.makeText(details_product.this, "ItemName: " + itemName, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error
                    Toast.makeText(details_product.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SingleProductModel>> call, Throwable t) {
                // Handle failure
                Toast.makeText(details_product.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}